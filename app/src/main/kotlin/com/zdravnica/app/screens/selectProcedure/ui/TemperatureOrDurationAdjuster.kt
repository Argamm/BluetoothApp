package com.zdravnica.app.screens.selectProcedure.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.GRADIENT_ICON_BUTTON_DECREASE_DESCRIPTION
import com.zdravnica.uikit.GRADIENT_ICON_BUTTON_INCREASE_DESCRIPTION
import com.zdravnica.uikit.components.buttons.ui.GradientIconButton
import com.zdravnica.uikit.extensions.compose.formatAsValue
import com.zdravnica.uikit.extensions.compose.getValueRange
import com.zdravnica.uikit.resources.R

@Composable
fun TemperatureOrDurationAdjuster(
    modifier: Modifier = Modifier,
    isMinutes: Boolean = false
) {
    var value by remember { mutableIntStateOf(0) }
    val (minValue, maxValue) = getValueRange(isMinutes)
    val isMinusDisabled = value <= minValue
    val isPlusDisabled = value >= maxValue

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ZdravnicaAppTheme.dimens.size16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                id = if (isMinutes) R.string.select_product_duration else R.string.select_product_temperature
            ),
            style = ZdravnicaAppTheme.typography.bodyLargeSemi.copy(
                color = ZdravnicaAppTheme.colors.baseAppColor.gray200
            ),
            modifier = Modifier.padding(bottom = ZdravnicaAppTheme.dimens.size16)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            GradientIconButton(
                modifier = Modifier.padding(start = ZdravnicaAppTheme.dimens.size44),
                isDisabled = isMinusDisabled,
                icon = ImageVector.vectorResource(id = R.drawable.ic_minus),
                contentDescription = GRADIENT_ICON_BUTTON_DECREASE_DESCRIPTION,
                onClick = {
                    if (!isMinusDisabled) value -= if (isMinutes)
                        DURATION_INCREASE_DECREASE_VALUE
                    else
                        TEMPERATURE_INCREASE_DECREASE_VALUE
                }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            brush = Brush.linearGradient(
                                colors = ZdravnicaAppTheme.colors.timeAndTemperatureColor
                            )
                        )
                    ) {
                        append(value.formatAsValue(isMinutes))
                    }
                },
                style = ZdravnicaAppTheme.typography.headH2,
                modifier = Modifier.padding(horizontal = ZdravnicaAppTheme.dimens.size16),
            )

            GradientIconButton(
                modifier = Modifier.padding(end = ZdravnicaAppTheme.dimens.size44),
                isDisabled = isPlusDisabled,
                icon = ImageVector.vectorResource(id = R.drawable.ic_plus),
                contentDescription = GRADIENT_ICON_BUTTON_INCREASE_DESCRIPTION,
                onClick = {
                    if (!isPlusDisabled) value += if (isMinutes)
                        DURATION_INCREASE_DECREASE_VALUE
                    else
                        TEMPERATURE_INCREASE_DECREASE_VALUE
                }
            )
        }
    }
}

const val DURATION_INCREASE_DECREASE_VALUE = 60
const val TEMPERATURE_INCREASE_DECREASE_VALUE = 1

@Preview(showBackground = true)
@Composable
fun PreviewTemperatureAdjuster() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TemperatureOrDurationAdjuster(isMinutes = false)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDurationAdjuster() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TemperatureOrDurationAdjuster(isMinutes = true)
    }
}