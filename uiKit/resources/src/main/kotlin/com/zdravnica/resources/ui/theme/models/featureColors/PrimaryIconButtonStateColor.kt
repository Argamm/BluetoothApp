package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class PrimaryIconButtonStateColor(
    val defaultBackgroundGradientColors: List<Color>,
    val pressedBackgroundGradientColors: List<Color>,
    val disabledBackgroundGradientColor: List<Color>,
    val defaultBorderGradientColor: List<Color>,
    val pressedBorderGradientColor: List<Color>,
    val disabledBorderGradientColor: List<Color>,
    val defaultContentColor: Color,
    val disabledContentColor: Color,
    val pressedContentColor: Color
)
