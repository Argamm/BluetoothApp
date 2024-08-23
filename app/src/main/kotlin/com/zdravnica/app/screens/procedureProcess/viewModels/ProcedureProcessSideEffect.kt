package com.zdravnica.app.screens.procedureProcess.viewModels

sealed interface ProcedureProcessSideEffect {

    data object OnNavigateToMainScreen : ProcedureProcessSideEffect

    data object OnNavigateToCancelDialogPage : ProcedureProcessSideEffect
}