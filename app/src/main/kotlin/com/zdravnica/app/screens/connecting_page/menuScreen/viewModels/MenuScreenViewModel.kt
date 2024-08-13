package com.zdravnica.app.screens.connecting_page.menuScreen.viewModels

import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.screens.connecting_page.menuScreen.models.MenuScreenViewState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MenuScreenViewModel : BaseViewModel<MenuScreenViewState, MenuScreenSideEffect>() {
    override val container =
        container<MenuScreenViewState, MenuScreenSideEffect>(
            MenuScreenViewState()
        )

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(uiModel = state.uiModel.copy(idDialogVisible = isVisible))
        }
    }

    fun navigateGToConnectionScreen(){
        postSideEffect(MenuScreenSideEffect.OnNavigateToConnectionScreen)
    }

    fun onNavigateUp(){
        postSideEffect(MenuScreenSideEffect.OnNavigateUp)
    }

    fun onSiteClick(){
        postSideEffect(MenuScreenSideEffect.OnSiteClick)
    }

    fun onEmailClick(){
        postSideEffect(MenuScreenSideEffect.OnEmailClick)
    }

    fun onCallClick(){
        postSideEffect(MenuScreenSideEffect.OnCallClick)
    }
}