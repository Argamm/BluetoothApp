package com.zdravnica.app.screens.connecting_page.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ConnectingPageViewState(
    val isBtConnected: Boolean = false,
    val isLoading: Boolean = false
) : BaseViewState()
