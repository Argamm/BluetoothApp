package com.zdravnica.app.screens.procedure.models

import androidx.compose.runtime.Immutable
import com.zdravnica.app.core.models.BaseViewState
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel

@Immutable
data class ProcedureScreenViewState(
    val balmCounts: Map<String, Float> = emptyMap(),
    val selectedOption: Int? = null,
    val chipData: BigChipType? = null,
    val balmInfo: List<ChipBalmInfoModel>? = null,
    val firstBalmCount: Float = 0f,
    val secondBalmCount: Float = 0f,
    val thirdBalmCount: Float = 0f,
    val temperatureAlert: Boolean = true,
) : BaseViewState()