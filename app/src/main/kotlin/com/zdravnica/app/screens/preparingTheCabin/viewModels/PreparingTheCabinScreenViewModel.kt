package com.zdravnica.app.screens.preparingTheCabin.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.domain.CalculateTemperatureProgressUseCase
import com.zdravnica.app.screens.preparingTheCabin.models.PreparingTheCabinScreenViewState
import com.zdravnica.bluetooth.domain.controller.BluetoothController
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

    init {
        observeSensorData()
    }

    private fun observeSensorData() = intent {
        viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                postViewState(
                    state.copy(
                        sensorTemperature = sensorData?.temrTmpr1 ?: 0
                    )
                )

                calculateTemperatureProgressUseCase.execute(
                    bluetoothController.sensorDataFlow.map { it?.temrTmpr1 ?: 0 },
                    temperature.value
                ).collect { progress ->
                    _progress.value = progress
                }
            }
        }
    }

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(isDialogVisible = isVisible)
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToCancelDialogPage)
    }
}