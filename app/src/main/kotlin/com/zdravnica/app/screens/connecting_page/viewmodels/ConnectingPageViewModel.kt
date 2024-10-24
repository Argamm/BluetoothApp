package com.zdravnica.app.screens.connecting_page.viewmodels

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.app.screens.connecting_page.models.DeviceUIModel
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_KMPR
import com.zdravnica.bluetooth.data.COMMAND_STOP
import com.zdravnica.bluetooth.data.COMMAND_STV1
import com.zdravnica.bluetooth.data.COMMAND_STV2
import com.zdravnica.bluetooth.data.COMMAND_STV3
import com.zdravnica.bluetooth.data.COMMAND_STV4
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import com.zdravnica.uikit.DELAY_DURATION_1500
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class ConnectingPageViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController
) : BaseViewModel<ConnectingPageViewState, ConnectingPageSideEffect>() {

    override val container =
        container<ConnectingPageViewState, ConnectingPageSideEffect>(
            ConnectingPageViewState()
        )

    override fun onCleared() {
        super.onCleared()
        bluetoothController.close()
    }

    init {
        initFirstState()
    }

    private fun initFirstState() = intent {
        viewModelScope.launch {
            postViewState(state.copy(isBtConnected = bluetoothController.bluetoothIsEnabled()))
        }
    }

    fun showAllBluetoothDevicesDialog() {
        postSideEffect(ConnectingPageSideEffect.OnShowAllDevicesDialog)
    }

    fun observeBluetoothDevices() = intent {
        viewModelScope.launch {
            bluetoothController.refreshPairedDevices()
            bluetoothController.pairedDevices.collectLatest { pairedDevices ->
                postViewState(
                    state.copy(
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

        viewModelScope.launch {
            bluetoothController.startScanning()
            bluetoothController.scannedDevices.collectLatest { scannedDevices ->
                postViewState(
                    state.copy(
                        scannedDevices = scannedDevices.map {
                            DeviceUIModel(
                                name = it.name.orEmpty(),
                                macAddress = it.macAddress.orEmpty()
                            )
                        }.toMutableStateList(),
                    )
                )
            }
        }

        viewModelScope.launch {
            bluetoothController.connectionResultFlow.collectLatest { result ->
                when (result) {
                    ConnectionResult.Established -> {
                        postSideEffect(ConnectingPageSideEffect.OnEstablished)
                        postViewState(state.copy(isLoading = false))
                    }

                    is ConnectionResult.Transferred -> {
                        postViewState(state.copy(isLoading = false))
                        postSideEffect(ConnectingPageSideEffect.OnSuccess)
                    }

                    is ConnectionResult.Error -> {
                        postSideEffect(ConnectingPageSideEffect.OnError)
                        postViewState(state.copy(isLoading = false))
                    }
                }
            }
        }

        viewModelScope.launch {
            bluetoothController.showLoading.collectLatest { result ->
                if (result) {
                    postViewState(state.copy(isLoading = true))
                    postSideEffect(ConnectingPageSideEffect.OnCloseDialog)
                }
            }
        }
    }

    fun connectingToDevice(deviceUIModel: DeviceUIModel) = intent {
        viewModelScope.launch {
            postViewState(state.copy(isLoading = true))
            delay(DELAY_DURATION_1500)
            bluetoothController.connect(
                BluetoothDeviceDomainModel(
                    name = deviceUIModel.name,
                    macAddress = deviceUIModel.macAddress
                )
            ).collectLatest { }
        }
    }

    fun sendStopCommand() = intent {
        bluetoothController.sendCommand(COMMAND_STOP)
    }

    fun turnOffAllWorkingProcesses() = intent {
        viewModelScope.launch {
            delay(120000)

            if (localDataStore.getCommandState(COMMAND_FAN)) {
                Log.i("COMMAND_FAN", "turnOffAllWorkingProcesses: OFF COMMAND_FAN")

                bluetoothController.sendCommand(COMMAND_FAN, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_FAN, false)
                })
            }
        }
        Log.i("COMMAND_TEN", "turnOffAllWorkingProcesses: OFF COMMAND_TEN")

        val commands = listOf(
            COMMAND_TEN,
            COMMAND_KMPR,
            COMMAND_IREM,
            COMMAND_STV1,
            COMMAND_STV2,
            COMMAND_STV3,
            COMMAND_STV4,
            COMMAND_STOP,
        )
        localDataStore.saveAllCommandsAreTurnedOff()

        for (command in commands) {
            if (localDataStore.getCommandState(command)) {
                bluetoothController.sendCommand(command, onSuccess = {
                    localDataStore.saveCommandState(command, false)
                })
            }
        }
    }
}
