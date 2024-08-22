package com.zdravnica.app.screens.menuScreen.models

import androidx.compose.runtime.Immutable

@Immutable
data class MenuScreenUiModel (
    val idDialogVisible: Boolean = false,
    val temperature: Int = 0,
)