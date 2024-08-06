package com.zdravnica.bluetooth.data.mapper

import com.zdravnica.bluetooth.data.models.InfoDataModel

fun ByteArray.toMessage(count: Int): InfoDataModel {
    val data = decodeToString(endIndex = count)
    val message = data.substringAfter("#")
    return InfoDataModel(
        info = message
    )
}