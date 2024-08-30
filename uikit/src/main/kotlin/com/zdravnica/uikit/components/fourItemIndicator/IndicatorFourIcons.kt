package com.zdravnica.uikit.components.fourItemIndicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.resources.R

@Composable
fun IndicatorFourIcons(iconStates: SnapshotStateList<IconState>) {
    Row(
        modifier = Modifier
            .background(
                color = ZdravnicaAppTheme.colors.baseAppColor.primary500,
                shape = CircleShape
            ),
        horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size8)

    ) {
        val icons = listOf(
            R.drawable.ic_fan,
            R.drawable.ic_ten,
            R.drawable.ic_compressor,
            R.drawable.ic_ik
        )
        val iconCount = icons.size

        icons.zip(iconStates).forEachIndexed { index, (iconResId, state) ->
            IconInCenter(
                iconResId = iconResId,
                iconState = state,
                startPadding = if (index == 0) ZdravnicaAppTheme.dimens.size8 else 0.dp,
                endPadding = if (index == iconCount - 1) ZdravnicaAppTheme.dimens.size8 else 0.dp
            )
        }
    }
}

@Composable
fun IconInCenter(
    iconResId: Int,
    startPadding: Dp = 0.dp,
    endPadding: Dp = 0.dp,
    iconState: IconState,
) {

    val tintColor = when (iconState) {
        IconState.ENABLED -> Color.White
        IconState.DISABLED -> ZdravnicaAppTheme.colors.baseAppColor.primary400
    }

    Box(
        modifier = Modifier
            .padding(
                start = startPadding,
                end = endPadding,
                top = ZdravnicaAppTheme.dimens.size3,
                bottom = ZdravnicaAppTheme.dimens.size3
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = tintColor,
            modifier = Modifier.size(ZdravnicaAppTheme.dimens.size18)
        )
    }
}
