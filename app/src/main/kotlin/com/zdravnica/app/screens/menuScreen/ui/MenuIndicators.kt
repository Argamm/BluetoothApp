package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.ERROR_ICON_DESCRIPTION
import com.zdravnica.uikit.INDICATOR_ICON_DESCRIPTION
import com.zdravnica.uikit.components.tooltip.TooltipPopup
import com.zdravnica.uikit.resources.R

@Composable
fun MenuIndicators(
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = ZdravnicaAppTheme.dimens.size4,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(top = ZdravnicaAppTheme.dimens.size16)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size16)
        ) {
            Text(
                text = stringResource(R.string.menu_screen_indicators_title),
                style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray500
            )

            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size16))

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_fan),
                indicatorText = stringResource(R.string.menu_screen_fan),
                errorIconVisible = true,
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_ten),
                indicatorText = stringResource(R.string.menu_screen_ten),
                errorIconVisible = true,
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_compressor),
                indicatorText = stringResource(R.string.menu_screen_compressor),
                errorIconVisible = true,
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_ik),
                indicatorText = stringResource(R.string.menu_screen_ik),
                errorIconVisible = false,
            )
        }
    }
}

@Composable
fun IndicatorRowLine(
    modifier: Modifier = Modifier,
    indicatorIcon: ImageVector,
    indicatorText: String,
    errorIconVisible: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = indicatorIcon,
            contentDescription = INDICATOR_ICON_DESCRIPTION,
            tint = ZdravnicaAppTheme.colors.baseAppColor.gray200,
            modifier = Modifier.size(ZdravnicaAppTheme.dimens.size18)
        )
        Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size8))
        Text(
            text = indicatorText,
            style = ZdravnicaAppTheme.typography.bodyNormalMedium,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray200
        )
        Spacer(modifier = Modifier.weight(1f))
        if (errorIconVisible) {
            TooltipPopup(
                modifier = Modifier
                    .padding(start = ZdravnicaAppTheme.dimens.size8),
                requesterView = { modifier ->
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                        contentDescription = ERROR_ICON_DESCRIPTION,
                        tint = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                        modifier = modifier
                    )
                },
                tooltipContent = {
                    Text(
                        maxLines = COUNT_THREE,
                        minLines = COUNT_THREE,
                        modifier = Modifier.padding(
                            horizontal = ZdravnicaAppTheme.dimens.size8,
                            vertical = ZdravnicaAppTheme.dimens.size4
                        ).widthIn(max = ZdravnicaAppTheme.dimens.size152),
                        text = stringResource(R.string.procedure_screen_tooltip_message),
                        style = ZdravnicaAppTheme.typography.bodyXSMedium,
                        color = Color.Black,
                    )
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))
}

@Preview
@Composable
fun PreviewMenuIndicators() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuIndicators()
    }
}