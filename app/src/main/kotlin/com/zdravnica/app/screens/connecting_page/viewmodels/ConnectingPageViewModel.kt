package com.zdravnica.app.screens.connecting_page.viewmodels

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.app.screens.connecting_page.models.DeviceUIModel
import com.zdravnica.bluetooth.data.COMMAND_ALLOFF
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_IREM
import com.zdravnica.bluetooth.data.COMMAND_KMPR
import com.zdravnica.bluetooth.data.COMMAND_STOP
import com.zdravnica.bluetooth.data.COMMAND_STV1
import com.zdravnica.bluetooth.data.COMMAND_STV2
import com.zdravnica.bluetooth.data.COMMAND_STV3
import com.zdravnica.bluetooth.data.COMMAND_STV4
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.bluetooth.data.DELAY_DURATION_2000
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import com.zdravnica.uikit.DELAY_DURATION_120000
import com.zdravnica.uikit.components.clock.Clock
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container

class ConnectingPageViewModel(
    private val localDataStore: LocalDataStore,
    private val bluetoothController: BluetoothController
) : BaseViewModel<ConnectingPageViewState, ConnectingPageSideEffect>() {
    private var turnOffJob: Job? = null
    private var connectionJob: Job? = null
    private var clock: Clock? = null
    private var snackBarClock: Clock? = null
    val commands = listOf(
        COMMAND_IREM,
        COMMAND_TEN,
        COMMAND_KMPR,
        COMMAND_STV1,
        COMMAND_STV2,
        COMMAND_STV3,
        COMMAND_STV4,
        COMMAND_STOP,
    )
    private val _currentSnackBarModel = MutableStateFlow<SnackBarTypeEnum?>(null)
    val currentSnackBarModel: StateFlow<SnackBarTypeEnum?> = _currentSnackBarModel

    override val container =
        container<ConnectingPageViewState, ConnectingPageSideEffect>(
            ConnectingPageViewState()
        )

    override fun onCleared() {
        super.onCleared()
        bluetoothController.close()
        snackBarClock?.cancel()
        connectionJob?.cancel()
        connectionJob = null
    }

    fun setSnackBarModel(snackBarType: SnackBarTypeEnum) {
        _currentSnackBarModel.value = snackBarType
        startSnackBarTimer()
    }

    private fun startSnackBarTimer() {
        snackBarClock?.cancel()
        snackBarClock = Clock(DELAY_DURATION_2000).apply {
            start(
                onFinish = { _currentSnackBarModel.value = null },
                onTick = {}
            )
        }
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
//        viewModelScope.launch {
//            bluetoothController.getCommandsState.collect { state ->
//                Log.i("asdasdsa", "observeSensorData in state: $state")
//                Log.i("asdasdsa", "observeSensorData in state 0: ${state[0]}")
//                Log.i("asdasdsa", "observeSensorData in state 1: ${state[1]}")
//                Log.i("asdasdsa", "observeSensorData in state 2: ${state[2]}")
//                localDataStore.saveCommandState(COMMAND_TEN, state[0] == '1')
//                localDataStore.saveCommandState(COMMAND_FAN, state[1] == '1')
//                localDataStore.saveCommandState(COMMAND_KMPR, state[2] == '1')
//                localDataStore.saveCommandState(COMMAND_IREM, state[3] == '1')
//
//                localDataStore.saveCommandState(COMMAND_STV1, state[5] == '1')
//                localDataStore.saveCommandState(COMMAND_STV2, state[6] == '1')
//                localDataStore.saveCommandState(COMMAND_STV3, state[7] == '1')
//                localDataStore.saveCommandState(COMMAND_STV4, state[8] == '1')
//            }
//        }

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

        connectionJob = viewModelScope.launch {
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
            bluetoothController.connect(
                BluetoothDeviceDomainModel(
                    name = deviceUIModel.name,
                    macAddress = deviceUIModel.macAddress
                )
            ).collectLatest { }
        }
    }

    fun sendStopCommand() = intent {
        bluetoothController.sendCommand(COMMAND_ALLOFF)
//        for (command in commands) {
            localDataStore.saveCommandState(COMMAND_FAN, false)
//        }
        bluetoothController.sendCommand(COMMAND_STOP)
    }

    fun turnOffAllWorkingProcesses() = intent {
        clock?.cancel()
        clock = Clock(DELAY_DURATION_120000)

        withContext(Dispatchers.Main) {
            clock?.start(
                onFinish = {
                    if (localDataStore.getCommandState(COMMAND_FAN)) {
                        viewModelScope.launch {
                            bluetoothController.sendCommand(COMMAND_FAN, onSuccess = {
                                localDataStore.saveCommandState(COMMAND_FAN, false)
                            })
                        }
                    }
                },
                onTick = { }
            )
        }

        Log.i("asdsadas", "turnOffAllWorkingProcesses: COMMAND_TEN OFF")

        localDataStore.saveAllCommandsAreTurnedOff()

        for (command in commands) {
            while (localDataStore.getCommandState(command)) {
                bluetoothController.sendCommand(command, onSuccess = {
                    localDataStore.saveCommandState(command, false)
                })
            }
        }
    }

    fun stopTurningOffProcesses() {
        turnOffJob?.cancel()
        turnOffJob = null
        clock?.cancel()
        clock = null
    }
}
