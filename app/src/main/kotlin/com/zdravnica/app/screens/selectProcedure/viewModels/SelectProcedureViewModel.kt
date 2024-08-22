package com.zdravnica.app.screens.selectProcedure.viewModels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import org.orbitmvi.orbit.viewmodel.container

class SelectProcedureViewModel() : BaseViewModel<SelectProcedureViewState, SelectProcedureSideEffect>() {

    override val container =
        container<SelectProcedureViewState, SelectProcedureSideEffect>(
            SelectProcedureViewState()
        )

    fun navigateToMenuScreen(){
        postSideEffect(SelectProcedureSideEffect.OnNavigateToMenuScreen)
    }

    fun onProcedureCardClick(bigChipsStateModel: BigChipsStateModel){
        postSideEffect(SelectProcedureSideEffect.OnProcedureCardClick(bigChipsStateModel))
    }
}