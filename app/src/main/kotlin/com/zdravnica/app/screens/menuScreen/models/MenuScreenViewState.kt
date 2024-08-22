package com.zdravnica.app.screens.menuScreen.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class MenuScreenViewState(
    val uiModel: MenuScreenUiModel = MenuScreenUiModel(),
) : BaseViewState()