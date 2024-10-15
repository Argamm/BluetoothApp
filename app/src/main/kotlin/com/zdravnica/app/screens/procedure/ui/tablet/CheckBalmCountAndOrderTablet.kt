package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zdravnica.app.screens.procedure.ui.BalmInfoText
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.TOOLTIP_SHOWING_DURATION_2500
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import com.zdravnica.uikit.components.tooltip.TooltipInfoMessage
import com.zdravnica.uikit.resources.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckBalmCountAndOrderTablet(
    modifier: Modifier = Modifier,
    balmInfo: List<ChipBalmInfoModel>,
    startProcedure: () -> Unit,
    orderBalm: () -> Unit,
) {
    val isAnyBalmCountZero = balmInfo.any { it.isBalmCountZero }
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    var selectedBalm by remember { mutableStateOf<ChipBalmInfoModel?>(null) }
    var showInfoMessage by remember { mutableStateOf(false) }
    var isBigButtonClick by remember { mutableStateOf(false) }
    var firstIconClicked by remember { mutableStateOf(false) }
    var position by remember { mutableStateOf(IntOffset.Zero) }
    var bigButtonPosition by remember { mutableStateOf(IntOffset.Zero) }

    LaunchedEffect(showInfoMessage) {
        if (showInfoMessage) {
            delay(TOOLTIP_SHOWING_DURATION_2500)
            showInfoMessage = false
        }
    }

    Column(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    if (showInfoMessage) {
                        showInfoMessage = false
                    }
                }
            }
            .background(color = Color.White)
            .padding(PaddingValues(ZdravnicaAppTheme.dimens.size8))
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        if (isAnyBalmCountZero) {
            Text(
                text = stringResource(R.string.procedure_screen_need_balm),
                style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                color = ZdravnicaAppTheme.colors.baseAppColor.error500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = ZdravnicaAppTheme.dimens.size8),
                textAlign = TextAlign.Center
            )
        }

        FlowRow(
            modifier = Modifier
                .padding(top = ZdravnicaAppTheme.dimens.size12)
                .pointerInput(Unit) {
                    detectTapGestures {
                        showInfoMessage = false
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4),
            verticalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
        ) {
            balmInfo.forEachIndexed { index, balm ->
                BalmInfoText(
                    text = stringResource(id = balm.balmName),
                    isBalmCountZero = balm.isBalmCountZero,
                    onClick = { clickedPosition ->
                        if (balm.isBalmCountZero) selectedBalm = balm
                        firstIconClicked = index == 0
                        isBigButtonClick = false
                        showInfoMessage = !showInfoMessage
                        position = clickedPosition
                    }
                )
            }
        }

        if (isAnyBalmCountZero) {
            ZeroBalmContent(
                onClickOrderBalmButton = { orderBalm.invoke() },
                onClickBalmFilledButton = { /* Handle button click */ },
            )
        } else {
            NonZeroBalmContent(
                onClickOrderBalmButton = { orderBalm.invoke() },
                onClickBigButton = {
                    startProcedure.invoke()
                },
                onGloballyPositioned = { coordinates ->
                    bigButtonPosition = coordinates
                        .positionInRoot()
                        .let { IntOffset(it.x.toInt(), it.y.toInt()) }
                }
            )
        }
    }

    if (showInfoMessage) {
        val isRightIcon = position.x > screenWidthPx / COUNT_THREE || !firstIconClicked
        TooltipInfoMessage(
            message = stringResource(R.string.procedure_screen_tooltip_message),
            offset = position.copy(
                x = position.x - 1880,
                y = position.y - 844
            ),
            modifier = Modifier.padding(
                top = ZdravnicaAppTheme.dimens.size8,
                start = if (isRightIcon)
                    ZdravnicaAppTheme.dimens.size24
                else
                    ZdravnicaAppTheme.dimens.size12
            ),
            isFirstItem = firstIconClicked && position.x <= screenWidthPx / COUNT_THREE,
            isLastItem = !firstIconClicked && position.x > screenWidthPx / (COUNT_THREE / 2)
        )
    }
}