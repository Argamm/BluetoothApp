package com.zdravnica.app.screens.connecting_page.selectProcedure.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.ui.GradientIconButton
import com.zdravnica.uikit.resources.R

@Composable
fun ValueAdjuster(
    isMinutes: Boolean = false
) {
    var value by remember { mutableIntStateOf(0) }

    fun formatValue(value: Int): String {
        return if (isMinutes) {
            val minutes = (value / 60).toString().padStart(2, '0')
            val seconds = (value % 60).toString().padStart(2, '0')
            "$minutes:$seconds"
        } else {
            "$value°"
        }
    }

    val (minValue, maxValue) = if (isMinutes) {
        60 to 1800 // 1 minute to 30 minutes
    } else {
        0 to 80 // 0° to 80°
    }

    val isMinusDisabled = value <= minValue
    val isPlusDisabled = value >= maxValue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                onClick = { if (!isMinusDisabled) value -= if (isMinutes) 60 else 1 },
                icon = Icons.Default.Remove,
                contentDescription = "Decrease",
                isDisabled = isMinusDisabled,
                modifier = Modifier.padding(start = ZdravnicaAppTheme.dimens.size44)
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
                        append(formatValue(value))
                    }
                },
                style = ZdravnicaAppTheme.typography.headH2,
                modifier = Modifier.padding(horizontal = ZdravnicaAppTheme.dimens.size16),
            )

            GradientIconButton(
                onClick = { if (!isPlusDisabled) value += if (isMinutes) 60 else 1 },
                icon = Icons.Default.Add,
                contentDescription = "Increase",
                isDisabled = isPlusDisabled,
                modifier = Modifier.padding(end = ZdravnicaAppTheme.dimens.size44)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewValueAdjuster() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ValueAdjuster(isMinutes = false) // Preview Celsius degrees
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewValueAdjusterMinutes() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ValueAdjuster(isMinutes = true) // Preview minutes
    }
}



