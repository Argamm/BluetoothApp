package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class IconButtonStateColor(
    val primaryButtonStateColor: PrimaryButtonState,
    val secondaryButtonState: SecondaryButtonState,
    val tertiaryButtonState: TertiaryButtonState
)

@Stable
data class PrimaryButtonState(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val defaultContentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color
)

@Stable
data class SecondaryButtonState(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val defaultContentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color
)

@Stable
data class TertiaryButtonState(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val defaultContentColor: Color,
    val pressedContentColor: Color,
    val disabledContentColor: Color
)
