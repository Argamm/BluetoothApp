package com.zdravnica.app.screens.procedureProcess.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.domain.CalculateCaloriesUseCase
import com.zdravnica.app.screens.procedureProcess.models.ProcedureProcessViewState
import com.zdravnica.bluetooth.domain.controller.BluetoothController
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

    override val container =
        container<ProcedureProcessViewState, ProcedureProcessSideEffect>(
            ProcedureProcessViewState()
        )

    fun observeSensorData() = intent {
        viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val currentCalorieValue =
                    calculateCaloriesUseCase.calculateCalories(sensorData?.snsrHC ?: 0)

                postViewState(
                    state.copy(
                        sensorTemperature = sensorData?.temrTmpr1 ?: 0,
                        calorieValue = currentCalorieValue,
                        pulse = sensorData?.snsrHC ?: 0
                    )
                )
            }
        }
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
        postSideEffect(ProcedureProcessSideEffect.OnNavigateToMainScreen)
    }
}