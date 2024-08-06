package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.IconButtonStateColor
import com.zdravnica.uikit.components.buttons.models.IconButtonModel
import com.zdravnica.uikit.components.buttons.models.IconButtonType

@Composable
fun IconButtonsComponent(
    iconButtonModel: IconButtonModel,
    modifier: Modifier = Modifier,
    stateColor: IconButtonStateColor = ZdravnicaAppTheme.colors.iconButtonStateColor
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color by remember {
        mutableStateOf(
            IconButtonColors(
                containerColor = when (iconButtonModel.type) {
                    IconButtonType.PRIMARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.primaryButtonStateColor.disabledBackgroundColor
                        isPressed -> stateColor.primaryButtonStateColor.pressedBackgroundColor
                        else -> stateColor.primaryButtonStateColor.defaultBackgroundColor
                    }

                    IconButtonType.SECONDARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.secondaryButtonState.disabledBackgroundColor
                        isPressed -> stateColor.secondaryButtonState.pressedBackgroundColor
                        else -> stateColor.secondaryButtonState.defaultBackgroundColor
                    }

                    IconButtonType.TERTIARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.tertiaryButtonState.disabledBackgroundColor
                        isPressed -> stateColor.tertiaryButtonState.pressedBackgroundColor
                        else -> stateColor.tertiaryButtonState.defaultBackgroundColor
                    }
                },
                contentColor = when (iconButtonModel.type) {
                    IconButtonType.PRIMARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.primaryButtonStateColor.disabledContentColor
                        isPressed -> stateColor.primaryButtonStateColor.pressedContentColor
                        else -> stateColor.primaryButtonStateColor.defaultContentColor
                    }

                    IconButtonType.SECONDARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.secondaryButtonState.disabledContentColor
                        isPressed -> stateColor.secondaryButtonState.pressedContentColor
                        else -> stateColor.secondaryButtonState.defaultContentColor
                    }

                    IconButtonType.TERTIARY -> when {
                        iconButtonModel.isEnabled.not() -> stateColor.tertiaryButtonState.disabledContentColor
                        isPressed -> stateColor.tertiaryButtonState.pressedContentColor
                        else -> stateColor.tertiaryButtonState.defaultContentColor
                    }
                }, disabledContentColor = when (iconButtonModel.type) {
                    IconButtonType.PRIMARY -> stateColor.primaryButtonStateColor.disabledContentColor

                    IconButtonType.SECONDARY -> stateColor.secondaryButtonState.disabledContentColor

                    IconButtonType.TERTIARY -> stateColor.tertiaryButtonState.disabledContentColor
                }, disabledContainerColor = when (iconButtonModel.type) {
                    IconButtonType.PRIMARY -> stateColor.primaryButtonStateColor.disabledBackgroundColor

                    IconButtonType.SECONDARY -> stateColor.secondaryButtonState.disabledBackgroundColor

                    IconButtonType.TERTIARY -> stateColor.tertiaryButtonState.disabledBackgroundColor
                }
            )
        )
    }

    Box(
        modifier = modifier
            .requiredSize(48.dp)
            .shadow(
                shape = CircleShape,
                elevation = 1.dp,
                clip = true,
                ambientColor = Color(0x0F323247),
                spotColor = Color(0x14323247)

            )
            .background(color = color.containerColor)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = iconButtonModel.isEnabled,
                onClick = { iconButtonModel.onClick?.invoke() }
            )
            .padding(
                horizontal = ZdravnicaAppTheme.dimens.size4,
                vertical = ZdravnicaAppTheme.dimens.size18
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconButtonModel.iconRes),
            contentDescription = iconButtonModel.contentDescription,
            modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size24),
            tint = color.contentColor
        )
    }
}

@Preview
@Composable
private fun IconButtonComponentPreview() {
    ZdravnicaAppExerciseTheme(darkThem = true) {
        Column(modifier = Modifier.background(ZdravnicaAppTheme.colors.primaryBackgroundColor)) {
            Row {
                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = true, type = IconButtonType.PRIMARY
                    )
                )
                Spacer(modifier = Modifier.requiredWidth(ZdravnicaAppTheme.dimens.size12))

                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = false, type = IconButtonType.PRIMARY
                    )
                )
            }


            Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size12))

            Row {
                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = true, type = IconButtonType.SECONDARY
                    )
                )
                Spacer(modifier = Modifier.requiredWidth(ZdravnicaAppTheme.dimens.size12))

                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = false, type = IconButtonType.SECONDARY
                    )
                )
            }

            Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size12))

            Row {
                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = true, type = IconButtonType.TERTIARY
                    )
                )
                Spacer(modifier = Modifier.requiredWidth(ZdravnicaAppTheme.dimens.size12))

                IconButtonsComponent(
                    iconButtonModel = IconButtonModel(
                        isEnabled = false, type = IconButtonType.TERTIARY
                    )
                )
            }
        }
    }
}

