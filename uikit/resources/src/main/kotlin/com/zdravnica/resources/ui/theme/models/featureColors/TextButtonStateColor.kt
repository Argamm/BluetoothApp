package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable

@Stable
data class TextButtonStateColor(
    val primaryButtonStateColor: PrimaryButtonState,
    val secondaryButtonState: SecondaryButtonState,
    val tertiaryButtonState: TertiaryButtonState
)
