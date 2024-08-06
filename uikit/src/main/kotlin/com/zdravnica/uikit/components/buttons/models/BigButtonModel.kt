package com.zdravnica.uikit.components.buttons.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.zdravnica.uikit.resources.R

@Immutable
data class BigButtonModel(
    val buttonText: String,
    val textModifier: Modifier = Modifier,
    val isEnabled: Boolean = true,
    val textStyle: TextStyle? = null,
    val onClick: (() -> Unit)? = null,
    @DrawableRes val iconRes: Int = R.drawable.ic_plus
)
