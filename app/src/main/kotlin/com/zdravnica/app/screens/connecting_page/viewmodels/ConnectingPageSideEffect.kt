package com.zdravnica.app.screens.connecting_page.viewmodels

sealed interface ConnectingPageSideEffect {

    data object OnShowAllDevicesDialog : ConnectingPageSideEffect
}