package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ERROR_ICON_DESCRIPTION
import com.zdravnica.uikit.HEIGHT_OF_TOOLTIP
import com.zdravnica.uikit.INDICATOR_ICON_DESCRIPTION
import com.zdravnica.uikit.TOOLTIP_SHOWING_DURATION_2500
import com.zdravnica.uikit.WIDTH_OF_TOOLTIP
import com.zdravnica.uikit.components.tooltip.TooltipInfoMessage
import com.zdravnica.uikit.resources.R
import kotlinx.coroutines.delay

@Composable
fun MenuIndicators(
    modifier: Modifier = Modifier,
) {
    var position by remember { mutableStateOf(IntOffset.Zero) }
    var showInfoMessage by remember { mutableStateOf(false) }

    LaunchedEffect(showInfoMessage) {
        if (showInfoMessage) {
            delay(TOOLTIP_SHOWING_DURATION_2500)
            showInfoMessage = false
        }
    }

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
                onClick = { clickedPosition ->
                    showInfoMessage = !showInfoMessage
                    position = clickedPosition
                }
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_ten),
                indicatorText = stringResource(R.string.menu_screen_ten),
                errorIconVisible = true,
                onClick = { clickedPosition ->
                    showInfoMessage = !showInfoMessage
                    position = clickedPosition
                }
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_compressor),
                indicatorText = stringResource(R.string.menu_screen_compressor),
                errorIconVisible = true,
                onClick = { clickedPosition ->
                    showInfoMessage = !showInfoMessage
                    position = clickedPosition
                }
            )

            IndicatorRowLine(
                indicatorIcon = ImageVector.vectorResource(id = R.drawable.ic_ik),
                indicatorText = stringResource(R.string.menu_screen_ik),
                errorIconVisible = false,
                onClick = { clickedPosition ->
                    showInfoMessage = !showInfoMessage
                    position = clickedPosition
                }
            )
        }
    }

    if (showInfoMessage) {
        TooltipInfoMessage(
            message = stringResource(R.string.procedure_screen_tooltip_message),
            offset = position.copy(
                x = position.x - WIDTH_OF_TOOLTIP,
                y = position.y - HEIGHT_OF_TOOLTIP
            ),
            modifier = Modifier.padding(
                top = ZdravnicaAppTheme.dimens.size8,
                start = ZdravnicaAppTheme.dimens.size24,
                end = ZdravnicaAppTheme.dimens.size10,
            ),
            isFirstItem = false,
            isLastItem = true
        )
    }
}

@Composable
fun IndicatorRowLine(
    modifier: Modifier = Modifier,
    indicatorIcon: ImageVector,
    indicatorText: String,
    errorIconVisible: Boolean,
    onClick: (IntOffset) -> Unit = {}
) {
    var offset by remember { mutableStateOf(IntOffset.Zero) }

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
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                contentDescription = ERROR_ICON_DESCRIPTION,
                tint = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                modifier = Modifier
                    .size(ZdravnicaAppTheme.dimens.size18)
                    .onGloballyPositioned { coordinates ->
                        offset = coordinates
                            .positionInRoot()
                            .let {
                                IntOffset(it.x.toInt(), it.y.toInt())
                            }
                    }
                    .clickable {
                        onClick(offset)
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
