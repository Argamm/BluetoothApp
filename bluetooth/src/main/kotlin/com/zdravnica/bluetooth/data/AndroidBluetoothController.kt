package com.zdravnica.bluetooth.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import com.zdravnica.bluetooth.data.mapper.toBluetoothDevice
import com.zdravnica.bluetooth.data.mapper.toBluetoothDeviceDomain
import com.zdravnica.bluetooth.data.models.BluetoothDeviceDataModel
import com.zdravnica.bluetooth.data.models.ConnectionResult
import com.zdravnica.bluetooth.data.models.InfoDataModel
import com.zdravnica.bluetooth.domain.controller.BluetoothController
import com.zdravnica.bluetooth.domain.models.BluetoothDeviceDomainModel
import com.zdravnica.bluetooth.util.PermissionUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
internal class AndroidBluetoothController(
    private val context: Context,
    private val adapter: BluetoothAdapter?
) : BluetoothController {

    private var service: BluetoothDataTransferService? = null
    private var serverSocket: BluetoothServerSocket? = null
    private var clientSocket: BluetoothSocket? = null
    private var getPairedDevicesJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // TODO create business logic for info scanned devices
    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomainModel>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomainModel>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomainModel>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomainModel>>
        get() = _pairedDevices.asStateFlow()

    override fun bluetoothIsEnabled(): Boolean = adapter?.isEnabled ?: false

    override fun refreshPairedDevices() = cancelAndGetPairedDevices()

    override fun connect(device: BluetoothDeviceDataModel): Flow<ConnectionResult> {
        return flow {
            if (!PermissionUtil.canBluetoothConnect(context)) {
                emit(ConnectionResult.Error("No BLUETOOTH_CONNECT permission!"))
                return@flow
            }

            try {
                clientSocket = adapter
                    ?.getRemoteDevice(device.macAddress)
                    ?.createRfcommSocketToServiceRecord(UUID.fromString(SERVICE_UUID))

                clientSocket?.connect()

                emit(ConnectionResult.Established)

                clientSocket?.let { socket ->
                    service = BluetoothDataTransferService(socket)
                    emitAllMessages(this)
                }

            } catch (e: IOException) {
                emit(ConnectionResult.Error("Connection was interrupted!"))
            }
        }.onCompletion {
            close()
        }.flowOn(Dispatchers.IO)
    }

    override fun close() {
        serverSocket?.close()
        serverSocket = null
        clientSocket?.close()
        clientSocket = null
    }

    override suspend fun sendData(sendData: InfoDataModel): InfoDataModel? {
        if (!PermissionUtil.canBluetoothConnect(context)) return null
        if (service == null) return null
        val model = InfoDataModel(
            info = sendData.info,
        )
        val success = service?.sendData(model) ?: false
        return if (success) model else null
    }

    private fun cancelAndGetPairedDevices() {
        getPairedDevicesJob?.cancel()
        getPairedDevicesJob = scope.launch {
            getPairedDevices()
        }
    }

    private suspend fun getPairedDevices() {
        if (!PermissionUtil.canBluetoothConnect(context)) return
        val devices = adapter
            ?.bondedDevices
            ?.map { it.toBluetoothDevice().toBluetoothDeviceDomain() }
        if (devices != null) _pairedDevices.emit(devices)
    }

    private suspend fun emitAllMessages(collector: FlowCollector<ConnectionResult>) {
        service?.let { currentService ->
            collector.emitAll(
                currentService.getData()
                    .catch { e ->
                        if (e !is IOException) throw e
                        collector.emit(ConnectionResult.Error("Connection was interrupted!"))
                    }.map { ConnectionResult.Transferred(it) }
            )
        }
    }
}
