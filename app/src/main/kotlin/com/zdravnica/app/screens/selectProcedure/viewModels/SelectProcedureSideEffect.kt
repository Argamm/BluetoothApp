package com.zdravnica.app.screens.selectProcedure.viewModels

import com.zdravnica.uikit.components.chips.models.BigChipsStateModel

sealed interface SelectProcedureSideEffect {
    data object OnNavigateToMenuScreen : SelectProcedureSideEffect
    data class OnProcedureCardClick(val chipData: BigChipsStateModel) : SelectProcedureSideEffect
    data object OnBluetoothConnectionLost: SelectProcedureSideEffect
}