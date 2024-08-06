package com.zdravnica.uikit.components.snackbars.models

import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Immutable
import java.time.format.TextStyle

@Immutable
data class SnackBarModel(
    val snackBarData: SnackbarData,
    val snackBarType: SnackBarTypeEnum,
    val textStyle: TextStyle = TextStyle.FULL,
    val actionOnNewLine: Boolean = true
)
