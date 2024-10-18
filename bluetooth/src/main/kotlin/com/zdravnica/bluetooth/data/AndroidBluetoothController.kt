package com.zdravnica.bluetooth.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothStatusCodes
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.zdravnica.bluetooth.data.mapper.toBluetoothDevice
import com.zdravnica.bluetooth.data.mapper.toBluetoothDeviceDomain
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
import kotlinx.coroutines.flow.onCompletion
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
    private var serverSocket: BluetoothServerSocket? = null
    private var clientSocket: BluetoothSocket? = null
    private var isListening = false

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
        }.onCompletion {
            close()
        }.flowOn(Dispatchers.IO)
    }

    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(
                gatt: BluetoothGatt?,
                status: Int,
                newState: Int
            ) {
                super.onConnectionStateChange(gatt, status, newState)
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    Log.d("Bluetooth", "Connected to ${device.name}")

                    scope.launch {
                        delay(DELAY_DURATION_1000)
                        sendCommand(COMMAND_START)
                        _connectionResultFlow.emit(ConnectionResult.Transferred(InfoDataModel("Connected")))
                    }
                    bluetoothGatt?.discoverServices()
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    scope.launch {
                        _connectionResultFlow.emit(ConnectionResult.Established)
                    }
                    stopListening()
                    close()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    startListeningForData()
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
                    bluetoothGatt?.setCharacteristicNotification(characteristic, true)
                    frmtSnsrData(value)
                }
            }
        })
    }

    private fun startListeningForData() {
        isListening = true
        scope.launch {
            while (isListening) {
                bluetoothGatt?.services?.flatMap { it.characteristics }?.forEach { characteristic ->
                    if (characteristic.uuid.toString() == CHARACTERISTIC_INFO_UUID) {
                        bluetoothGatt?.readCharacteristic(characteristic)
                    }
                }
                delay(DELAY_DURATION_1000)
            }
        }
    }

    private fun stopListening() {
        isListening = false
    }

    override fun startScanning() {
        if (!PermissionUtil.canBluetoothConnect(context)) return

        adapter?.startDiscovery()
        val receiver = object : BroadcastReceiver() {
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
                            if (it.name == TARGET_DEVICE_NAME) {
                                _showLoading.emit(true)
                                delay(DELAY_DURATION_2000)
                                stopScanning()
                                connectToDevice(it)
                            }
                        }
                    }
                }
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
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

    override fun stopScanning() {
        if (adapter?.isDiscovering == true) {
            adapter.cancelDiscovery()
        }
    }

    override fun close() {
        stopListening()
        try {
            serverSocket?.close()
            clientSocket?.close()
            bluetoothGatt?.close()
        } catch (_: IOException) {
        } finally {
            serverSocket = null
            clientSocket = null
            bluetoothGatt = null
        }
    }

    override suspend fun sendCommand(cmd: String, onSuccess: (() -> Unit)?) {
        if (cmd == COMMAND_STOP) {
            close()
            return
        }
        val gatt = bluetoothGatt ?: return
        val characteristic = findCharacteristic(gatt) ?: return

        if (cmd.isNotEmpty()) {
            val binCmd = cmd.toByteArray(Charsets.UTF_8)
            withContext(Dispatchers.IO) {
                var attempt = 0

                if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
                    try {
                        while (attempt < 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                val result = gatt.writeCharacteristic(
                                    characteristic,
                                    binCmd,
                                    BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                                )
                                if (result == BluetoothStatusCodes.SUCCESS) {
                                    Log.d("Bluetooth", "Command sent: $cmd")
                                    onSuccess?.invoke()
                                    break
                                } else {
                                    attempt++
                                    delay(DELAY_DURATION_500)
                                    Log.e("Bluetooth", "Failed to send command: $cmd")
                                }
                            } else {
                                @Suppress("DEPRECATION")
                                characteristic.value = binCmd
                                @Suppress("DEPRECATION") val result =
                                    gatt.writeCharacteristic(characteristic)
                                if (result) {
                                    Log.d("Bluetooth", "Command sent: $cmd")
                                    onSuccess?.invoke()
                                    break
                                } else {
                                    attempt++
                                    delay(DELAY_DURATION_500)
                                    Log.e("Bluetooth", "Failed to send command: $cmd")
                                }
                            }
                        }
                        delay(DELAY_DURATION_1000)
                    } catch (e: Exception) {
                        Log.e("Bluetooth", "Error sending command: ${e.message}")
                    }
                } else {
                    Log.e("Bluetooth", "Characteristic does not support write")
                }
            }
        }
    }

    private fun findCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        val service =
            gatt.services.find { it.uuid.toString() == TARGET_SERVICE_UUID } ?: return null
        return service.characteristics.find { it.uuid.toString() == TARGET_CHARACTERISTIC_UUID }
    }

    private fun frmtSnsrData(data: ByteArray) {
        if (data.isNotEmpty()) {
            val temrTmpr1 = data[0].toDouble().roundToInt()
            val temrIR1 = ((data[2].toInt() shl 8) + data[3].toInt()).toDouble() * 0.02 - 273.15
            val temrIR2 = ((data[4].toInt() shl 8) + data[5].toInt()).toDouble() * 0.02 - 273.15
            val snsrHC = data[6].toInt()
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

            val sensorData = SensorData(
                temrTmpr1 = temrTmpr1,
                temrIR1 = temrIR1,
                temrIR2 = temrIR2,
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
}

const val DELAY_DURATION_500 = 500L
const val DELAY_DURATION_1000 = 1000L
const val DELAY_DURATION_2000 = 2000L
const val DELAY_DURATION_3000 = 3000L
