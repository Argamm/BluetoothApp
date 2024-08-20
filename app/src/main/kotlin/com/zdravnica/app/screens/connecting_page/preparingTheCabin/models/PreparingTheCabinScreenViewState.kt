package com.zdravnica.app.screens.connecting_page.preparingTheCabin.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState

@Immutable
data class PreparingTheCabinScreenViewState(
    val uiModel: PreparingTheCabinUiModel = PreparingTheCabinUiModel()
) : BaseViewState()