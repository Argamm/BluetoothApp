package com.zdravnica.app.screens.connecting_page.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
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
import com.zdravnica.bluetooth.data.COMMAND_START
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    private val commands = listOf(
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

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val isStartCommandSent = MutableStateFlow(false)

    override val container =
        container<ConnectingPageViewState, ConnectingPageSideEffect>(
            ConnectingPageViewState()
        )

    override fun onCleared() {
        super.onCleared()
        bluetoothController.close()
        snackBarClock?.cancel()
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
        viewModelScope.launch {
            bluetoothController.getCommandsState.collect { state ->
                localDataStore.saveCommandState(COMMAND_TEN, state[0] == '1')
                localDataStore.saveCommandState(COMMAND_FAN, state[1] == '1')
                localDataStore.saveCommandState(COMMAND_KMPR, state[2] == '1')
                localDataStore.saveCommandState(COMMAND_IREM, state[3] == '1')
                localDataStore.saveCommandState(COMMAND_STV1, state[5] == '1')
                localDataStore.saveCommandState(COMMAND_STV2, state[6] == '1')
                localDataStore.saveCommandState(COMMAND_STV3, state[7] == '1')
                localDataStore.saveCommandState(COMMAND_STV4, state[8] == '1')
            }
        }

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
                        isStartCommandSent.value = false
                        sendStartCommand()
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
        bluetoothController.sendCommand(COMMAND_STOP)
        localDataStore.saveCommandState(COMMAND_FAN, false)
        postViewState(state.copy(isLoading = false))
    }

    fun turnOffAllWorkingProcesses() = intent {
        turnOffJob?.cancel()
        turnOffJob = null

        turnOffJob = viewModelScope.launch {
            delay(DELAY_DURATION_120000)

            if (localDataStore.getCommandState(COMMAND_FAN)) {
                bluetoothController.sendCommand(COMMAND_FAN, onSuccess = {
                    localDataStore.saveCommandState(COMMAND_FAN, false)
                })
            }
        }
        localDataStore.setIREMActive(false)
        localDataStore.saveAllCommandsAreTurnedOff()

    }

    fun turnOffAllExpectFun() = intent {
        for (command in commands) {
            while (localDataStore.getCommandState(command)) {
                bluetoothController.sendCommand(command, onSuccess = {
                    localDataStore.saveCommandState(command, false)
                })
            }
        }
    }

    private fun sendStartCommand() = intent {
        while (!isStartCommandSent.value) {
            bluetoothController.sendCommand(
                COMMAND_START,
                onSuccess = {
                    postViewState(state.copy(isLoading = false))
                    postSideEffect(ConnectingPageSideEffect.OnSuccess)
                    localDataStore.saveCommandState(COMMAND_START, true)
                    isStartCommandSent.value = true
                }
            )
        }
    }

    fun stopAllProcessesExceptFanUntilCool() = intent {
        turnOffJob?.cancel()
        turnOffJob = null

        turnOffJob = viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                val sensorTemperature = sensorData?.temrTmpr1 ?: 0
                val isDifferenceLarge = (sensorTemperature - temperature.value) < 6

                turnOffAllExpectFun()
                if (isDifferenceLarge) {
                    if (localDataStore.getCommandState(COMMAND_FAN)) {
                        bluetoothController.sendCommand(COMMAND_FAN, onSuccess = {
                            localDataStore.saveCommandState(COMMAND_FAN, false)
                        })
                    }
                    localDataStore.setIREMActive(false)
                    localDataStore.saveAllCommandsAreTurnedOff()
                }
            }
        }
    }

    fun runFanOnlyUntilThermostatStable() = intent {
        turnOffJob = viewModelScope.launch {
            bluetoothController.sensorDataFlow.collectLatest { sensorData ->
                if (sensorData?.thermostat == true) {
                    turnOffAllWorkingProcesses()
                }
            }
        }
    }

    fun stopConnectionObserving() = intent {
        postViewState(state.copy(isLoading = true))
        connectionJob?.cancel()
        connectionJob = null
        isStartCommandSent.value = false
    }

    fun stopTurningOffProcesses() {
        turnOffJob?.cancel()
        turnOffJob = null
        clock?.cancel()
        clock = null
    }
}
