package com.zdravnica.app.screens.preparingTheCabin.models

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.PINK_BACK_PROGRESS_FROM
import com.zdravnica.uikit.PINK_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.RED_BACK_PROGRESS_FROM
import com.zdravnica.uikit.RED_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.WHITE_BACK_PROGRESS

@Immutable
data class ProcedureProgressCircleState(
    val progress: Int,
    val borderColor: Color,
    val backgroundBrush: Brush
)

@Composable
@Stable
fun rememberProcedureProgressCircleState(
    progress: Int,
    borderColor: Color
): ProcedureProgressCircleState {
    val colors = ZdravnicaAppTheme.colors.baseAppColor

    val backgroundBrush = when {
        progress <= WHITE_BACK_PROGRESS -> Brush.linearGradient(
            ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
        )

        progress in PINK_BACK_PROGRESS_FROM..PINK_BACK_PROGRESS_UNTIL ->
            Brush.linearGradient(listOf(colors.secondary700, colors.secondary700))

        progress in RED_BACK_PROGRESS_FROM..RED_BACK_PROGRESS_UNTIL ->
            Brush.linearGradient(listOf(colors.error700, colors.error700))

        progress == COUNT_TO_100 ->
            Brush.linearGradient(listOf(colors.success800, colors.success800))

        else -> Brush.linearGradient(
            ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
        )
    }

    return ProcedureProgressCircleState(
        progress = progress,
        borderColor = borderColor,
        backgroundBrush = backgroundBrush
    )
}