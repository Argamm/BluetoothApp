package com.zdravnica.app.screens.connecting_page.procedure.viewModels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.procedure.models.ProcedureScreenViewState
import org.orbitmvi.orbit.viewmodel.container

class ProcedureScreenViewModel : BaseViewModel<ProcedureScreenViewState, ProcedureScreenSideEffect>()  {

    override val container =
        container<ProcedureScreenViewState, ProcedureScreenSideEffect>(
            ProcedureScreenViewState()
        )

    fun onNavigateUp(){
        postSideEffect(ProcedureScreenSideEffect.OnNavigateUp)
    }

    fun onOptionSelected(selectedOption: Int){
        postSideEffect(ProcedureScreenSideEffect.OnOptionSelected(selectedOption))
    }
}