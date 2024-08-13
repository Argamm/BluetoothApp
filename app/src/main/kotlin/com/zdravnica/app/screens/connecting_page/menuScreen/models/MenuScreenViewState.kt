package com.zdravnica.app.screens.connecting_page.menuScreen.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class MenuScreenViewState(
    val uiModel: MenuScreenUiModel = MenuScreenUiModel(),
) : BaseViewState()