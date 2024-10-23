package com.zdravnica.app.screens.procedure.viewModels

sealed interface ProcedureScreenSideEffect {
    data object OnNavigateUp : ProcedureScreenSideEffect
    data class OnOptionSelected(val selectedOption: Int) : ProcedureScreenSideEffect
    data object OnNavigateToFailedTenCommandScreen : ProcedureScreenSideEffect
    data object OnNavigateToFailedFanCommandScreen : ProcedureScreenSideEffect
    data object OnBluetoothConnectionLost : ProcedureScreenSideEffect
}