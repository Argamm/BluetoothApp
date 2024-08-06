package com.zdravnica.uikit.components.chips.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class BigChipsStateModel(
    val isEnabled: Boolean,
    val title: String,
    val description: String,
    @DrawableRes val iconRes: Int
)
