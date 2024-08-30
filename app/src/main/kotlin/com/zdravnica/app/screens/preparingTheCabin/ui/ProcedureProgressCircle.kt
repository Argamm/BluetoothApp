package com.zdravnica.app.screens.preparingTheCabin.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.FLOAT_0_3
import com.zdravnica.uikit.FLOAT_1
import com.zdravnica.uikit.FLOAT_1_6
import com.zdravnica.uikit.PINK_BACK_PROGRESS_FROM
import com.zdravnica.uikit.PINK_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.RED_BACK_PROGRESS_FROM
import com.zdravnica.uikit.RED_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.WHITE_BACK_PROGRESS

@Composable
fun ProcedureProgressCircle(
    modifier: Modifier = Modifier,
    progress: Int,
    borderColor: Color,
) {
    val colors = ZdravnicaAppTheme.colors.baseAppColor
    val dimens = ZdravnicaAppTheme.dimens
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(dimens.size280)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = FLOAT_0_3),
                            Color.Transparent
                        ),
                        radius = this.size.minDimension / FLOAT_1_6,
                        center = this.center.copy(y = this.center.y + dimens.size30.toPx())
                    ),
                    radius = this.size.minDimension / FLOAT_1
                )
            }
            .border(
                border = BorderStroke(dimens.size3, borderColor),
                shape = CircleShape
            )
            .background(
                brush = animatedBackgroundBrush,
                shape = CircleShape
            )
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        brush = Brush.linearGradient(
                            colors = ZdravnicaAppTheme.colors.timeAndTemperatureColor
                        )
                    )
                ) {
                    append("$progress%")
                }
            },
            style = ZdravnicaAppTheme.typography.headH1
        )
    }
}

@Preview
@Composable
fun PreviewProcedureProgressCircle() {
    ProcedureProgressCircle(progress = 75, borderColor = Color.White)
}
