package com.zdravnica.app.screens.procedure.viewModels

import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.procedure.models.ProcedureScreenViewState
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
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
