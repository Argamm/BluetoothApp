package com.zdravnica.app.screens.selectProcedure.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_KMPR
import com.zdravnica.bluetooth.data.COMMAND_START
import com.zdravnica.bluetooth.data.COMMAND_STV1
import com.zdravnica.bluetooth.data.COMMAND_STV2
import com.zdravnica.bluetooth.data.COMMAND_STV3
import com.zdravnica.bluetooth.data.COMMAND_STV4
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.DELAY_DURATION_1500
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getChipDataList
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.components.clock.Clock
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class SelectProcedureViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
) : BaseViewModel<SelectProcedureViewState, SelectProcedureSideEffect>() {
    private var snackBarClock: Clock? = null
    private var isProcessing = false

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    private val _timerFinished = mutableStateOf(false)
    val timerFinished: State<Boolean> get() = _timerFinished

    override val container =
        container<SelectProcedureViewState, SelectProcedureSideEffect>(
            SelectProcedureViewState(
                bigChipsList = getChipDataList().map { it.chipData }
            )
        )

    init {
        observeSensorData()
    }

    private fun observeSensorData() = intent {
        viewModelScope.launch {
            bluetoothController.getCommandsState.collect { state ->
                if (state[1] == '0') {
                    _timerFinished.value = true
                }
                localDataStore.saveCommandState(COMMAND_TEN, state[0] == '1')
                localDataStore.saveCommandState(COMMAND_FAN, state[1] == '1')
                localDataStore.saveCommandState(COMMAND_KMPR, state[2] == '1')
                localDataStore.saveCommandState(COMMAND_IREM, state[3] == '1')

                localDataStore.saveCommandState(COMMAND_STV1, state[5] == '1')
                localDataStore.saveCommandState(COMMAND_STV2, state[6] == '1')
                localDataStore.saveCommandState(COMMAND_STV3, state[7] == '1')
                localDataStore.saveCommandState(COMMAND_STV4, state[8] == '1')
                loadCommandStates()
            }
        }

        viewModelScope.launch {
            bluetoothController.bluetoothConnectionStatus.collect { status ->
                when (status) {
                    is BluetoothConnectionStatus.Connected -> {}
                    is BluetoothConnectionStatus.Disconnected -> {
                        postSideEffect(SelectProcedureSideEffect.OnBluetoothConnectionLost)
                    }

                    is BluetoothConnectionStatus.Error -> {
                        postSideEffect(SelectProcedureSideEffect.OnBluetoothConnectionLost)
                    }
                }
            }
        }

        viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                postViewState(
                    state.copy(
                        temperature = sensorData?.temrTmpr1 ?: 0
                    )
                )
                loadCommandStates()
            }
        }
    }

    fun navigateToMenuScreen() {
        postSideEffect(SelectProcedureSideEffect.OnNavigateToMenuScreen)
    }

    fun onProcedureCardClick(bigChipsStateModel: BigChipsStateModel) {
        postSideEffect(SelectProcedureSideEffect.OnProcedureCardClick(bigChipsStateModel))
    }

    fun saveTemperature(temperature: Int) {
        _temperature.intValue = temperature
        localDataStore.saveTemperature(temperature)
    }

    fun saveDuration(duration: Int) {
        _duration.intValue = duration
        localDataStore.saveDuration(duration)
    }

    fun updateIkSwitchState(newState: Boolean) = intent {
        localDataStore.setIREMActive(newState)
        postViewState(state.copy(ikSwitchState = newState))
    }

    fun updateIsButtonVisible(newState: Boolean) = intent {
        postViewState(state.copy(isButtonVisible = newState))
    }

    fun updateScrollToEnd(newState: Boolean) = intent {
        postViewState(state.copy(scrollToEnd = newState))
    }

    fun startSnackBarClock() {
        if (snackBarClock == null) {
            snackBarClock = Clock(DELAY_DURATION_1500)
            snackBarClock?.start(
                onFinish = { setSnackBarInvisible() },
                onTick = {}
            )
        }
        updateIsShowingSnackBar(true)
    }

    fun cancelSnackBarClock() {
        snackBarClock?.cancel()
        snackBarClock = null
    }

    fun setSnackBarInvisible() {
        updateIsShowingSnackBar(false)
        cancelSnackBarClock()
    }

    private fun updateIsShowingSnackBar(isVisible: Boolean) = intent {
        postViewState(state.copy(isShowingSnackBar = isVisible))
    }

    fun loadCommandStates() = intent {
        val fanState = localDataStore.getCommandState(COMMAND_FAN)
        val tenState = localDataStore.getCommandState(COMMAND_TEN)
        val kmprState = localDataStore.getCommandState(COMMAND_KMPR)
        val iremState = localDataStore.getCommandState(COMMAND_IREM)

        val iconStates = listOf(
            if (fanState) IconState.ENABLED else IconState.DISABLED,
            if (tenState) IconState.ENABLED else IconState.DISABLED,
            if (kmprState) IconState.ENABLED else IconState.DISABLED,
            if (iremState) IconState.ENABLED else IconState.DISABLED
        )

        postViewState(
            state.copy(iconStates = iconStates)
        )
    }

    fun isFailedSendingCommand(command: String): Boolean {
        return localDataStore.getIsFailedSendingCommand(command)
    }
}