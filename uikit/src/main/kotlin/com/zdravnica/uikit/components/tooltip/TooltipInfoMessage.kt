package com.zdravnica.uikit.components.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun TooltipInfoMessage(
    modifier: Modifier = Modifier,
    message: String,
    offset: IntOffset,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false
) {
    Popup(alignment = Alignment.Center, offset = offset) {
        Column(
            horizontalAlignment = when {
                isFirstItem -> Alignment.Start
                isLastItem -> Alignment.End
                else -> Alignment.CenterHorizontally
            },
            modifier = modifier,
        ) {
            Card(
                shape = RoundedCornerShape(size = ZdravnicaAppTheme.dimens.size6),
                elevation = ZdravnicaAppTheme.dimens.size10,
                backgroundColor = Color.White,
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(size = ZdravnicaAppTheme.dimens.size6)
                        )
                ) {
                    Text(
                        text = message,
                        color = Color.Black,
                        style = ZdravnicaAppTheme.typography.bodyXSMedium,
                        modifier = Modifier.padding(
                            horizontal = ZdravnicaAppTheme.dimens.size8,
                            vertical = ZdravnicaAppTheme.dimens.size4
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(
                        PaddingValues(
                            start = if (isFirstItem) ZdravnicaAppTheme.dimens.size20 else 0.dp,
                            end = if (isLastItem) ZdravnicaAppTheme.dimens.size20 else 0.dp,
                        )
                    )
                    .size(
                        width = ZdravnicaAppTheme.dimens.size12,
                        height = ZdravnicaAppTheme.dimens.size6
                    )
                    .background(
                        color = Color.White,
                        shape = TriangleShape()
                    )
            )
        }
    }
}
