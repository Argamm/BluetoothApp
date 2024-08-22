package com.zdravnica.app.screens.procedure.viewModels

sealed interface ProcedureScreenSideEffect {
    data object OnNavigateUp : ProcedureScreenSideEffect

    data class OnOptionSelected(
        val selectedOption: Int,
    ) : ProcedureScreenSideEffect
}