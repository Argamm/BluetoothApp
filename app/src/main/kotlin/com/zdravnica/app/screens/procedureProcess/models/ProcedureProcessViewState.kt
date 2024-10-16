package com.zdravnica.app.screens.procedureProcess.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState
import com.zdravnica.uikit.base_type.IconState

@Immutable
data class ProcedureProcessViewState(
    val isDialogVisible: Boolean = false,
    val sensorTemperature: Int = 0,
    val calorieValue: Int = 0,
    val pulse: Int = 0,
    val iconStates: List<IconState> = listOf(
        IconState.DISABLED,
        IconState.DISABLED,
        IconState.DISABLED,
        IconState.DISABLED
    ),
) : BaseViewState()