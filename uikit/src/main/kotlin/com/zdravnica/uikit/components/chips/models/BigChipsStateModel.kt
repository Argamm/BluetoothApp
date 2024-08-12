package com.zdravnica.uikit.components.chips.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class BigChipsStateModel(
    val isEnabled: Boolean,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val iconRes: Int? = null
)
