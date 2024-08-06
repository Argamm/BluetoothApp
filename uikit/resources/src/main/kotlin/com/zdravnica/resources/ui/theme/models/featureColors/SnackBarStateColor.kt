package com.zdravnica.resources.ui.theme.models.featureColors

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class SnackBarStateColor(
    val successBackgroundColor: Color,
    val successContentColor: Color,
    val warningBackgroundColor: Color,
    val warningContentColor: Color,
    val errorBackgroundColor: Color,
    val errorContentColor: Color
)
