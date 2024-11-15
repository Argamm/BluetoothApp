package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zdravnica.app.utils.isLandscape
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_1000
import com.zdravnica.uikit.ANIMATION_DURATION_5000
import com.zdravnica.uikit.COUNT_FOUR
import com.zdravnica.uikit.resources.R

@Composable
fun ECGAnimation(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIMATION_DURATION_5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val ecgCycleWidth = width / 1.5f
        val currentOffsetX = -ecgCycleWidth * phase
        val ecgPath = Path().apply {
            var x = currentOffsetX
            while (x < width) {
                moveTo(x, height * 0.5f) // Baseline
                lineTo(x + ecgCycleWidth * 0.1f, height * 0.5f)
                lineTo(x + ecgCycleWidth * 0.1f, height * 0.5f)
                lineTo(x + ecgCycleWidth * 0.15f, height * 0.35f) // Sharp upward peak
                lineTo(x + ecgCycleWidth * 0.25f, height * 0.65f) // Downward slope
//                lineTo(x + ecgCycleWidth * 0.35f, height * 0.65f) // Downward slope
                lineTo(x + ecgCycleWidth * 0.35f, height * 0.5f)  // Back to baseline

                lineTo(x + ecgCycleWidth * 0.45f, height * 0.5f)
                lineTo(x + ecgCycleWidth * 0.55f, height * 0.5f)
                lineTo(x + ecgCycleWidth * 0.65f, height * 0.5f)
                lineTo(x + ecgCycleWidth * 0.75f, height * 0.35f) // Sharp upward peak
                lineTo(x + ecgCycleWidth * 0.85f, height * 0.65f) // Downward slope
//                lineTo(x + ecgCycleWidth * 0.75f, height * 0.65f) // Downward slope
                lineTo(x + ecgCycleWidth * 0.95f, height * 0.5f)  // Back to baseline
                lineTo(x + ecgCycleWidth * 1.1f, height * 0.5f)

                x += ecgCycleWidth
            }
        }

        drawPath(
            path = ecgPath,
            color = Color.White,
            style = Stroke(width = COUNT_FOUR.dp.toPx())
        )
    }
}

@Composable
fun HeartWithECG() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIMATION_DURATION_1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(ZdravnicaAppTheme.dimens.size200)
            .clip(RectangleShape)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart_anim),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer {
                    translationX = -COUNT_FOUR.dp.toPx()
                    translationY = -COUNT_FOUR.dp.toPx()
                    clip = true
                }
                .fillMaxSize()
                .scale(scale)
        )
        if (isLandscape()) {
            ECGAnimation(
                modifier = Modifier
                    .matchParentSize()
                    .padding(ZdravnicaAppTheme.dimens.size24)
            )
        }
    }
}

@Composable
fun MainScreen() {
    HeartWithECG()
}