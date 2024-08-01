package com.zdravnica.app.screens.connecting_page.viewmodels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.bluetooth.data.BluetoothController
import org.orbitmvi.orbit.viewmodel.container

class ConnectingPageViewModel(
    private val bluetoothController: BluetoothController
) : BaseViewModel<ConnectingPageViewState, ConnectingPageSideEffect>() {


    override val container =
        container<ConnectingPageViewState, ConnectingPageSideEffect>(
            ConnectingPageViewState())



}
