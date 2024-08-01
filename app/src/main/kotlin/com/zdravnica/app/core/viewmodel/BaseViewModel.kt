package com.zdravnica.app.core.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.zdravnica.app.core.models.BaseViewState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@Stable
abstract class BaseViewModel<VIEWS_STATE : BaseViewState, SIDE_EFFECT : Any> :
    ContainerHost<VIEWS_STATE, SIDE_EFFECT>, ViewModel() {

    fun postViewState(newViewState: VIEWS_STATE) {
        intent {
            reduce {
                newViewState
            }
        }
    }

    fun postSideEffect(sideEffect: SIDE_EFFECT) {
        intent {
            this.postSideEffect(
                sideEffect
            )
        }
    }
}
