package com.zdravnica.app.screens.preparingTheCabin.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.domain.CalculateTemperatureProgressUseCase
import com.zdravnica.app.screens.preparingTheCabin.models.PreparingTheCabinScreenViewState
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.base_type.IconState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PreparingTheCabinScreenViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
    private val calculateTemperatureProgressUseCase: CalculateTemperatureProgressUseCase
) : BaseViewModel<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>() {
    private var sensorDataJob: Job? = null
    private var hasTemperatureDifferenceWarningBeenShown = false
    private var hasThermostatWarningBeenShown = false
    private var hasTempSensorWarningBeenShown = false

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> get() = _progress

    override val container =
        container<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>(
            PreparingTheCabinScreenViewState()
        )

    override fun onCleared() {
        super.onCleared()
        stopObservingSensorData()
    }

    init {
        observeSensorData()
    }

    fun observeSensorData() = intent {
        sensorDataJob?.cancel()
        sensorDataJob = null

        sensorDataJob = viewModelScope.launch {
            if (!localDataStore.getCommandState(COMMAND_FAN)) {
                bluetoothController.sendCommand(
                    COMMAND_FAN,
                    onSuccess = {
                        localDataStore.saveCommandState(COMMAND_FAN, true)
                    },
                    onFailed = {
                    }
                )
            }

            if (!localDataStore.getCommandState(COMMAND_IREM)
                && localDataStore.getIREMActive()
            ) {
                bluetoothController.sendCommand(
                    COMMAND_IREM,
                    onSuccess = {
                        localDataStore.saveCommandState(COMMAND_IREM, true)
                    },
                    onFailed = {
                    }
                )
            }

            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0
                val isDifferenceLarge = (sensorTemperature - temperature.value) >= 6

                postViewState(
                    state.copy(
                        sensorTemperature = sensorTemperature,
                        isTemperatureDifferenceLarge = isDifferenceLarge,
                    )
                )

                if (isDifferenceLarge && !hasTemperatureDifferenceWarningBeenShown) {
                    turnOffWorkingProcesses()
                    hasTemperatureDifferenceWarningBeenShown = true
                    postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToFailedTemperatureCommandScreen)
                    sensorDataJob?.cancel()
                    sensorDataJob = null
                }

                if (temperature.value - sensorTemperature >= 1) {
                    if (!localDataStore.getCommandState(COMMAND_TEN)) {
                        bluetoothController.sendCommand(
                            COMMAND_TEN,
                            onSuccess = {
                                localDataStore.saveCommandState(COMMAND_TEN, true)
                                updateIconStates()
                            },
                            onFailed = {
                                postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToFailedTenCommandScreen)
                            }
                        )
                    }
                } else {
                    if (localDataStore.getCommandState(COMMAND_TEN)) {
                        bluetoothController.sendCommand(
                            COMMAND_TEN,
                            onSuccess = {
                                localDataStore.saveCommandState(COMMAND_TEN, false)
                                updateIconStates()
                            },
                            onFailed = {
                                postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToFailedTenCommandScreen)
                            }
                        )
                    }
                }

                updateIconStates()

                if (sensorData?.thermostat == false && !hasThermostatWarningBeenShown) {
                    postSideEffect(PreparingTheCabinScreenSideEffect.OnThermostatActivation)
                    hasThermostatWarningBeenShown = true
                    sensorDataJob?.cancel()
                    sensorDataJob = null
                }

                if (sensorData?.temrTmpr1 != 0 && !hasTempSensorWarningBeenShown)  {
                    postSideEffect(PreparingTheCabinScreenSideEffect.OnTemperatureSensorWarning)
                    hasTempSensorWarningBeenShown = true
                    sensorDataJob?.cancel()
                    sensorDataJob = null
                }

                calculateTemperatureProgressUseCase.execute(
                    bluetoothController.sensorDataFlow.map { it?.temrTmpr1 ?: 0 },
                    temperature.value
                ).collect { progress ->
                    _progress.value = progress
                }
            }

            bluetoothController.bluetoothConnectionStatus.collect { status ->
                when (status) {
                    is BluetoothConnectionStatus.Connected -> {}

                    is BluetoothConnectionStatus.Disconnected -> {
                        postSideEffect(PreparingTheCabinScreenSideEffect.OnBluetoothConnectionLost)
                    }

                    is BluetoothConnectionStatus.Error -> {
                        postSideEffect(PreparingTheCabinScreenSideEffect.OnBluetoothConnectionLost)
                    }
                }
            }
        }
    }

    private fun turnOffWorkingProcesses() = intent {
        if (localDataStore.getCommandState(COMMAND_IREM)) {
            bluetoothController.sendCommand(
                COMMAND_IREM,
                onSuccess = {
                    localDataStore.saveCommandState(COMMAND_IREM, false)
                    updateIconStates()
                }
            )
        }
    }

    fun stopObservingSensorData() {
        sensorDataJob?.cancel()
        sensorDataJob = null
    }

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(isDialogVisible = isVisible)
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToCancelDialogPage)
    }

    fun updateIconStates() = intent {
        val fanState = localDataStore.getCommandState(COMMAND_FAN)
        val tenState = localDataStore.getCommandState(COMMAND_TEN)
        val ikState = localDataStore.getCommandState(COMMAND_IREM)

        val newIconStates = listOf(
            if (fanState) IconState.ENABLED else IconState.DISABLED,
            if (tenState) IconState.ENABLED else IconState.DISABLED,
            if (ikState) IconState.ENABLED else IconState.DISABLED,
            IconState.DISABLED,
        )

        postViewState(state.copy(iconStates = newIconStates))
    }
}