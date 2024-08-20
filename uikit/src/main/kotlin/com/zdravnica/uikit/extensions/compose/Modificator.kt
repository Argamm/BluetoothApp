package com.zdravnica.uikit.extensions.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import com.zdravnica.uikit.COUNT_TO_100

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    isEnabledIndication: Boolean = true,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
        properties["isEnabledIndication"] = isEnabledIndication
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    this.then(
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { multipleEventsCutter.processEvent(onClick) },
            role = role,
            indication = if (isEnabledIndication) LocalIndication.current else null,
            interactionSource = remember {
                MutableInteractionSource()
            }
        )
    )
}

fun Int.formatAsValue(isMinutes: Boolean): String {
    return if (isMinutes) {
        val minutes = (this / NUMBER_FOR_COUNT_60).toString().padStart(NUMBER_FOR_COUNT_2, '0')
        val seconds = (this % NUMBER_FOR_COUNT_60).toString().padStart(NUMBER_FOR_COUNT_2, '0')
        "$minutes:$seconds"
    } else {
        "$this°"
    }
}

fun getValueRange(isMinutes: Boolean): Pair<Int, Int> {
    return if (isMinutes) {
        MIN_MINUTES to MAX_MINUTES
    } else {
        MIN_TEMPERATURE to MAX_TEMPERATURE
    }
}

fun calculateProgress(currentTemperature: Int, targetTemperature: Int): Int {
    val maxTemperature = targetTemperature
    val minTemperature = 0
    val percentage = ((currentTemperature - minTemperature).toFloat() / (maxTemperature - minTemperature)) * COUNT_TO_100
    return percentage.toInt().coerceIn(0, COUNT_TO_100)
}

const val NUMBER_FOR_COUNT_60 = 60
const val NUMBER_FOR_COUNT_2 = 2
const val MAX_MINUTES = 1800
const val MIN_MINUTES = 600
const val MAX_TEMPERATURE = 80
const val MIN_TEMPERATURE = 40
