package com.zdravnica.app.screens.preparingTheCabin.models

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_ONE
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
    val targetColors = when {
        progress <= WHITE_BACK_PROGRESS -> ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
        progress in PINK_BACK_PROGRESS_FROM..PINK_BACK_PROGRESS_UNTIL ->
            listOf(colors.secondary700, colors.secondary700)
        progress in RED_BACK_PROGRESS_FROM..RED_BACK_PROGRESS_UNTIL ->
            listOf(colors.error700, colors.error700)
        progress == COUNT_TO_100 ->
            listOf(colors.success800, colors.success800)
        else -> ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
    }

    val animatedColors by animateColorAsState(
        targetValue = targetColors[0],
        animationSpec = tween(durationMillis = ANIMATION_DURATION_3000), label = ""
    )
    val secondAnimatedColor by animateColorAsState(
        targetValue = targetColors.getOrElse(COUNT_ONE) { targetColors[0] },
        animationSpec = tween(durationMillis = ANIMATION_DURATION_3000), label = ""
    )

    val animatedBackgroundBrush = Brush.linearGradient(
        listOf(animatedColors, secondAnimatedColor)
    )

    return ProcedureProgressCircleState(
        progress = progress,
        borderColor = borderColor,
        backgroundBrush = animatedBackgroundBrush
    )
}