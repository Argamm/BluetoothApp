package com.zdravnica.app.screens.connecting_page.selectProcedure.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R

@Composable
fun TextWithSwitches(
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ZdravnicaAppTheme.dimens.size16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.select_product_infrared_radiation),
                style = ZdravnicaAppTheme.typography.bodyNormalMedium.copy(
                    color = ZdravnicaAppTheme.colors.baseAppColor.gray200
                )
            )
            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size2))
            Text(
                text = stringResource(id = R.string.select_product_warming_up),
                style = ZdravnicaAppTheme.typography.bodySmallRegular.copy(
                    color = ZdravnicaAppTheme.colors.baseAppColor.gray400
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomSwitch(
                switchState = switchState,
                onCheckedChange = { onSwitchChange(it) }
            )
        }
    }
}

@Composable
fun CustomSwitch(
    switchState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (switchState)
            ZdravnicaAppTheme.dimens.size27
        else
            ZdravnicaAppTheme.dimens.size3,
        animationSpec = tween(durationMillis = 100),
        label = ""
    )
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(ZdravnicaAppTheme.dimens.size60, ZdravnicaAppTheme.dimens.size35)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onCheckedChange(!switchState) }
            .padding(ZdravnicaAppTheme.dimens.size4)

    ) {
        // Track
        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(
                    brush = Brush.linearGradient(
                        colors = if (switchState)
                            ZdravnicaAppTheme.colors.switchStateColor.switchTrackColor
                        else
                            ZdravnicaAppTheme.colors.switchStateColor.switchBorderColor,
                        end = androidx.compose.ui.geometry.Offset(0f, 0f),
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size15)
                )
                .border(
                    brush = Brush.linearGradient(
                        colors = ZdravnicaAppTheme.colors.switchStateColor.switchBorderColor,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(
                            0f,
                            Float.POSITIVE_INFINITY
                        )
                    ),
                    width = 3.dp,
                    shape = RoundedCornerShape(15.dp)
                )

        )

        // Thumb
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .padding(top = 2.dp)
                .size(22.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = ZdravnicaAppTheme.colors.switchStateColor.switchBorderColor,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = CircleShape
                )
                .border(
                    brush = Brush.linearGradient(
                        colors = ZdravnicaAppTheme.colors.switchStateColor.switchBorderColor,
                        end = androidx.compose.ui.geometry.Offset(0f, 0f),
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    width = 2.dp,
                    shape = CircleShape
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextWithSwitches() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TextWithSwitches(switchState = true) {}
    }
}

