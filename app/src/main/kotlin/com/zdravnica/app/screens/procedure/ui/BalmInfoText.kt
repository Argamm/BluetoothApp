package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_TWO
import com.zdravnica.uikit.components.tooltip.TooltipPopup
import com.zdravnica.uikit.resources.R

@Composable
fun BalmInfoText(
    modifier: Modifier = Modifier,
    text: String,
    isBalmCountZero: Boolean,
) {
    TooltipPopup(
        modifier = modifier.padding(start = ZdravnicaAppTheme.dimens.size4),
        isEnableToClick = isBalmCountZero,
        requesterView = { boxModifier ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = boxModifier
                    .background(
                        color = if (isBalmCountZero)
                            ZdravnicaAppTheme.colors.baseAppColor.error1000
                        else
                            ZdravnicaAppTheme.colors.baseAppColor.gray1000,
                        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size12)
                    )
                    .padding(ZdravnicaAppTheme.dimens.size4)
                    .wrapContentSize()
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
        },
        tooltipContent = {
            Text(
                maxLines = COUNT_TWO,
                minLines = COUNT_TWO,
                modifier = Modifier
                    .padding(
                        horizontal = ZdravnicaAppTheme.dimens.size8,
                        vertical = ZdravnicaAppTheme.dimens.size4
                    )
                    .widthIn(max = ZdravnicaAppTheme.dimens.size152)
                    .heightIn(max = ZdravnicaAppTheme.dimens.size100),
                text = stringResource(R.string.procedure_screen_tooltip_message),
                style = ZdravnicaAppTheme.typography.bodyXSMedium,
                color = Color.Black,
            )
        }
    )
}

@Preview
@Composable
private fun BalmInfoTextPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        BalmInfoText(text = "Балм", isBalmCountZero = false)
    }
}