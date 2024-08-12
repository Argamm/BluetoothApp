package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.GRADIENT_ICON_BUTTON_INCREASE_DESCRIPTION

@Composable
fun GradientIconButton(
    modifier: Modifier = Modifier,
    isDisabled: Boolean,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(ZdravnicaAppTheme.dimens.size40)
            .height(ZdravnicaAppTheme.dimens.size40)
            .clip(RoundedCornerShape(ZdravnicaAppTheme.dimens.size16))
            .background(
                brush = Brush.linearGradient(
                    colors = ZdravnicaAppTheme.colors.bigChipsStateColor.borderStrokeGradientColors,
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
            .clickable(
                enabled = !isDisabled,
                onClick = onClick,
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(ZdravnicaAppTheme.dimens.size4)
            .semantics { role = androidx.compose.ui.semantics.Role.Button },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(ZdravnicaAppTheme.dimens.size32)
                .height(ZdravnicaAppTheme.dimens.size32)
                .shadow(
                    elevation = ZdravnicaAppTheme.dimens.size3,
                    shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size1000),
                    ambientColor = ZdravnicaAppTheme.colors.baseAppColor.gray100,
                    spotColor = ZdravnicaAppTheme.colors.baseAppColor.gray100
                )
                .clip(RoundedCornerShape(ZdravnicaAppTheme.dimens.size14))
                .border(
                    BorderStroke(
                        width = ZdravnicaAppTheme.dimens.size4,
                        brush = Brush.linearGradient(
                            colors = ZdravnicaAppTheme.colors.bigChipsStateColor.borderStrokeGradientColors,
                            start = Offset(0f, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    ),
                    shape = CircleShape
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = ZdravnicaAppTheme.colors.bigChipsStateColor.borderStrokeGradientColors,
                    )
                )
                .padding(ZdravnicaAppTheme.dimens.size4),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(ZdravnicaAppTheme.dimens.size26)
                    .height(ZdravnicaAppTheme.dimens.size26)
                    .clip(RoundedCornerShape(ZdravnicaAppTheme.dimens.size14))
                    .background(
                        brush = Brush.linearGradient(
                            ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
                        )
                    )
                    .padding(ZdravnicaAppTheme.dimens.size4),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = if (isDisabled)
                        ZdravnicaAppTheme.colors.baseAppColor.gray800
                    else
                        ZdravnicaAppTheme.colors.baseAppColor.gray200
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTemperatureOrDurationAdjuster() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        GradientIconButton(
            onClick = { },
            icon = Icons.Default.Add,
            contentDescription = GRADIENT_ICON_BUTTON_INCREASE_DESCRIPTION,
            isDisabled = false,
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size44)
        )
    }
}