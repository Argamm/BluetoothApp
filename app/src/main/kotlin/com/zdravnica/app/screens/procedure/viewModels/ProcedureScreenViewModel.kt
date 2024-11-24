package com.zdravnica.app.screens.procedure.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.procedure.models.ProcedureScreenViewState
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class ProcedureScreenViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
) : BaseViewModel<ProcedureScreenViewState, ProcedureScreenSideEffect>() {
    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    override val container =
        container<ProcedureScreenViewState, ProcedureScreenSideEffect>(
            ProcedureScreenViewState()
        )

    init {
        observeSensorData()
    }

    private fun observeSensorData() = intent {
        viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val currentTemperature = sensorData?.temrTmpr1 ?: 0
                val alertCondition = currentTemperature > _temperature.intValue + 5

                postViewState(
                    state.copy(
                        temperatureAlert = alertCondition,
                        thermostatAlert = sensorData?.thermostat == false,
                        temperatureSensorAlert = sensorData?.temrTmpr1 == 0
                    )
                )
            }
        }
    }


    fun onNavigateUp() {
        postSideEffect(ProcedureScreenSideEffect.OnNavigateUp)
    }

    fun onOptionSelected(selectedOption: Int) {
        postSideEffect(ProcedureScreenSideEffect.OnOptionSelected(selectedOption))
    }

    fun startProcedureWithCommands() {
        viewModelScope.launch {
            bluetoothController.getCommandsState.collect { state ->
                localDataStore.saveCommandState(COMMAND_TEN, state[0] == '1')
                localDataStore.saveCommandState(COMMAND_FAN, state[1] == '1')
                localDataStore.saveCommandState(COMMAND_KMPR, state[2] == '1')
                localDataStore.saveCommandState(COMMAND_IREM, state[3] == '1')
                localDataStore.saveCommandState(COMMAND_STV1, state[5] == '1')
                localDataStore.saveCommandState(COMMAND_STV2, state[6] == '1')
                localDataStore.saveCommandState(COMMAND_STV3, state[7] == '1')
                localDataStore.saveCommandState(COMMAND_STV4, state[8] == '1')
            }
        }

        viewModelScope.launch {
            bluetoothController.bluetoothConnectionStatus.collect { status ->
                when (status) {
                    is BluetoothConnectionStatus.Connected -> {}
                    is BluetoothConnectionStatus.Disconnected -> {
                        postSideEffect(ProcedureScreenSideEffect.OnBluetoothConnectionLost)
                    }

                    is BluetoothConnectionStatus.Error -> {
                        postSideEffect(ProcedureScreenSideEffect.OnBluetoothConnectionLost)
                    }
                }
            }
        }
    }

    private fun getBalmCount(balmName: String): Float {
        return localDataStore.getBalmCount(balmName)
    }

    fun balmFilled(balmName: String, balmsName: List<String>) {
        localDataStore.resetBalmCount(balmName = balmName)
        updateBalmCounts(balmsName)
    }

    fun updateBalmCounts(balmsName: List<String>) = intent {
        val firstBalmCount = getBalmCount(balmsName[0])
        val secondBalmCount = getBalmCount(balmsName[1])
        val thirdBalmCount = getBalmCount(balmsName[2])
        postViewState(
            state.copy(
                firstBalmCount = firstBalmCount,
                secondBalmCount = secondBalmCount,
                thirdBalmCount = thirdBalmCount
            )
        )
    }
}
