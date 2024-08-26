package com.zdravnica.app.screens.procedure.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ProcedureScreenViewState(
    val balmCount: Int = 0,
) : BaseViewState()