package com.zdravnica.uikit.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.MAX_MINUTES
import com.zdravnica.uikit.MAX_TEMPERATURE
import com.zdravnica.uikit.MIN_MINUTES
import com.zdravnica.uikit.MIN_TEMPERATURE

fun calculateProgress(currentTemperature: Int, targetTemperature: Int): Int {
    val minTemperature = 0
    val percentage = ((currentTemperature - minTemperature).toFloat() / (targetTemperature - minTemperature)) * COUNT_TO_100
    return percentage.toInt().coerceIn(0, COUNT_TO_100)
}

fun getValueRange(isMinutes: Boolean): Pair<Int, Int> {
    return if (isMinutes) {
        MIN_MINUTES to MAX_MINUTES
    } else {
        MIN_TEMPERATURE to MAX_TEMPERATURE
    }
}

@Composable
fun buildGradientAnnotatedString(
    titleRes: Int?,
    gradientColors: List<Color>
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
        ) {
            append(titleRes?.let { stringResource(id = it) } ?: "")
        }
    }
}