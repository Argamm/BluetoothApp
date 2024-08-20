package com.zdravnica.app.screens.connecting_page.preparingTheCabin.viewModels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.preparingTheCabin.models.PreparingTheCabinScreenViewState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PreparingTheCabinScreenViewModel :
    BaseViewModel<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>() {

    override val container =
        container<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>(
            PreparingTheCabinScreenViewState()
        )

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(uiModel = state.uiModel.copy(isDialogVisible = isVisible))
        }
    }

    fun navigateToSelectProcedureScreen() {
        postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToSelectProcedureScreen)
    }
}