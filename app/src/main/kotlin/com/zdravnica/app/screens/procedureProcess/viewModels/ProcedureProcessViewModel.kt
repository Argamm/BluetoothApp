package com.zdravnica.app.screens.procedureProcess.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.zdravnica.bluetooth.data.COMMAND_STV4
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.COUNT_TWO
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import com.zdravnica.uikit.components.chips.models.ValveType
import com.zdravnica.uikit.components.chips.models.getSystemTimingSequence
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import kotlin.math.roundToInt

class ProcedureProcessViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
    private val calculateCaloriesUseCase: CalculateCaloriesUseCase
) : BaseViewModel<ProcedureProcessViewState, ProcedureProcessSideEffect>() {
    private var sensorDataJob: Job? = null
    private var balmSupplyJob: Job? = null
    private var sendingCommands: Job? = null
    private var hasTemperatureDifferenceWarningBeenShown = false
    private var isTimerFinished: Boolean = false

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    private val _balmFeeding = mutableStateOf(false)
    val balmFeeding: State<Boolean> get() = _balmFeeding

    private val _timerFinished = mutableStateOf(false)
    val timerFinished: State<Boolean> get() = _timerFinished

    override val container =
        container<ProcedureProcessViewState, ProcedureProcessSideEffect>(
            ProcedureProcessViewState()
        )

    override fun onCleared() {
        super.onCleared()
        stopObservingSensorData()
    }

    init {
        switchIkOn()
    }

    fun observeSensorData() = intent {
        sensorDataJob?.cancel()
        calculateCaloriesUseCase.resetCalories()

        viewModelScope.launch {
            bluetoothController.getCommandsState.collect { state ->
                if (state[1] == '0')
                    _timerFinished.value = true
            }
        }

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
                    calculateCaloriesUseCase.calculateCalories(
                        sensorData?.snsrHC ?: 0,
                        isTimerFinished
                    )
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0
                val isDifferenceLarge = (sensorTemperature - temperature.value) >= 5

                postViewState(
                    state.copy(
                        sensorTemperature = sensorData?.temrTmpr1 ?: 0,
                        calorieValue = currentCalorieValue,
                        pulse = sensorData?.snsrHC ?: 0,
                        skinTemperature = sensorData?.skinTemperature?.roundToInt() ?: 0,
                        isTemperatureDifferenceLarge = isDifferenceLarge,
                    )
                )

                updateIconStates()

                if (isDifferenceLarge && !hasTemperatureDifferenceWarningBeenShown) {
                    hasTemperatureDifferenceWarningBeenShown = true
                    postSideEffect(ProcedureProcessSideEffect.OnNavigateToFailedTemperatureCommandScreen)
                }

                if (!isTimerFinished && temperature.value - sensorTemperature >= 1) {
                    if (!localDataStore.getCommandState(COMMAND_TEN)) {
                        bluetoothController.sendCommand(
                            COMMAND_TEN,
                            onSuccess = {
                                localDataStore.saveFailSendingCommand(COMMAND_TEN, false)
                                localDataStore.saveCommandState(COMMAND_TEN, true)
                                updateIconStates()
                            },
                            onFailed = {
                                localDataStore.saveFailSendingCommand(COMMAND_TEN, true)
                                postSideEffect(ProcedureProcessSideEffect.OnNavigateToFailedTenCommandScreen)
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
                                postSideEffect(ProcedureProcessSideEffect.OnNavigateToFailedTenCommandScreen)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun switchIkOn() = intent{
        if (!isTimerFinished && !localDataStore.getCommandState(COMMAND_IREM)) {
            bluetoothController.sendCommand(
                COMMAND_IREM,
                onSuccess = {
                    localDataStore.saveCommandState(COMMAND_IREM, true)
                    updateIconStates()
                    localDataStore.saveFailSendingCommand(COMMAND_IREM, false)
                }, onFailed = {
                    localDataStore.saveFailSendingCommand(COMMAND_IREM, true)
                }
            )
            updateIconStates()
        }
    }

    fun updateTimerStatus(isFinished: Boolean) {
        isTimerFinished = isFinished
    }

    fun stopObservingSensorData() {
        sensorDataJob?.cancel()
        sendingCommands?.cancel()
        balmSupplyJob?.cancel()
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

    fun turnOnKMPR() = intent {
        while (!localDataStore.getCommandState(COMMAND_KMPR)) {
            bluetoothController.sendCommand(COMMAND_KMPR, onSuccess = {
                localDataStore.saveFailSendingCommand(COMMAND_KMPR, false)
                localDataStore.saveCommandState(COMMAND_KMPR, true)
                updateIconStates()
            }, onFailed = {
                localDataStore.saveFailSendingCommand(COMMAND_KMPR, true)
            })
        }
    }

    fun turnOffKMPR() = intent {
        while (localDataStore.getCommandState(COMMAND_KMPR)) {
            if (!_balmFeeding.value) {
                bluetoothController.sendCommand(COMMAND_KMPR, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_KMPR, false)
                    updateIconStates()
                })
            }
        }
    }

    fun startSTVCommandSequence(
        chipBalmInfoModels: List<ChipBalmInfoModel>,
        allBalmNames: List<String>,
        chipTitle: Int
    ) = intent {
        _balmFeeding.value = true
        val sequence = getSystemTimingSequence(chipTitle)

        chipBalmInfoModels.let { list ->
            val totalConsumption = list.sumOf { it.consumption }
            val totalDurationInSeconds = ONE_MINUTE_IN_SEC

            val commandDurations = list.map { balmInfo ->
                val commandTime =
                    (balmInfo.consumption / totalConsumption.toFloat()) * totalDurationInSeconds
                Pair(balmInfo, commandTime.toLong())
            }

            balmSupplyJob = viewModelScope.launch {
                sequence.forEach { timingPattern ->
                    timingPattern.forEachIndexed { index, (command, duration) ->
                        if (duration != 0L) {
                            when (command) {
                                ValveType.FIRST_BALM -> sendCommandUntilOn(COMMAND_STV1)
                                ValveType.SECOND_BALM -> sendCommandUntilOn(COMMAND_STV2)
                                ValveType.THIRD_BALM -> sendCommandUntilOn(COMMAND_STV3)
                                ValveType.FOURTH_BALM -> sendCommandUntilOn(COMMAND_STV4)
                            }

                            delay((duration - 1) * DELAY_1000_ML)

                            if (index + 1 < timingPattern.size && timingPattern[index + 1].second != 0L) {
                                val nextCommand = timingPattern[index + 1].first
                                when (nextCommand) {
                                    ValveType.FIRST_BALM -> sendCommandUntilOn(COMMAND_STV1)
                                    ValveType.SECOND_BALM -> sendCommandUntilOn(COMMAND_STV2)
                                    ValveType.THIRD_BALM -> sendCommandUntilOn(COMMAND_STV3)
                                    ValveType.FOURTH_BALM -> sendCommandUntilOn(COMMAND_STV4)
                                }
                            }
                            delay(DELAY_1000_ML)

                            when (command) {
                                ValveType.FIRST_BALM -> sendCommandUntilOff(COMMAND_STV1)
                                ValveType.SECOND_BALM -> sendCommandUntilOff(COMMAND_STV2)
                                ValveType.THIRD_BALM -> sendCommandUntilOff(COMMAND_STV3)
                                ValveType.FOURTH_BALM -> sendCommandUntilOff(COMMAND_STV4)
                            }

                            delay(DELAY_1000_ML)
                        }
                    }
                }

                // Update balm consumption in the local data store
                commandDurations.forEachIndexed { index, (balmInfo, _) ->
                    val balmName = allBalmNames.getOrNull(index) ?: "Unknown Balm"
                    localDataStore.consumeBalm(balmName, balmInfo.balmCount / COUNT_TWO)
                }
                _balmFeeding.value = false
            }
        }
    }

    private suspend fun sendCommandUntilOn(command: String) {
        sendingCommands?.cancel()
        sendingCommands = viewModelScope.launch {
            while (!localDataStore.getCommandState(command)) {
                bluetoothController.sendCommand(command, onSuccess = {
                    localDataStore.saveCommandState(command, true)
                })
            }
        }
    }

    private suspend fun sendCommandUntilOff(command: String) {
        sendingCommands?.cancel()
        sendingCommands = viewModelScope.launch {
            while (localDataStore.getCommandState(command)) {
                bluetoothController.sendCommand(command, onSuccess = {
                    localDataStore.saveCommandState(command, false)
                })
            }
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