package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class BigChipsStateColor(
    val backgroundGradientColors: List<Color>,
    val enabledTitleColor: Color,
    val enabledDescriptionColor: Color,
    val disabledContentColor: Color,
    val borderStrokeGradientColors: List<Color>
)
