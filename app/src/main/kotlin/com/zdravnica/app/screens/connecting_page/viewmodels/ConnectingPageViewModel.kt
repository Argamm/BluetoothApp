package com.zdravnica.app.screens.connecting_page.viewmodels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.app.screens.connecting_page.models.DeviceUIModel
import com.zdravnica.bluetooth.data.models.BluetoothDeviceDataModel
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class ConnectingPageViewModel(
    private val bluetoothController: BluetoothController
) : BaseViewModel<ConnectingPageViewState, ConnectingPageSideEffect>() {


    override val container =
        container<ConnectingPageViewState, ConnectingPageSideEffect>(
            ConnectingPageViewState()
        )

    init {
        initFirstState()
    }

    fun send() = intent {
        // TODO send data to BT controller
    }

    fun showAllBluetoothDevicesDialog() {
        postSideEffect(ConnectingPageSideEffect.OnShowAllDevicesDialog)
    }

    fun observePairedDevices() = intent {
        viewModelScope.launch {
            bluetoothController.refreshPairedDevices()
            bluetoothController.pairedDevices.collectLatest { pairedDevices ->
                postViewState(
                    state.copy(
                        isLoading = false,
                        pairedDevices = pairedDevices.map {
                            DeviceUIModel(
                                name = it.name.orEmpty(),
                                macAddress = it.macAddress.orEmpty()
                            )
                        }.toMutableStateList()
                    )
                )
            }
        }
    }

    private fun initFirstState() = intent {
        viewModelScope.launch {
            postViewState(state.copy(isBtConnected = bluetoothController.bluetoothIsEnabled()))
        }
    }

    fun connectingToDevice(deviceUIModel: DeviceUIModel) = intent {
        viewModelScope.launch {
            postViewState(state.copy(isLoading = true))
            bluetoothController.connect(
                BluetoothDeviceDataModel(
                    name = deviceUIModel.name,
                    macAddress = deviceUIModel.macAddress
                )
            ).collectLatest { result ->
                when (result) {
                    ConnectionResult.Established -> {
                        // TODO SHOW ERROR SNACK BAR

                        postViewState(state.copy(isLoading = false))

                    }

                    is ConnectionResult.Transferred -> {
                        // TODO SUCCESS STATE

                        postViewState(state.copy(isLoading = false))

                    }

                    is ConnectionResult.Error -> {
                        // TODO SHOW ERROR SNACK BAR
                        postViewState(state.copy(isLoading = false))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.close()
    }
}
