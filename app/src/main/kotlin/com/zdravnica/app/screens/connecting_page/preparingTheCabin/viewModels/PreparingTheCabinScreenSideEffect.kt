package com.zdravnica.app.screens.connecting_page.preparingTheCabin.viewModels

sealed interface PreparingTheCabinScreenSideEffect {
    data object OnNavigateToSelectProcedureScreen : PreparingTheCabinScreenSideEffect
}