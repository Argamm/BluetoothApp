package com.zdravnica.app.screens.procedureProcess.viewModels

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
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
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

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    private var isTimerFinished: Boolean = false

    override val container =
        container<ProcedureProcessViewState, ProcedureProcessSideEffect>(
            ProcedureProcessViewState()
        )

    private var sensorDataJob: Job? = null

    fun observeSensorData() = intent {
        sensorDataJob?.cancel()

        sensorDataJob = viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val currentCalorieValue =
                    calculateCaloriesUseCase.calculateCalories(sensorData?.snsrHC ?: 0)
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0

                postViewState(
                    state.copy(
                        sensorTemperature = sensorData?.temrTmpr1 ?: 0,
                        calorieValue = currentCalorieValue,
                        pulse = sensorData?.snsrHC ?: 0
                    )
                )

                if (!isTimerFinished && temperature.value - sensorTemperature >= 1) {
                    if (!localDataStore.getCommandState(COMMAND_TEN)) {
                        localDataStore.saveCommandState(COMMAND_TEN, true)
                        bluetoothController.sendCommand(COMMAND_TEN)
                    }
                } else {
                    if (localDataStore.getCommandState(COMMAND_TEN)) {
                        localDataStore.saveCommandState(COMMAND_TEN, false)
                        bluetoothController.sendCommand(COMMAND_TEN)
                    }
                }
            }
        }
    }

    fun updateTimerStatus(isFinished: Boolean) {
        isTimerFinished = isFinished
    }

    private fun stopObservingSensorData() {
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
                localDataStore.saveCommandState(COMMAND_TEN, false)
                bluetoothController.sendCommand(COMMAND_TEN)//turn Off
            }
            if (localDataStore.getCommandState(COMMAND_IREM)) {
                localDataStore.saveCommandState(COMMAND_IREM, false)
                bluetoothController.sendCommand(COMMAND_IREM)//turn Off
            }

            GlobalScope.launch {
                delay(120000)
                if (localDataStore.getCommandState(COMMAND_FAN)) {
                    localDataStore.saveCommandState(COMMAND_FAN, false)
                    bluetoothController.sendCommand(COMMAND_FAN) // turn Off
                }
            }
        }
    }

    fun turnOnKMPR() = intent {
        bluetoothController.sendCommand(COMMAND_KMPR) // Turn On
        localDataStore.saveCommandState(COMMAND_KMPR, true)
    }

    fun turnOffKMPR() = intent {
        if (localDataStore.getCommandState(COMMAND_KMPR)) {
            bluetoothController.sendCommand(COMMAND_KMPR) // Turn Off
            localDataStore.saveCommandState(COMMAND_KMPR, false)
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
                        1 -> {
                            sendCommandUntilOn(
                                COMMAND_STV1,
                                balmName,
                                balmInfo.balmCount
                            )
                        }

                        2 -> {
                            sendCommandUntilOn(
                                COMMAND_STV2,
                                balmName,
                                balmInfo.balmCount
                            )
                        }

                        3 -> {
                            sendCommandUntilOn(
                                COMMAND_STV3,
                                balmName,
                                balmInfo.balmCount
                            )
                        }
                    }
                    delay(duration * DELAY_1000_ML)

                    when (balmInfo.key) {
                        1 -> {
                            sendCommandUntilOff(COMMAND_STV1)
                        }

                        2 -> {
                            sendCommandUntilOff(COMMAND_STV2)
                        }

                        3 -> {
                            sendCommandUntilOff(COMMAND_STV3)
                        }
                    }
                    delay(DELAY_1000_ML)
                }
            }
        }
    }

    private suspend fun sendCommandUntilOn(command: String, balmName: String, balmCount: Double) {
        while (!localDataStore.getCommandState(command)) {
            bluetoothController.sendCommand(command, onSuccess = {
                localDataStore.saveCommandState(command, true)
                localDataStore.consumeBalm(balmName, balmCount / 2)
            })
            delay(DELAY_1000_ML)
        }
    }

    private suspend fun sendCommandUntilOff(command: String) {
        while (localDataStore.getCommandState(command)) {
            bluetoothController.sendCommand(command, onSuccess = {
                localDataStore.saveCommandState(command, false)
            })
            delay(DELAY_1000_ML)
        }
    }
}