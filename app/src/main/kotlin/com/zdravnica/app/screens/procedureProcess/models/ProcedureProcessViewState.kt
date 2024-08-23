package com.zdravnica.app.screens.procedureProcess.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ProcedureProcessViewState(
    val uiModel: ProcedureProcessUiModel = ProcedureProcessUiModel(),
) : BaseViewState()