package com.zdravnica.app.screens.connecting_page.selectProcedure.viewModels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.selectProcedure.models.SelectProcedureViewState
import org.orbitmvi.orbit.viewmodel.container

class SelectProcedureViewModel() : BaseViewModel<SelectProcedureViewState, SelectProcedureSideEffect>() {

    override val container =
        container<SelectProcedureViewState, SelectProcedureSideEffect>(
            SelectProcedureViewState()
        )
}