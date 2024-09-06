package com.zdravnica.app.screens.selectProcedure.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.zdravnica.app.core.viewmodel.BaseViewModel
import com.zdravnica.app.data.LocalDataStore
import com.zdravnica.app.screens.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import org.orbitmvi.orbit.viewmodel.container

class SelectProcedureViewModel(
    private val localDataStore: LocalDataStore
) : BaseViewModel<SelectProcedureViewState, SelectProcedureSideEffect>() {

    private val _temperature = mutableIntStateOf(localDataStore.getTemperature())
    val temperature: State<Int> get() = _temperature

    private val _duration = mutableIntStateOf(localDataStore.getDuration())
    val duration: State<Int> get() = _duration

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

    fun saveTemperature(temperature: Int) {
        _temperature.intValue = temperature
        localDataStore.saveTemperature(temperature)
    }

    fun saveDuration(duration: Int) {
        _duration.intValue = duration
        localDataStore.saveDuration(duration)
    }
}