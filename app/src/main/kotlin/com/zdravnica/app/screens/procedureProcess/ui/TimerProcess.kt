package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.uikit.resources.R
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.extensions.compose.calculateTimeText
import kotlinx.coroutines.delay

@Composable
fun TimerProcess(totalSeconds: Int, onTimerFinish: () -> Unit) {
    val remainingSeconds = remember { mutableIntStateOf(totalSeconds) }

    LaunchedEffect(remainingSeconds.intValue) {
        if (remainingSeconds.intValue > 0) {
            delay(DELAY_1000_ML)
            remainingSeconds.intValue -= COUNT_ONE
        } else {
            onTimerFinish()
        }
    }

    val timeText = calculateTimeText(remainingSeconds.intValue)

    Column(
        modifier = Modifier
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
            style = ZdravnicaAppTheme.typography.headH1
        )
        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size16))
        Text(
            text = stringResource(R.string.procedure_process_to_the_end_of_procedure),
            style = ZdravnicaAppTheme.typography.bodyMediumMedium.copy(
                color = ZdravnicaAppTheme.colors.baseAppColor.gray300
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimerProcess() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TimerProcess(totalSeconds = 600) {}
    }
}