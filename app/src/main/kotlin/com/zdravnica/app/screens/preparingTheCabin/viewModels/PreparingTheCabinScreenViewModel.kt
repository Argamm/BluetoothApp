package com.zdravnica.app.screens.preparingTheCabin.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.preparingTheCabin.models.PreparingTheCabinScreenViewState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PreparingTheCabinScreenViewModel(
    localDataStore: LocalDataStore
) : BaseViewModel<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>() {

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    override val container =
        container<PreparingTheCabinScreenViewState, PreparingTheCabinScreenSideEffect>(
            PreparingTheCabinScreenViewState()
        )

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(uiModel = state.uiModel.copy(isDialogVisible = isVisible))
        }

    }

    fun navigateToCancelDialogPage() {
        postSideEffect(PreparingTheCabinScreenSideEffect.OnNavigateToCancelDialogPage)
    }
}