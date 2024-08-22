package com.zdravnica.app.screens.procedure.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class ProcedureScreenViewState(
    val someData: Int = 0,
) : BaseViewState()