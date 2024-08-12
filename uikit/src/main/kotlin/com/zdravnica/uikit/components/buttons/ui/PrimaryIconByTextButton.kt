package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryIconButtonStateColor
import com.zdravnica.uikit.MAX_LINES_COUNT_ONE
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.preview.AppPreview

@Composable
fun PrimaryIconByTextButton(
    modifier: Modifier = Modifier,
    model: BigButtonModel,
    stateColor: PrimaryIconButtonStateColor = ZdravnicaAppTheme.colors.primaryIconButtonStateColor,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = modifier
            .requiredHeightIn(min = ZdravnicaAppTheme.dimens.size56)
            .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR14)
            .border(
                width = ZdravnicaAppTheme.dimens.size3,
                brush = Brush.linearGradient(
                    colors = when {
                        !model.isEnabled -> stateColor.disabledBorderGradientColor
                        isPressed -> stateColor.pressedBorderGradientColor
                        else -> stateColor.defaultBorderGradientColor
                    }
                ),
                shape = ZdravnicaAppTheme.roundedCornerShape.shapeR14
            )
            .background(
                brush = Brush.linearGradient(
                    when {
                        !model.isEnabled -> stateColor.defaultBackgroundGradientColors
                        isPressed -> stateColor.pressedBackgroundGradientColors
                        else -> stateColor.defaultBackgroundGradientColors
                    }
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                enabled = model.isEnabled,
                onClick = { model.onClick?.invoke() }
            )
            .padding(
                horizontal = ZdravnicaAppTheme.dimens.size4,
                vertical = ZdravnicaAppTheme.dimens.size18
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size12)
    ) {
        Icon(
            painter = painterResource(id = model.iconRes),
            contentDescription = null,
            modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size24),
            tint = when {
                !model.isEnabled -> stateColor.disabledContentColor
                isPressed -> stateColor.pressedContentColor
                else -> stateColor.defaultContentColor
            }
        )
        if (model.buttonText.isNotBlank()) {
            Text(
                text = model.buttonText,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                style = ZdravnicaAppTheme.typography.bodyNormalSemi.copy(
                    color = when {
                        !model.isEnabled -> stateColor.disabledContentColor
                        isPressed -> stateColor.pressedContentColor
                        else -> stateColor.defaultContentColor
                    }
                ),
                maxLines = MAX_LINES_COUNT_ONE,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@AppPreview
@Composable
private fun PrimaryIconButtonPreviewRenterMode() {
    ZdravnicaAppExerciseTheme(darkThem = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(ZdravnicaAppTheme.dimens.size48)
        ) {
            PrimaryIconByTextButton(
                model = BigButtonModel(
                    isEnabled = true,
                    buttonText = "Заказать бальзам"
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}
