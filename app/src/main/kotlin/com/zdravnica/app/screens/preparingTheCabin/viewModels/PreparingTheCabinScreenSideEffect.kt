package com.zdravnica.app.screens.preparingTheCabin.viewModels

sealed interface PreparingTheCabinScreenSideEffect {
    data object OnNavigateToSelectProcedureScreen : PreparingTheCabinScreenSideEffect

    data object OnNavigateToCancelDialogPage : PreparingTheCabinScreenSideEffect
}