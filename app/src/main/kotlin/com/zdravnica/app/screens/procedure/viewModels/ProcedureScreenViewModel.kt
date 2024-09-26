package com.zdravnica.app.screens.procedure.viewModels

import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.procedure.models.ProcedureScreenViewState
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container

class ProcedureScreenViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
) : BaseViewModel<ProcedureScreenViewState, ProcedureScreenSideEffect>() {

    override val container =
        container<ProcedureScreenViewState, ProcedureScreenSideEffect>(
            ProcedureScreenViewState()
        )

    fun onNavigateUp() {
        postSideEffect(ProcedureScreenSideEffect.OnNavigateUp)
    }

    fun onOptionSelected(selectedOption: Int) {
        postSideEffect(ProcedureScreenSideEffect.OnOptionSelected(selectedOption))
    }

    fun startProcedureWithCommands() {
        viewModelScope.launch {
            bluetoothController.sendCommand(COMMAND_FAN)
            bluetoothController.sendCommand(COMMAND_TEN)
        }
    }

}