package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class BigButtonStateColor(
    val enabledBackground: Color,
    val pressedBackground: Color,
    val disabledBackground: Color,
    val borderStrokeColor: Color,
    val enabledContentColor: Color,
    val disabledContentColor: Color,
)
