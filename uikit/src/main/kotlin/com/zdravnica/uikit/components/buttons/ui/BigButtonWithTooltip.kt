package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicarStyle
import com.zdravnica.resources.ui.theme.models.featureColors.BigButtonStateColor
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.COUNT_TWO
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.preview_params.PrimaryButtonPreviewParams
import com.zdravnica.uikit.components.tooltip.TooltipPopup
import com.zdravnica.uikit.resources.R

@Composable
fun BigButtonWithTooltip(
    modifier: Modifier = Modifier,
    temperatureAlert: Boolean,
    thermostatAlert: Boolean,
    temperatureSensorAlert: Boolean,
    bigButtonModel: BigButtonModel,
    showTooltip: Boolean = false,
    bigBtnStateColors: BigButtonStateColor = ZdravnicaAppTheme.colors.bigButtonStateColor
) {
    val isPressed by remember { MutableInteractionSource() }.collectIsPressedAsState()
    val backgroundColor = if (isPressed) bigBtnStateColors.pressedBackground
    else if (bigButtonModel.isEnabled) bigBtnStateColors.enabledBackground
    else bigBtnStateColors.disabledBackground

    val contentColor = if (bigButtonModel.isEnabled) bigBtnStateColors.enabledContentColor
    else bigBtnStateColors.disabledContentColor

    val borderColor = if (bigButtonModel.isEnabled) bigBtnStateColors.borderStrokeColor
    else bigBtnStateColors.disabledBackground

    OutlinedButton(
        onClick = { bigButtonModel.onClick?.invoke() },
        modifier = modifier
            .then(
                if (bigButtonModel.isEnabled) {
                    Modifier
                        .shadow(
                            elevation = ZdravnicaAppTheme.dimens.size8,
                            shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
                            clip = false,
                            ambientColor = Color.LightGray,
                            spotColor = Color.Gray
                        )
                        .padding(top = ZdravnicaAppTheme.dimens.size15)
                        .offset(y = -ZdravnicaAppTheme.dimens.size10)
                } else modifier
            ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        enabled = true,
        shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
        contentPadding = PaddingValues(
            horizontal = ZdravnicaAppTheme.dimens.size12,
            vertical = ZdravnicaAppTheme.dimens.size18
        ),
        border = BorderStroke(
            width = ZdravnicaAppTheme.dimens.size1,
            color = borderColor
        )
    ) {
        TooltipPopup(
            modifier = Modifier.padding(start = ZdravnicaAppTheme.dimens.size8),
            viewHeight = -30,
            requesterView = { boxModifier ->
                Text(
                    modifier = boxModifier,
                    text = bigButtonModel.buttonText,
                    style = bigButtonModel.textStyle ?: ZdravnicaAppTheme.typography.bodyMediumSemi,
                    maxLines = COUNT_ONE,
                    overflow = TextOverflow.Ellipsis
                )
            },
            tooltipContent = {
                if (showTooltip) {
                    Text(
                        maxLines = COUNT_TWO,
                        minLines = COUNT_TWO,
                        modifier = Modifier
                            .padding(
                                horizontal = ZdravnicaAppTheme.dimens.size8,
                                vertical = ZdravnicaAppTheme.dimens.size4
                            )
                            .widthIn(max = ZdravnicaAppTheme.dimens.size152)
                            .heightIn(max = ZdravnicaAppTheme.dimens.size100),
                        text = if (temperatureAlert)  stringResource(R.string.procedure_screen_temperature_alert)
                        else if (thermostatAlert) stringResource(R.string.procedure_screen_thermostat_alert)
                        else if (temperatureSensorAlert) stringResource(R.string.procedure_screen_temperature_sensor_alert)
                        else stringResource(R.string.procedure_screen_tooltip_message),
                        style = ZdravnicaAppTheme.typography.bodyXSMedium,
                        color = Color.Black,
                    )
                }
            }
        )

    }
}

@Preview
@Composable
private fun BigButtonButtonPreview(
    @PreviewParameter(PrimaryButtonPreviewParams::class)
    bigButtonModel: BigButtonModel
) {
    ZdravnicaAppExerciseTheme(style = ZdravnicarStyle.Main, darkThem = false) {
        BigButton(
            bigButtonModel = bigButtonModel,
            modifier = Modifier
                .requiredSizeIn(
                    minHeight = ZdravnicaAppTheme.dimens.size56,
                    maxWidth = ZdravnicaAppTheme.dimens.size198
                )
        )
    }
}