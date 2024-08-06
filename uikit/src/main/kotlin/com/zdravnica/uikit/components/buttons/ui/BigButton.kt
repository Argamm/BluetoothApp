package com.zdravnica.uikit.components.buttons.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicarStyle
import com.zdravnica.resources.ui.theme.models.featureColors.BigButtonStateColor
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.preview_params.PrimaryButtonPreviewParams

@Composable
fun BigButton(
    bigButtonModel: BigButtonModel,
    modifier: Modifier = Modifier,
    bigBtnStateColors: BigButtonStateColor = ZdravnicaAppTheme.colors.bigButtonStateColor
) {
    val isEnabled by rememberSaveable(bigButtonModel.isEnabled) {
        mutableStateOf(
            bigButtonModel.isEnabled
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    with(bigBtnStateColors) {
        OutlinedButton(
            onClick = { bigButtonModel.onClick?.invoke() },
            modifier = modifier,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isPressed) pressedBackground else enabledBackground,
                contentColor = enabledContentColor,
                disabledContentColor = disabledContentColor,
                disabledContainerColor = disabledBackground
            ),
            enabled = isEnabled,
            shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
            contentPadding = PaddingValues(
                horizontal = ZdravnicaAppTheme.dimens.size12,
                vertical = ZdravnicaAppTheme.dimens.size18
            ),
            border = BorderStroke(
                width = ZdravnicaAppTheme.dimens.size1,
                color = if (isEnabled) borderStrokeColor else disabledBackground
            )
        ) {
            Text(
                modifier = bigButtonModel.textModifier
                    .wrapContentSize(),
                text = bigButtonModel.buttonText,
                style = bigButtonModel.textStyle
                    ?: ZdravnicaAppTheme.typography.bodyMediumSemi,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

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
