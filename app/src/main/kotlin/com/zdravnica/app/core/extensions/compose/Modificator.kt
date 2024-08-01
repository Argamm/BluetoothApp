package com.zdravnica.app.core.extensions.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

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
