package com.zdravnica.app.screens.connecting_page.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ConnectingPageViewState(
    val isBtConnected: Boolean = false,
    val isLoading: Boolean = false,
    val pairedDevices: SnapshotStateList<DeviceUIModel> = mutableStateListOf(),
    val scannedDevices: SnapshotStateList<DeviceUIModel> = mutableStateListOf(),
) : BaseViewState()
