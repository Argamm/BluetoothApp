package com.zdravnica.app.screens.connecting_page.selectProcedure.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel

@Immutable
data class SelectProcedureViewState(
    val isConnected: Boolean = false,
    val temperature: Int = 0,
    val bigChipsStateModel: List<BigChipsStateModel> = listOf(),
) : BaseViewState()