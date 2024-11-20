package com.zdravnica.uikit.components.pulseAnimation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.zdravnica.uikit.resources.R

@Composable
fun PulseAnimationWithBackground(heartBeat: Int, cardWidth: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.heart_pulse_background),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(324f / 76f)
                .align(Alignment.TopCenter)
        )

        EcgScreen(
            modifier = Modifier.align(Alignment.Center),
            heartBeat = heartBeat,
            cardWidth = cardWidth
        )
    }
}

@Composable
fun PulseAnimation(heartBeat: Int, cardWidth: Int) {
    PulseAnimationWithBackground(heartBeat, cardWidth)
}
