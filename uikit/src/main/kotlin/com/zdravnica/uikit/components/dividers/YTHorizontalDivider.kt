package com.zdravnica.uikit.components.dividers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun YTHorizontalDivider(
    modifier: Modifier = Modifier,
    dividerColor: Color = ZdravnicaAppTheme.colors.baseAppColor.gray900,
    height: Dp = ZdravnicaAppTheme.dimens.size1
) {
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height),
        color = dividerColor
    )
}
