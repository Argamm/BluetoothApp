package com.zdravnica.app.screens.preparingTheCabin.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState
import com.zdravnica.uikit.base_type.IconState

@Immutable
data class PreparingTheCabinScreenViewState(
    val isDialogVisible: Boolean = false,
    val sensorTemperature: Int = 0,
    val isTemperatureDifferenceLarge: Boolean = false,
    val iconStates: List<IconState> = listOf(
        IconState.DISABLED,
        IconState.DISABLED,
        IconState.DISABLED,
        IconState.DISABLED
    )
) : BaseViewState()