package com.zdravnica.uikit.components.pulseAnimation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import kotlinx.coroutines.delay

val CURVE_COLOR = Color(0xFFE19B9B)
const val HEIGHT = 80
const val DATA_LENGTH = 70

val ecgWavePattern = listOf(
    0.0,
    0.0,
    0.0,
    0.0,
    0.0,
    0.0,
    0.3,
    0.6,
    1.0,
    0.3,
    -0.2,
    -0.5,
    -0.1,
    0.0,
    0.0,
    0.1,
    0.3,
    0.5,
    0.2,
    -0.1,
    -0.3,
    -0.05,
    0.0,
    0.0
)

@Composable
fun rememberEcgState(dataLength: Int): EcgState {
    return remember {
        EcgState(dataLength)
    }
}

class EcgState(dataLength: Int) {
    var heartData = MutableList(dataLength) { 0.0 }
    var heartDataIndex by mutableIntStateOf(0)
    private var waveIndex by mutableIntStateOf(0)
    var bang by mutableStateOf(false)

    fun updateData() {
        heartDataIndex = (heartDataIndex + 1) % heartData.size

        if (bang) {
            heartData[heartDataIndex] = ecgWavePattern[waveIndex]
            waveIndex = (waveIndex + 1) % ecgWavePattern.size
            if (waveIndex == 0) bang = false
        } else {
            heartData[heartDataIndex] = 0.0
        }
    }
}

@Composable
fun EcgLine(ecgState: EcgState, width: Int, height: Int) {
    val yFactor = height * 0.45
    val baseY = height / 2f
    val step = (width - 5) / ecgState.heartData.size.toFloat()

    Canvas(
        modifier = Modifier
            .size(width.dp, height.dp)
            .padding(top = ZdravnicaAppTheme.dimens.size25)
    ) {
        val path = Path()
        val heartIndex = (ecgState.heartDataIndex + 1) % ecgState.heartData.size

        path.moveTo(0f, baseY)

        var circleX = 0f
        var circleY = baseY

        for (i in ecgState.heartData.indices) {
            val x = i * step
            val y =
                baseY - ecgState.heartData[(heartIndex + i) % ecgState.heartData.size].toFloat() * yFactor
            path.lineTo(x, y.toFloat())

            if (i == ecgState.heartData.size - 1) {
                circleX = x
                circleY = y.toFloat()
            }
        }

        drawPath(
            path = path,
            color = CURVE_COLOR,
            style = Stroke(width = 8f)
        )

        drawCircle(color = CURVE_COLOR, radius = 9f, center = Offset(circleX, circleY))
    }
}


@Composable
fun EcgChart(heartBeat: Int, cardWidth: Int) {
    val ecgState = rememberEcgState(DATA_LENGTH)
    val adjustedWidth = cardWidth / 1.1

    val dynamicInterval = when (heartBeat) {
        in 1..20 -> 90L
        in 20..50 -> 70L
        in 50..80 -> 60L
        in 80..100 -> 40L
        in 100..120 -> 30L
        in 120..180 -> 20L
        else -> 40L
    }

    LaunchedEffect(heartBeat) {
        while (true) {
            ecgState.updateData()
            delay(dynamicInterval)

            ecgState.bang = heartBeat != 0
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        EcgLine(
            ecgState = ecgState,
            width = adjustedWidth.toInt(),
            height = HEIGHT,
        )
    }
}

@Composable
fun EcgScreen(modifier: Modifier = Modifier, heartBeat: Int, cardWidth: Int) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EcgChart(heartBeat, cardWidth)
    }
}