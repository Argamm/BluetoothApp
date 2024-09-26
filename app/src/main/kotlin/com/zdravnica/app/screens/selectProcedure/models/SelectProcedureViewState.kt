package com.zdravnica.app.screens.selectProcedure.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel

@Immutable
data class SelectProcedureViewState(
    val isConnected: Boolean = false,
    val temperature: Int = 0,
    val duration: Int = 0,
    val ikSwitchState: Boolean = false,
    val isButtonVisible: Boolean = true,
    val scrollToEnd: Boolean = false,
    val bigChipsList: List<BigChipsStateModel> = listOf(),
    val isShowingSnackBar: Boolean = true,
) : BaseViewState()