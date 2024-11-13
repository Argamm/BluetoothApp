package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.utils.isLandscape
import com.zdravnica.app.utils.isTablet
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.components.clock.Clock
import com.zdravnica.uikit.extensions.compose.calculateTimeText
import com.zdravnica.uikit.resources.R

private const val NINE_MINUTES_IN_SECONDS = 539
private const val FOUR_MINUTES_IN_SECONDS = 239
private const val TURN_OFF_THRESHOLD_NINE_MINUTES = 480
private const val TURN_OFF_THRESHOLD_FOUR_MINUTES = 180
private const val MINUTES_LEFT_CREDITS_NINE_MINUTES = 540
private const val MINUTES_LEFT_CREDITS_FOUR_MINUTES = 240

@Composable
fun TimerProcess(
    modifier: Modifier = Modifier,
    totalSeconds: Int,
    onTimerFinish: () -> Unit,
    onNineMinutesLeft: () -> Unit,
    onTurnOffCommand: () -> Unit,
    onFourMinutesLeft: () -> Unit,
    onTurnOffCommandAfterFour: () -> Unit,
    onMinutesLeftWithCredits: () -> Unit,
) {
    val remainingSeconds = remember { mutableIntStateOf(totalSeconds) }
    var hasNineMinutesBeenCalled by remember { mutableStateOf(false) }
    var hasFourMinutesBeenCalled by remember { mutableStateOf(false) }

    val clock = remember {
        Clock(duration = totalSeconds * DELAY_1000_ML).apply {
            start(
                onFinish = { onTimerFinish() },
                onTick = { remainingTime ->
                    remainingSeconds.intValue = (remainingTime / DELAY_1000_ML).toInt()

                    if (remainingSeconds.intValue == NINE_MINUTES_IN_SECONDS && !hasNineMinutesBeenCalled) {
                        onNineMinutesLeft()
                        hasNineMinutesBeenCalled = true
                    }

                    if (remainingSeconds.intValue == MINUTES_LEFT_CREDITS_NINE_MINUTES && !hasNineMinutesBeenCalled) {
                        onMinutesLeftWithCredits()
                    }

                    if (remainingSeconds.intValue == TURN_OFF_THRESHOLD_NINE_MINUTES && hasNineMinutesBeenCalled) {
                        onTurnOffCommand()
                    }

                    if (remainingSeconds.intValue == FOUR_MINUTES_IN_SECONDS && !hasFourMinutesBeenCalled) {
                        onFourMinutesLeft()
                        hasFourMinutesBeenCalled = true
                    }

                    if (remainingSeconds.intValue == MINUTES_LEFT_CREDITS_FOUR_MINUTES && !hasFourMinutesBeenCalled) {
                        onMinutesLeftWithCredits()
                    }

                    if (remainingSeconds.intValue == TURN_OFF_THRESHOLD_FOUR_MINUTES && hasFourMinutesBeenCalled) {
                        onTurnOffCommandAfterFour()
                    }
                }
            )
        }
    }

    val timeText = calculateTimeText(remainingSeconds.intValue)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = ZdravnicaAppTheme.dimens.size32),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    append(timeText)
                }
            },
            style = if (isTablet()) {
                if (isLandscape()) {
                    ZdravnicaAppTheme.typography.gigaSans
                } else {
                    ZdravnicaAppTheme.typography.headH1
                }
            } else {
                ZdravnicaAppTheme.typography.headH1
            }
        )
        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size16))
        Text(
            text = stringResource(R.string.procedure_process_to_the_end_of_procedure),
            style = if (isTablet())
                ZdravnicaAppTheme.typography.bodyLargeMedium
            else
                ZdravnicaAppTheme.typography.bodyMediumMedium,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray300
        )
    }

    DisposableEffect(Unit) {
        onDispose { clock.cancel() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimerProcess() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TimerProcess(
            totalSeconds = 600,
            onTimerFinish = {},
            onNineMinutesLeft = {},
            onTurnOffCommand = {},
            onFourMinutesLeft = {},
            onTurnOffCommandAfterFour = {},
            onMinutesLeftWithCredits = {},
        )
    }
}