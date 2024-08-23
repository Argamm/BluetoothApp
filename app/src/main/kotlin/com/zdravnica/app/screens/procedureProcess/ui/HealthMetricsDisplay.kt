package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.uikit.resources.R
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_ONE

@Composable
fun HealthMetricsDisplay(
    modifier: Modifier = Modifier,
    temperatureValue: String,
    calorieValue: String,
    pulseValue: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ZdravnicaAppTheme.dimens.size18)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MetricCard(
                title = temperatureValue,
                value = stringResource(R.string.procedure_process_temperature_skin),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size4))
            MetricCard(
                title = calorieValue,
                value = stringResource(R.string.procedure_process_calory),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size4))
        PulseCardWithAnimation(
            modifier = Modifier.fillMaxWidth(),
            pulseValue = pulseValue,
        )
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(ZdravnicaAppTheme.dimens.size4)
            .shadow(
                elevation = ZdravnicaAppTheme.dimens.size4,
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.25f),
            )
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    top = ZdravnicaAppTheme.dimens.size30,
                    bottom = ZdravnicaAppTheme.dimens.size30,
                    start = ZdravnicaAppTheme.dimens.size8,
                    end = ZdravnicaAppTheme.dimens.size8
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
                        append(title)
                    }
                },
                style = ZdravnicaAppTheme.typography.headH3,
                textAlign = TextAlign.Center,
                maxLines = COUNT_ONE,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size4))
            Text(
                text = value,
                style = ZdravnicaAppTheme.typography.bodyMediumMedium,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray300,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ZdravnicaAppTheme.dimens.size4)
            )
        }
    }
}

@Composable
fun PulseCardWithAnimation(
    modifier: Modifier = Modifier,
    pulseValue: String
) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(ZdravnicaAppTheme.dimens.size4)
            .shadow(
                elevation = ZdravnicaAppTheme.dimens.size4,
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.25f),
            )
    ) {
        Row(
            modifier = Modifier
                .padding(ZdravnicaAppTheme.dimens.size8)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                MainScreen()
            }

            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size16))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
                            append(pulseValue)
                        }
                    },
                    style = ZdravnicaAppTheme.typography.headH3,
                    textAlign = TextAlign.Center,
                    maxLines = COUNT_ONE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = ZdravnicaAppTheme.dimens.size43)
                )
                Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size4))
                Text(
                    text = stringResource(R.string.procedure_process_pulse),
                    style = ZdravnicaAppTheme.typography.bodyMediumMedium,
                    color = ZdravnicaAppTheme.colors.baseAppColor.gray300,
                    textAlign = TextAlign.Center,
                    maxLines = COUNT_ONE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = ZdravnicaAppTheme.dimens.size43)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHealthMetricsDisplay() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        HealthMetricsDisplay(
            temperatureValue = "36.5°C",
            calorieValue = "120 ккал",
            pulseValue = "180/60",
        )
    }
}