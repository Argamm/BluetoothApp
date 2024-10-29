package com.zdravnica.app.screens.preparingTheCabin.ui

import androidx.compose.animation.core.*
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
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_700
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.FLOAT_0_3
import com.zdravnica.uikit.FLOAT_1
import com.zdravnica.uikit.FLOAT_140
import com.zdravnica.uikit.FLOAT_160
import com.zdravnica.uikit.FLOAT_1_6
import com.zdravnica.uikit.COUNT_FIVE
import kotlinx.coroutines.delay

@Composable
fun AnimationCircle(
    modifier: Modifier = Modifier,
    animationEnd: () -> Unit,
) {
    val dimens = ZdravnicaAppTheme.dimens
    var timer by remember { mutableIntStateOf(COUNT_FIVE) }
    LaunchedEffect(Unit) {
        while (timer > 0) {
            delay(DELAY_1000_ML)
            timer -= COUNT_ONE
        }
        animationEnd.invoke()
    }

    val colors = ZdravnicaAppTheme.colors.baseAppColor
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val pulseSize by infiniteTransition.animateFloat(
        initialValue = FLOAT_140,
        targetValue = FLOAT_160,
        animationSpec = infiniteRepeatable(
            animation = tween(
                ANIMATION_DURATION_700,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(dimens.size320)
            .drawBehind {
                drawCircle(
                    color = colors.success800,
                    radius = pulseSize.dp.toPx()
                )
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dimens.size280)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = FLOAT_0_3),
                                Color.Transparent
                            ),
                            radius = this.size.minDimension / FLOAT_1_6,
                            center = this.center.copy(
                                y = this.center.y + dimens.size30.toPx()
                            )
                        ),
                        radius = this.size.minDimension / FLOAT_1
                    )
                }
                .border(
                    border = BorderStroke(dimens.size3, Color.White),
                    shape = CircleShape
                )
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            ZdravnicaAppTheme.colors.baseAppColor.success800,
                            ZdravnicaAppTheme.colors.baseAppColor.success800
                        )
                    ),
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
                        append("$timer")
                    }
                },
                style = ZdravnicaAppTheme.typography.headH1
            )
        }
    }
}