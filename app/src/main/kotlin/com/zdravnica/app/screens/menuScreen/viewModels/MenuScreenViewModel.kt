package com.zdravnica.app.screens.menuScreen.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.menuScreen.models.MenuScreenViewState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MenuScreenViewModel(
    localDataStore: LocalDataStore
) : BaseViewModel<MenuScreenViewState, MenuScreenSideEffect>() {

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    override val container =
        container<MenuScreenViewState, MenuScreenSideEffect>(
            MenuScreenViewState()
        )

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(uiModel = state.uiModel.copy(idDialogVisible = isVisible))
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(MenuScreenSideEffect.OnNavigateToCancelDialogPage)
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