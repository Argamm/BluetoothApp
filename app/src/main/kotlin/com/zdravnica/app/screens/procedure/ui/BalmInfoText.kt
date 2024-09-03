package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R

@Composable
fun BalmInfoText(
    text: String,
    isBalmCountZero: Boolean,
    onClick: (IntOffset) -> Unit = {}
) {
    var offset by remember { mutableStateOf(IntOffset.Zero) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = if (isBalmCountZero)
                    ZdravnicaAppTheme.colors.baseAppColor.error1000
                else
                    ZdravnicaAppTheme.colors.baseAppColor.gray1000,
                shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size12)
            )
            .padding(ZdravnicaAppTheme.dimens.size4)
            .wrapContentSize()
            .onGloballyPositioned { coordinates ->
                offset = coordinates
                    .positionInRoot()
                    .let {
                        IntOffset(it.x.toInt(), it.y.toInt())
                    }
            }
            .clickable {
                if (isBalmCountZero) {
                    onClick(offset)
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success_solid),
                contentDescription = null,
                tint = if (isBalmCountZero)
                    ZdravnicaAppTheme.colors.baseAppColor.error500
                else
                    ZdravnicaAppTheme.colors.baseAppColor.gray200,
                modifier = Modifier.size(ZdravnicaAppTheme.dimens.size16)
            )
            Text(
                text = text,
                color = if (isBalmCountZero)
                    ZdravnicaAppTheme.colors.baseAppColor.error500
                else
                    ZdravnicaAppTheme.colors.baseAppColor.gray300,
                style = ZdravnicaAppTheme.typography.bodyXSMedium,
                modifier = Modifier.padding(
                    top = ZdravnicaAppTheme.dimens.size2,
                    start = ZdravnicaAppTheme.dimens.size2,
                    end = ZdravnicaAppTheme.dimens.size8
                )
            )
        }
    }
}

@Preview
@Composable
private fun BalmInfoTextPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        BalmInfoText(text = "Балм", isBalmCountZero = false)
    }
}