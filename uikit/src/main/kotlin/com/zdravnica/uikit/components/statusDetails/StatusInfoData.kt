package com.zdravnica.uikit.components.statusDetails

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class StatusInfoData(
    @DrawableRes val icon: Int,
    val stateInfo: Int,
    val instruction: Int
)