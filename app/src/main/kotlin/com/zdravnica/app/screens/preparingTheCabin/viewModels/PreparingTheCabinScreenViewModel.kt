package com.zdravnica.app.screens.preparingTheCabin.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.domain.CalculateTemperatureProgressUseCase
import com.zdravnica.app.screens.preparingTheCabin.models.PreparingTheCabinScreenViewState
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_KMPR
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
                        postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToFailedFanCommandScreen)
                    }
                )
            }

            if (!localDataStore.getCommandState(COMMAND_IREM)) {
                bluetoothController.sendCommand(COMMAND_IREM, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_IREM, true)
                })
            }

            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0

                postViewState(
                    state.copy(
                        sensorTemperature = sensorTemperature
                    )
                )

                if (temperature.value - sensorTemperature >= 1) {
                    if (!localDataStore.getCommandState(COMMAND_TEN)) {
                        Log.i("asdsadas", "PreparingCabin: COMMAND_TEN ON")
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
                        Log.i("asdsadas", "PreparingCabin: COMMAND_TEN OFF")

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
        val kmprState = localDataStore.getCommandState(COMMAND_KMPR)
        val iremState = localDataStore.getCommandState(COMMAND_IREM)

        val newIconStates = listOf(
            if (fanState) IconState.ENABLED else IconState.DISABLED,
            if (tenState) IconState.ENABLED else IconState.DISABLED,
            if (kmprState) IconState.ENABLED else IconState.DISABLED,
            if (iremState) IconState.ENABLED else IconState.DISABLED,
            IconState.DISABLED
        )

        postViewState(state.copy(iconStates = newIconStates))
    }
}