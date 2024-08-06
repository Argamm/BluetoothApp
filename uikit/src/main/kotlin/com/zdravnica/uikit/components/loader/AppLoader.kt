package com.zdravnica.uikit.components.loader

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.zdravnica.uikit.resources.R

@Composable
fun AppLoader(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = ANIMATION_LABEL)
    val angle by infiniteTransition.animateFloat(
        initialValue = INITIAL_VALUE,
        targetValue = TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = ANIMATION_DURATION
            }
        ),
        label = ANIMATION_LABEL
    )

    Image(
        imageVector =
        ImageVector.vectorResource(id = R.drawable.loader),
        contentDescription = "Loader",
        modifier = modifier
            .graphicsLayer {
                rotationZ = angle
            }
    )
}
