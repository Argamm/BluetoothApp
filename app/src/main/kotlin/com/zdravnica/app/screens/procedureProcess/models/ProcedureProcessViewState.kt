package com.zdravnica.app.screens.procedureProcess.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ProcedureProcessViewState(
    val isDialogVisible: Boolean = false,
    val sensorTemperature: Int = 0,
    val calorieValue: Int = 0,
    val pulse: Int = 0,
) : BaseViewState()