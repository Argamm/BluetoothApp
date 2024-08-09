package com.zdravnica.uikit.components.buttons.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun GradientIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    isDisabled: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR20)
            .border(
                BorderStroke(
                    width = ZdravnicaAppTheme.dimens.size4,
                    brush = Brush.linearGradient(
                        colors = ZdravnicaAppTheme.colors.bigChipsStateColor.borderStrokeGradientColors,
                        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
                        end = androidx.compose.ui.geometry.Offset(0f, 0f)
                    )
                ),
                shape = ZdravnicaAppTheme.roundedCornerShape.shapeR20
            )
            .padding(ZdravnicaAppTheme.dimens.size4)
            .border(
                BorderStroke(
                    width = ZdravnicaAppTheme.dimens.size3,
                    brush = Brush.linearGradient(
                        colors = ZdravnicaAppTheme.colors.bigChipsStateColor.borderStrokeGradientColors,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    )
                ),
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size16)
            )
            .background(
                brush = Brush.linearGradient(
                    ZdravnicaAppTheme.colors.bigChipsStateColor.backgroundGradientColors
                )
            ),
        enabled = !isDisabled
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

@Preview(showBackground = true)
@Composable
fun PreviewValueAdjuster() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        GradientIconButton(
            onClick = { },
            icon = Icons.Default.Add,
            contentDescription = "Increase",
            isDisabled = false,
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size44)
        )
    }
}