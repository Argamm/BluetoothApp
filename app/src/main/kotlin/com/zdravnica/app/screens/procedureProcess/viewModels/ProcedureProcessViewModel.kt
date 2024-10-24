package com.zdravnica.app.screens.procedureProcess.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.domain.CalculateCaloriesUseCase
import com.zdravnica.app.screens.procedureProcess.models.ProcedureProcessViewState
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_KMPR
import com.zdravnica.bluetooth.data.COMMAND_STV1
import com.zdravnica.bluetooth.data.COMMAND_STV2
import com.zdravnica.bluetooth.data.COMMAND_STV3
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.COUNT_TWO
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class ProcedureProcessViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
    private val calculateCaloriesUseCase: CalculateCaloriesUseCase
) : BaseViewModel<ProcedureProcessViewState, ProcedureProcessSideEffect>() {
    private var sensorDataJob: Job? = null
    private var hasTemperatureDifferenceWarningBeenShown = false
    private var isTimerFinished: Boolean = false

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration


    override val container =
        container<ProcedureProcessViewState, ProcedureProcessSideEffect>(
            ProcedureProcessViewState()
        )

    override fun onCleared() {
        super.onCleared()
        stopObservingSensorData()
        Log.d("asdasdasd", "onCleared in ProcedureProcess: onCleared")
    }

    fun observeSensorData() = intent {
        sensorDataJob?.cancel()

        viewModelScope.launch {
            bluetoothController.bluetoothConnectionStatus.collect { status ->
                when (status) {
                    is BluetoothConnectionStatus.Connected -> {}
                    is BluetoothConnectionStatus.Disconnected -> {
                        postSideEffect(ProcedureProcessSideEffect.OnBluetoothConnectionLost)
                    }
                    is BluetoothConnectionStatus.Error -> {
                        postSideEffect(ProcedureProcessSideEffect.OnBluetoothConnectionLost)
                    }
                }
            }
        }

        sensorDataJob = viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val currentCalorieValue =
                    calculateCaloriesUseCase.calculateCalories(sensorData?.snsrHC ?: 0)
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0
                val isDifferenceLarge = (sensorTemperature - temperature.value) >= 5

                postViewState(
                    state.copy(
                        sensorTemperature = sensorData?.temrTmpr1 ?: 0,
                        calorieValue = currentCalorieValue,
                        pulse = sensorData?.snsrHC ?: 0,
                        isTemperatureDifferenceLarge = isDifferenceLarge,
                    )
                )

                if (isDifferenceLarge && !hasTemperatureDifferenceWarningBeenShown) {
                    hasTemperatureDifferenceWarningBeenShown = true
                    postSideEffect(ProcedureProcessSideEffect.OnNavigateToFailedTemperatureCommandScreen)
                }

                if (!isTimerFinished && temperature.value - sensorTemperature >= 1) {
                    if (!localDataStore.getCommandState(COMMAND_TEN)) {
                        Log.i("COMMAND_TEN", "ProcedureProcess: ON COMMAND_TEN")

                        bluetoothController.sendCommand(
                            COMMAND_TEN,
                            onSuccess = {
                                localDataStore.saveCommandState(COMMAND_TEN, true)
                                updateIconStates()
                            },
                            onFailed = {
                                postSideEffect(ProcedureProcessSideEffect.OnNavigateToFailedTenCommandScreen)
                            }
                        )
                    }
                } else {
                    if (localDataStore.getCommandState(COMMAND_TEN)) {
                        Log.i("COMMAND_TEN", "ProcedureProcess: OFF COMMAND_TEN")

                        bluetoothController.sendCommand(COMMAND_TEN, onSuccess = {
                            localDataStore.saveCommandState(COMMAND_TEN, false)
                            updateIconStates()
                        })
                    }
                }
            }
        }
    }

    fun updateTimerStatus(isFinished: Boolean) {
        isTimerFinished = isFinished
    }

    fun stopObservingSensorData() {
        sensorDataJob?.cancel()
    }

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(isDialogVisible = isVisible)
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(ProcedureProcessSideEffect.OnNavigateToCancelDialogPage)
    }

    fun navigateToMainScreen() {
        stopObservingSensorData()
        postSideEffect(ProcedureProcessSideEffect.OnNavigateToMainScreen)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendEndingCommands() {
        viewModelScope.launch {
            if (localDataStore.getCommandState(COMMAND_TEN)) {
                Log.i("COMMAND_TEN", "ProcedureProcess endingCommands: OFF COMMAND_TEN")

                bluetoothController.sendCommand(COMMAND_TEN, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_TEN, false)
                    updateIconStates()
                })//turn Off
            }
            while (localDataStore.getCommandState(COMMAND_IREM)) {
                bluetoothController.sendCommand(COMMAND_IREM, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_IREM, false)
                    updateIconStates()
                })//turn Off
            }

            GlobalScope.launch {
                delay(120000)
                while (localDataStore.getCommandState(COMMAND_FAN)) {
                    Log.i("COMMAND_FAN", "PROCEDURE Process finish: OFF COMMAND_FAN")

                    bluetoothController.sendCommand(COMMAND_FAN, onSuccess = {
                        localDataStore.saveCommandState(COMMAND_FAN, false)
                        updateIconStates()
                    }) // turn Off
                }
            }
        }
    }

    fun turnOnKMPR() = intent {
        while (!localDataStore.getCommandState(COMMAND_KMPR)) {
            bluetoothController.sendCommand(COMMAND_KMPR, onSuccess = {
                localDataStore.saveCommandState(COMMAND_KMPR, true)
                updateIconStates()
            }) // Turn On
        }
    }

    fun turnOffKMPR() = intent {
        while (localDataStore.getCommandState(COMMAND_KMPR)) {
            bluetoothController.sendCommand(COMMAND_KMPR, onSuccess = {
                localDataStore.saveCommandState(COMMAND_KMPR, false)
                updateIconStates()
            })  // Turn Off
        }
    }

    fun startSTVCommandSequence(
        chipBalmInfoModels: List<ChipBalmInfoModel>,
        allBalmNames: List<String>
    ) = intent {
        chipBalmInfoModels.let { list ->
            val totalConsumption = list.sumOf { it.consumption }
            val totalDurationInSeconds = ONE_MINUTE_IN_SEC

            val commandDurations = list.map { balmInfo ->
                val commandTime =
                    (balmInfo.consumption / totalConsumption.toFloat()) * totalDurationInSeconds
                Pair(balmInfo, commandTime.toLong())
            }

            viewModelScope.launch {
                commandDurations.forEachIndexed { index, (balmInfo, duration) ->
                    val balmName = allBalmNames.getOrNull(index) ?: "Unknown Balm"

                    when (balmInfo.key) {
                        COUNT_ONE -> {
                            sendCommandUntilOn(
                                COMMAND_STV1,
                            )
                        }

                        COUNT_TWO -> {
                            sendCommandUntilOn(
                                COMMAND_STV2,
                            )
                        }

                        COUNT_THREE -> {
                            sendCommandUntilOn(
                                COMMAND_STV3,
                            )
                        }
                    }
                    delay(duration * DELAY_1000_ML)
                    localDataStore.consumeBalm(balmName, balmInfo.balmCount / COUNT_TWO)

                    when (balmInfo.key) {
                        COUNT_ONE -> {
                            sendCommandUntilOff(COMMAND_STV1)
                        }

                        COUNT_TWO -> {
                            sendCommandUntilOff(COMMAND_STV2)
                        }

                        COUNT_THREE -> {
                            sendCommandUntilOff(COMMAND_STV3)
                        }
                    }
                    delay(DELAY_1000_ML)
                }
            }
        }
    }

    private suspend fun sendCommandUntilOn(command: String) {
        while (!localDataStore.getCommandState(command)) {
            bluetoothController.sendCommand(command, onSuccess = {
                localDataStore.saveCommandState(command, true)
            })
        }
    }

    private suspend fun sendCommandUntilOff(command: String) {
        while (localDataStore.getCommandState(command)) {
            bluetoothController.sendCommand(command, onSuccess = {
                localDataStore.saveCommandState(command, false)
            })
        }
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