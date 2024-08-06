package com.zdravnica.bluetooth.data.models

sealed interface ConnectionResult {
    data object Established : ConnectionResult
    data class Transferred(val infoDataModel: InfoDataModel) : ConnectionResult
    data class Error(val message: String) : ConnectionResult
}