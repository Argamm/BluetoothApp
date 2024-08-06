package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.TextButtonStateColor
import com.zdravnica.uikit.components.buttons.models.TextButtonModel
import com.zdravnica.uikit.components.buttons.models.TextButtonType
import com.zdravnica.uikit.components.buttons.preview_params.TextButtonPreviewParams

@Composable
fun TextButton(
    textButtonModel: TextButtonModel,
    modifier: Modifier = Modifier,
    textButtonStateColors: TextButtonStateColor = ZdravnicaAppTheme.colors.textButtonStateColor
) {


    val isEnabled by rememberSaveable(textButtonModel.isEnabled) {
        mutableStateOf(
            textButtonModel.isEnabled
        )
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val stateColor = ButtonDefaults.outlinedButtonColors(
        containerColor = when (textButtonModel.textButtonType) {
            TextButtonType.PRIMARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.primaryButtonStateColor.disabledBackgroundColor
                isPressed -> textButtonStateColors.primaryButtonStateColor.pressedBackgroundColor
                else -> textButtonStateColors.primaryButtonStateColor.defaultBackgroundColor
            }

            TextButtonType.SECONDARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.secondaryButtonState.disabledBackgroundColor
                isPressed -> textButtonStateColors.secondaryButtonState.pressedBackgroundColor
                else -> textButtonStateColors.secondaryButtonState.defaultBackgroundColor
            }

            TextButtonType.TERTIARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.tertiaryButtonState.disabledBackgroundColor
                isPressed -> textButtonStateColors.tertiaryButtonState.pressedBackgroundColor
                else -> textButtonStateColors.tertiaryButtonState.defaultBackgroundColor
            }
        },
        contentColor = when (textButtonModel.textButtonType) {
            TextButtonType.PRIMARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.primaryButtonStateColor.disabledContentColor
                isPressed -> textButtonStateColors.primaryButtonStateColor.pressedContentColor
                else -> textButtonStateColors.primaryButtonStateColor.defaultContentColor
            }

            TextButtonType.SECONDARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.secondaryButtonState.disabledContentColor
                isPressed -> textButtonStateColors.secondaryButtonState.pressedContentColor
                else -> textButtonStateColors.secondaryButtonState.defaultContentColor
            }

            TextButtonType.TERTIARY -> when {
                textButtonModel.isEnabled.not() -> textButtonStateColors.tertiaryButtonState.disabledContentColor
                isPressed -> textButtonStateColors.tertiaryButtonState.pressedContentColor
                else -> textButtonStateColors.tertiaryButtonState.defaultContentColor
            }
        },
        disabledContentColor = when (textButtonModel.textButtonType) {
            TextButtonType.PRIMARY -> textButtonStateColors.primaryButtonStateColor.disabledContentColor

            TextButtonType.SECONDARY -> textButtonStateColors.secondaryButtonState.disabledContentColor

            TextButtonType.TERTIARY -> textButtonStateColors.tertiaryButtonState.disabledContentColor
        },
        disabledContainerColor = when (textButtonModel.textButtonType) {
            TextButtonType.PRIMARY -> textButtonStateColors.primaryButtonStateColor.disabledBackgroundColor

            TextButtonType.SECONDARY -> textButtonStateColors.secondaryButtonState.disabledBackgroundColor

            TextButtonType.TERTIARY -> textButtonStateColors.tertiaryButtonState.disabledBackgroundColor
        }
    )

    OutlinedButton(
        onClick = { textButtonModel.onClick?.invoke() },
        modifier = modifier,
        colors = stateColor,
        enabled = isEnabled,
        shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
        contentPadding = PaddingValues(
            horizontal = ZdravnicaAppTheme.dimens.size12,
            vertical = ZdravnicaAppTheme.dimens.size18
        )
    ) {
        Text(
            modifier = textButtonModel.textModifier
                .wrapContentSize(),
            text = textButtonModel.buttonText,
            style = textButtonModel.textStyle
                ?: ZdravnicaAppTheme.typography.bodyMediumSemi,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TextButtonPreview(
    @PreviewParameter(TextButtonPreviewParams::class)
    textButtonModel: TextButtonModel
) {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TextButton(
            textButtonModel = textButtonModel,
            modifier = Modifier
                .wrapContentWidth()
                .requiredSizeIn(
                    minHeight = ZdravnicaAppTheme.dimens.size44,
                    minWidth = ZdravnicaAppTheme.dimens.size82
                )
        )
    }
}
