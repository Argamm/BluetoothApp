package com.zdravnica.app.screens.preparingTheCabin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.screens.preparingTheCabin.models.ProcedureProgressCircleState
import com.zdravnica.app.screens.preparingTheCabin.models.rememberProcedureProgressCircleState
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.FLOAT_0_3
import com.zdravnica.uikit.FLOAT_1
import com.zdravnica.uikit.FLOAT_1_6

@Composable
fun ProcedureProgressCircle(
    modifier: Modifier = Modifier,
    state: ProcedureProgressCircleState
) {
    val dimens = ZdravnicaAppTheme.dimens

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
                border = BorderStroke(dimens.size3, state.borderColor),
                shape = CircleShape
            )
            .background(
                brush = state.backgroundBrush,
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
                    append("${state.progress}%")
                }
            },
            style = ZdravnicaAppTheme.typography.headH1
        )
    }
}


@Preview
@Composable
fun PreviewProcedureProgressCircle() {
    val state = rememberProcedureProgressCircleState(progress = 75, borderColor = Color.White)
    ProcedureProgressCircle(state = state)
}
