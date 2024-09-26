package com.zdravnica.app.screens.selectProcedure.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getChipDataList
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class SelectProcedureViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
) : BaseViewModel<SelectProcedureViewState, SelectProcedureSideEffect>() {

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

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
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                postViewState(
                    state.copy(
                        temperature = sensorData?.temrTmpr1 ?: 0
                    )
                )
            }
        }
    }

    fun switchIk() {
        viewModelScope.launch {
            bluetoothController.sendCommand(COMMAND_IREM)
        }
    }

    fun navigateToMenuScreen(){
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
        postViewState(state.copy(ikSwitchState = newState))
    }

    fun updateIsButtonVisible(newState: Boolean) = intent {
        postViewState(state.copy(isButtonVisible = newState))
    }

    fun updateScrollToEnd(newState: Boolean) = intent {
        postViewState(state.copy(scrollToEnd = newState))
    }

    fun setSnackBarInvisible() = intent {
        postViewState(state.copy(isShowingSnackBar = false))
    }
}