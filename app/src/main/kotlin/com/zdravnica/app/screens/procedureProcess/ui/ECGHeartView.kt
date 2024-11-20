package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R

const val ANIMATON_DURATION_2500 = 2500

@Composable
fun PulseHeartWithCircle() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIMATON_DURATION_2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "HeartScale"
    )

    val circleScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIMATON_DURATION_2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CircleScale"
    )

    Box(
        modifier = Modifier
            .size(ZdravnicaAppTheme.dimens.size100)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart_anim_circle),
            contentDescription = "Circle",
            modifier = Modifier
                .fillMaxSize()
                .scale(circleScale)
        )

        Image(
            painter = painterResource(id = R.drawable.heart_anim_gradient),
            contentDescription = "Heart",
            modifier = Modifier
                .size(ZdravnicaAppTheme.dimens.size152)
                .scale(heartScale)
        )
    }
}


@Composable
fun HeartAnimationScreen() {
    PulseHeartWithCircle()
}