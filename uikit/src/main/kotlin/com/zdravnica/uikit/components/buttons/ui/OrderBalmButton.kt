package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R

@Composable
fun OrderBalmButton(
    modifier: Modifier = Modifier,
    isDisabled: Boolean,
    contentDescription: String,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(ZdravnicaAppTheme.dimens.size30))
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
            .semantics { role = androidx.compose.ui.semantics.Role.Button }
            .padding(ZdravnicaAppTheme.dimens.size4),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .let { baseModifier ->
                    if (!isDisabled) {
                        baseModifier.shadow(
                            elevation = ZdravnicaAppTheme.dimens.size2,
                            shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size1000),
                            ambientColor = ZdravnicaAppTheme.colors.baseAppColor.gray100,
                            spotColor = ZdravnicaAppTheme.colors.baseAppColor.gray100
                        )
                    } else {
                        baseModifier
                    }
                }
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
                    .background(
                        brush = Brush.linearGradient(
                            ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors,
                            start = Offset(0f, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
                    .padding(ZdravnicaAppTheme.dimens.size4),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_plus),
                        contentDescription = contentDescription,
                        tint = ZdravnicaAppTheme.colors.baseAppColor.gray200
                    )

                    Text(
                        modifier = Modifier.padding(
                            top = ZdravnicaAppTheme.dimens.size15,
                            bottom = ZdravnicaAppTheme.dimens.size15,
                            end = ZdravnicaAppTheme.dimens.size15
                        ),
                        text = text,
                        color = if (isDisabled)
                            ZdravnicaAppTheme.colors.baseAppColor.gray800
                        else
                            ZdravnicaAppTheme.colors.baseAppColor.gray200
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderBalmButton() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        OrderBalmButton(
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size44),
            isDisabled = false,
            contentDescription = "Increase",
            text = "Бальзам залит",
            onClick = { }
        )
    }
}