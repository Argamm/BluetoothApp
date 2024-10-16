package com.zdravnica.app.screens.menuScreen.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class MenuScreenViewState(
    val idDialogVisible: Boolean = false,
    val temperature: Int = 0,
    val isTemperatureDifferenceLarge: Boolean = false,
    val firstBalmCount: Float = 0f,
    val secondBalmCount: Float = 0f,
    val thirdBalmCount: Float = 0f,
) : BaseViewState()