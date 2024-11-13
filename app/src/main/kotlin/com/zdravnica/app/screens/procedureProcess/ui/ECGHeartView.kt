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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zdravnica.uikit.resources.R

@Composable
fun ECGAnimation(//NOT finished animation of heart with cardiogram
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Set the total width of one ECG cycle (one complete wave)
        val ecgCycleWidth = width / 1.5f

        // Calculate the current offset based on the animation phase
        val currentOffsetX = -ecgCycleWidth * phase

        // Create the ECG path that repeats seamlessly
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
            style = Stroke(width = 4.dp.toPx())
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
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart_anim),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
        )
        ECGAnimation(
            modifier = Modifier
                .matchParentSize()
                .padding(24.dp)
        )
    }
}

@Composable
fun MainScreen() {
    HeartWithECG()
}




