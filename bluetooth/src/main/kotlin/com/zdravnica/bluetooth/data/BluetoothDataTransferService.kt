package com.zdravnica.bluetooth.data

import android.bluetooth.BluetoothSocket
import com.zdravnica.bluetooth.data.mapper.toMessage
import com.zdravnica.bluetooth.data.models.InfoDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException

class BluetoothDataTransferService(
    private val socket: BluetoothSocket
) {
    suspend fun sendData(data: InfoDataModel): Boolean {

        return withContext(Dispatchers.IO) {
            try {
                socket.outputStream.write(data.info.toByteArray())
                return@withContext true
            } catch (e: IOException) {
                return@withContext false
            }
        }
    }

    fun getData(): Flow<InfoDataModel> {
        return flow {
            val buffer = ByteArray(BYTE_ARRAY_SIZE)
            while (socket.isConnected) {
                val count = socket.inputStream.read(buffer)
                val message = buffer.toMessage(
                    count = count
                )
                emit(message)
            }
        }.flowOn(Dispatchers.IO)
    }
}