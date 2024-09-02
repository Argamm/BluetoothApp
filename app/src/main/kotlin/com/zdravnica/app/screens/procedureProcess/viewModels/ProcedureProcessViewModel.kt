package com.zdravnica.app.screens.procedureProcess.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.procedureProcess.models.ProcedureProcessViewState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class ProcedureProcessViewModel(
    localDataStore: LocalDataStore
) : BaseViewModel<ProcedureProcessViewState, ProcedureProcessSideEffect>() {

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

    override val container =
        container<ProcedureProcessViewState, ProcedureProcessSideEffect>(
            ProcedureProcessViewState()
        )

    fun onChangeCancelDialogPageVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(uiModel = state.uiModel.copy(isDialogVisible = isVisible))
        }
    }

    fun navigateToCancelDialogPage() {
        postSideEffect(ProcedureProcessSideEffect.OnNavigateToCancelDialogPage)
    }

    fun navigateToMainScreen() {
        postSideEffect(ProcedureProcessSideEffect.OnNavigateToMainScreen)
    }
}