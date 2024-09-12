package com.zdravnica.app.screens.connecting_page.viewmodels

sealed interface ConnectingPageSideEffect {
    data object OnShowAllDevicesDialog : ConnectingPageSideEffect
    data object OnSuccess : ConnectingPageSideEffect
    data object OnError : ConnectingPageSideEffect
    data object OnEstablished : ConnectingPageSideEffect
    data object OnCloseDialog : ConnectingPageSideEffect
}