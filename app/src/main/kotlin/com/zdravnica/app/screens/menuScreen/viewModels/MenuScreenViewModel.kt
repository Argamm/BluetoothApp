package com.zdravnica.app.screens.menuScreen.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.menuScreen.models.MenuScreenViewState
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MenuScreenViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController,
) : BaseViewModel<MenuScreenViewState, MenuScreenSideEffect>() {
    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    override val container =
        container<MenuScreenViewState, MenuScreenSideEffect>(
            MenuScreenViewState()
        )

    fun observeSensorData() = intent {
        viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0
                val isDifferenceLarge = (sensorTemperature - temperature.value) > 6

                postViewState(
                    state.copy(
                        temperature = sensorData?.temrTmpr1 ?: 0,
                        isTemperatureDifferenceLarge = isDifferenceLarge,
                    )
                )
            }
        }
    }

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(idDialogVisible = isVisible)
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(MenuScreenSideEffect.OnNavigateToCancelDialogPage)
    }

    fun onNavigateUp() {
        postSideEffect(MenuScreenSideEffect.OnNavigateUp)
    }

    fun onSiteClick() {
        postSideEffect(MenuScreenSideEffect.OnSiteClick)
    }

    fun onEmailClick() {
        postSideEffect(MenuScreenSideEffect.OnEmailClick)
    }

    fun onCallClick() {
        postSideEffect(MenuScreenSideEffect.OnCallClick)
    }

    fun getBalmCount(balmName: String) : Float {
        return localDataStore.getBalmCount(balmName)
    }
}