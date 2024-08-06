package com.zdravnica.uikit.components.buttons.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Immutable
data class PrimaryButtonByTextModel(
    val buttonText: String,
    val textModifier: Modifier = Modifier,
    val isEnabled: Boolean = true,
    val textStyle: TextStyle? = null,
    val onClick: (() -> Unit)? = null
)
