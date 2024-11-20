package com.zdravnica.bluetooth.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothStatusCodes
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.zdravnica.bluetooth.data.mapper.toBluetoothDevice
import com.zdravnica.bluetooth.data.mapper.toBluetoothDeviceDomain
import com.zdravnica.bluetooth.data.models.BluetoothConnectionStatus
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.data.models.InfoDataModel
import com.zdravnica.bluetooth.data.models.SensorData
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import com.zdravnica.bluetooth.util.PermissionUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.math.roundToInt

@SuppressLint("MissingPermission")
internal class AndroidBluetoothController(
    private val context: Context,
    private val adapter: BluetoothAdapter?
) : BluetoothController {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var bluetoothGatt: BluetoothGatt? = null
    private var isListening = false
    private var lastTemrTmpr1: Int? = null
    private var anomalyCount = 0
    private var lastValidSkinTemperature: Double? = null
    private var commandCharacteristic: BluetoothGattCharacteristic? = null
    private var isConnecting = false

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomainModel>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomainModel>> =
        _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomainModel>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomainModel>> =
        _pairedDevices.asStateFlow()

    private val _connectionResultFlow = MutableSharedFlow<ConnectionResult>()
    override val connectionResultFlow: SharedFlow<ConnectionResult> = _connectionResultFlow

    private val _showLoading = MutableSharedFlow<Boolean>()
    override val showLoading: SharedFlow<Boolean> = _showLoading

    private val _sensorDataFlow = MutableStateFlow<SensorData?>(null)
    override val sensorDataFlow: StateFlow<SensorData?> = _sensorDataFlow.asStateFlow()

    private val _bluetoothConnectionStatus =
        MutableStateFlow<BluetoothConnectionStatus>(BluetoothConnectionStatus.Disconnected)
    override val bluetoothConnectionStatus: StateFlow<BluetoothConnectionStatus> =
        _bluetoothConnectionStatus

    private val _getCommandsState = MutableSharedFlow<String>()
    override val getCommandsState: SharedFlow<String> = _getCommandsState

    override fun bluetoothIsEnabled(): Boolean = adapter?.isEnabled ?: false

    override fun refreshPairedDevices() = cancelAndGetPairedDevices()

    override fun connect(device: BluetoothDeviceDomainModel): Flow<ConnectionResult> {
        return flow {
            if (!PermissionUtil.canBluetoothConnect(context)) {
                emit(ConnectionResult.Error("No BLUETOOTH_CONNECT permission!"))
                return@flow
            }

            try {
                val targetBluetoothDevice = _scannedDevices.value
                    .find { it.name == device.name && it.macAddress == device.macAddress }
                    ?: _pairedDevices.value
                        .find { it.name == device.name && it.macAddress == device.macAddress }

                if (targetBluetoothDevice == null) {
                    emit(ConnectionResult.Error("Device not found"))
                    return@flow
                }

                val bluetoothDevice = adapter?.getRemoteDevice(targetBluetoothDevice.macAddress)
                if (bluetoothDevice == null) {
                    emit(ConnectionResult.Error("Unable to create Bluetooth device"))
                    return@flow
                }

                if (bluetoothDevice.name != TARGET_DEVICE_NAME) {
                    _connectionResultFlow.emit(ConnectionResult.Established)
                    return@flow
                }

                connectToDevice(bluetoothDevice)
            } catch (e: IOException) {
                emit(ConnectionResult.Error("Connection failed: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun connectToDevice(device: BluetoothDevice) {
        close()
        bluetoothGatt = device.connectGatt(context, true, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(
                gatt: BluetoothGatt?,
                status: Int,
                newState: Int
            ) {
                super.onConnectionStateChange(gatt, status, newState)
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    commandCharacteristic = gatt?.let { findCharacteristic(it) }
                    gatt?.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH)
                    Log.d("Bluetooth", "Connected to ${device.name}")
                    _bluetoothConnectionStatus.value = BluetoothConnectionStatus.Connected
                    bluetoothGatt?.discoverServices()
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    _bluetoothConnectionStatus.value = BluetoothConnectionStatus.Disconnected

                    scope.launch {
                        _connectionResultFlow.emit(ConnectionResult.Established)
                    }
                    stopListening()
                    close()
                    gatt?.close()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    scope.launch {
                        _connectionResultFlow.emit(ConnectionResult.Transferred(InfoDataModel("Connected")))
                    }
                    startListeningForData()
                    startListeningForState()
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, value, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.i("asdasd", "onCharacteristicRead: ${characteristic.uuid}")
                    when (characteristic.uuid.toString()) {
                        CHARACTERISTIC_INFO_UUID -> {
                            bluetoothGatt?.setCharacteristicNotification(characteristic, true)
                            frmtSnsrData(value)
                        }

                        TARGET_CHARACTERISTIC_UUID -> {
                            bluetoothGatt?.setCharacteristicNotification(characteristic, true)
                            val stateResult = String(value)
                            setStateButtons(stateResult)
                        }
                    }
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                @Suppress("DEPRECATION")
                super.onCharacteristicRead(gatt, characteristic, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    @Suppress("DEPRECATION")
                    val value = characteristic?.value

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                        when (characteristic?.uuid.toString()) {
                            CHARACTERISTIC_INFO_UUID -> {
                                bluetoothGatt?.setCharacteristicNotification(characteristic, true)
                                if (value != null) {
                                    frmtSnsrData(value)
                                }
                            }

                            TARGET_CHARACTERISTIC_UUID -> {
                                bluetoothGatt?.setCharacteristicNotification(characteristic, true)
                                val stateResult = value?.let { String(it) }
                                if (stateResult != null) {
                                    setStateButtons(stateResult)
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun startListeningForData() {
        isListening = true
        scope.launch {
            while (isListening) {
                bluetoothGatt?.services?.flatMap { it.characteristics }
                    ?.forEach { characteristic ->
                        if (characteristic.uuid.toString() == CHARACTERISTIC_INFO_UUID) {
                            bluetoothGatt?.readCharacteristic(characteristic)
                        }
                    }
                delay(DELAY_DURATION_1000)
            }
        }
    }

    private fun startListeningForState() {
        isListening = true
        scope.launch {
            while (isListening) {
                bluetoothGatt?.services?.flatMap { it.characteristics }?.forEach { characteristic ->
                    if (characteristic.uuid.toString() == TARGET_CHARACTERISTIC_UUID) {
                        bluetoothGatt?.readCharacteristic(characteristic)
                    }
                }
                delay(DELAY_DURATION_900)
            }
        }
    }

    private fun stopListening() {
        isListening = false
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action

            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as? BluetoothDevice
                    }

                device?.let {
                    scope.launch {
                        addScannedDevice(it)
                        if (!isConnecting && it.name == TARGET_DEVICE_NAME) {
                            isConnecting = true
                            _showLoading.emit(true)
                            stopScanning()
                            connectToDevice(it)
                        }
                    }
                }
            }
        }
    }

    override fun startScanning() {
        if (!PermissionUtil.canBluetoothConnect(context)) return

        isConnecting = false
        adapter?.startDiscovery()

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
    }

    override fun stopScanning() {
        adapter?.cancelDiscovery()
        try {
            context.unregisterReceiver(receiver)
        } catch (_: IllegalArgumentException) {
        }
    }

    private fun addScannedDevice(device: BluetoothDevice) {
        val domainModel = device.toBluetoothDevice().toBluetoothDeviceDomain()
        val deviceExists = _scannedDevices.value.any {
            it.macAddress == domainModel.macAddress
        }

        if (!deviceExists) {
            _scannedDevices.value += domainModel
        }
    }

    override fun close() {
        stopListening()
        try {
            bluetoothGatt?.close()
        } catch (_: IOException) {
        } finally {
            bluetoothGatt = null
        }
    }

    override suspend fun sendCommand(
        cmd: String,
        onSuccess: (() -> Unit)?,
        onFailed: (() -> Unit)?
    ) {
        val characteristic = commandCharacteristic ?: bluetoothGatt?.let { findCharacteristic(it) }
        if (bluetoothGatt == null || characteristic == null) {
            Log.e("Bluetooth", "BluetoothGatt or characteristic is not available")
            onFailed?.invoke()
            return
        }

        if (cmd.isNotEmpty() && characteristic.properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
            val binCmd = cmd.toByteArray(Charsets.UTF_8)
            sendWithRetry(binCmd, characteristic, onSuccess, onFailed)
        } else {
            Log.e("Bluetooth", "Characteristic does not support write")
            onFailed?.invoke()
        }

        if (cmd == COMMAND_STOP) {
            close()
            return
        }
    }

    private suspend fun sendWithRetry(
        binCmd: ByteArray,
        characteristic: BluetoothGattCharacteristic,
        onSuccess: (() -> Unit)?,
        onFailed: (() -> Unit)?
    ) {
        withContext(Dispatchers.IO) {
            var attempt = 0
            var delayTime = 500L

            while (attempt < 3) {
                if (bluetoothGatt?.device?.bondState != BluetoothDevice.BOND_BONDED) {
                    bluetoothGatt?.connect()
                }

                val success = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    sendCommandTiramisu(binCmd, characteristic)
                } else {
                    sendCommandPreTiramisu(binCmd, characteristic)
                }

                if (success) {
                    Log.d("Bluetooth", "Command sent: ${String(binCmd)}")
                    onSuccess?.invoke()
                    return@withContext
                } else {
                    Log.e("Bluetooth", "Failed to send command, attempt:$attempt ${String(binCmd)}")
                    attempt++
                    delay(delayTime)
                    delayTime *= 2
                }
            }

            Log.e("Bluetooth", "Command failed after max attempts")
            onFailed?.invoke()
        }
    }

    private fun sendCommandTiramisu(
        binCmd: ByteArray,
        characteristic: BluetoothGattCharacteristic
    ): Boolean {
        val result = bluetoothGatt?.writeCharacteristic(
            characteristic,
            binCmd,
            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
        )
        return result == BluetoothStatusCodes.SUCCESS
    }

    @Suppress("DEPRECATION")
    private fun sendCommandPreTiramisu(
        binCmd: ByteArray,
        characteristic: BluetoothGattCharacteristic
    ): Boolean {
        characteristic.value = binCmd
        return bluetoothGatt?.writeCharacteristic(characteristic) == true
    }

    private fun findCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        val service =
            gatt.services.find { it.uuid.toString() == TARGET_SERVICE_UUID } ?: return null
        return service.characteristics.find { it.uuid.toString() == TARGET_CHARACTERISTIC_UUID }
    }

    private fun frmtSnsrData(data: ByteArray) {
        if (data.isNotEmpty()) {
            Log.e("asdsadsad", "frmtSnsrData: ${data[0]}", )
            var temrTmpr1 = maxOf(data[0].toDouble().roundToInt(), 0)
            val temrIR1 = ((data[2].toInt() shl 8) + data[3].toInt()).toDouble() * 0.02 - 273.15
            val temrIR2 = ((data[4].toInt() shl 8) + data[5].toInt()).toDouble() * 0.02 - 273.15
            val snsrHC = maxOf(data[6].toInt(), 0)
            val thermostat = data[8].toInt() == 1
            val stateDevice = data[10].toInt()

            Log.d(
                "log snsr", """
            |data ${data.joinToString(", ")}
            |temrTmpr1 $temrTmpr1
            |tempIR1 $temrIR1
            |tempIR2 $temrIR2
            |HeartCounter $snsrHC
            |thermostat $thermostat
            |statedevice ${data[9].toInt()}, $stateDevice
        """.trimMargin()
            )

            lastTemrTmpr1?.let { lastValue ->
                if (temrTmpr1 in (lastValue - 10)..(lastValue + 10)) {
                    lastTemrTmpr1 = temrTmpr1
                    anomalyCount = 0
                } else {
                    anomalyCount++
                    if (anomalyCount >= 5) {
                        lastTemrTmpr1 = temrTmpr1
                        anomalyCount = 0
                    } else {
                        temrTmpr1 = lastValue
                    }
                }
            } ?: run {
                lastTemrTmpr1 = temrTmpr1
            }

            val skinTemperature = when {
                temrIR1 > 0 && temrIR2 > 0 -> (temrIR1 + temrIR2) / 2
                temrIR1 > 0 -> temrIR1
                temrIR2 > 0 -> temrIR2
                else -> lastValidSkinTemperature ?: 0.0
            }

            if (skinTemperature > 0) {
                lastValidSkinTemperature = skinTemperature
            }

            val sensorData = SensorData(
                temrTmpr1 = temrTmpr1,
                temrIR1 = temrIR1,
                temrIR2 = temrIR2,
                skinTemperature = skinTemperature,
                snsrHC = snsrHC,
                thermostat = thermostat,
                stateDevice = stateDevice
            )

            scope.launch {
                _sensorDataFlow.emit(sensorData)
            }
        }
    }

    private fun cancelAndGetPairedDevices() {
        scope.launch {
            getPairedDevices()
        }
    }

    private suspend fun getPairedDevices() {
        if (!PermissionUtil.canBluetoothConnect(context)) return
        val devices =
            adapter?.bondedDevices?.map { it.toBluetoothDevice().toBluetoothDeviceDomain() }
        if (devices != null) _pairedDevices.emit(devices)
    }

    private fun setStateButtons(ss: String) {
        Log.e("Bluetooth", "setStateButtons: ${ss}")
        scope.launch {
            _getCommandsState.emit(ss)
        }
    }
}

const val DELAY_DURATION_900 = 900L
const val DELAY_DURATION_1000 = 1000L
const val DELAY_DURATION_2000 = 2000L
