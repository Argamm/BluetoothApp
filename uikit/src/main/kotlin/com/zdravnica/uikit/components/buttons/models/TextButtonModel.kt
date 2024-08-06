package com.zdravnica.uikit.components.buttons.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Immutable
data class TextButtonModel(
    val buttonText: String,
    val textModifier: Modifier = Modifier,
    val isEnabled: Boolean = true,
    val textStyle: TextStyle? = null,
    val textButtonType: TextButtonType = TextButtonType.PRIMARY,
    val onClick: (() -> Unit)? = null
)


@Stable
enum class TextButtonType {
    PRIMARY, SECONDARY, TERTIARY
}

