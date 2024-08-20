package com.zdravnica.app.screens.connecting_page.procedure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.HEIGHT_OF_TOOLTIP
import com.zdravnica.uikit.ORDER_DESCRIPTION
import com.zdravnica.uikit.TOOLTIP_SHOWING_DURATION_2500
import com.zdravnica.uikit.WIDTH_OF_TOOLTIP
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.components.buttons.ui.OrderBalmButton
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getBalmInfoByTitle
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import com.zdravnica.uikit.components.tooltip.TooltipInfoMessage
import com.zdravnica.uikit.resources.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckBalmCountAndOrder(
    modifier: Modifier = Modifier,
    balmInfo: List<ChipBalmInfoModel>,
    startProcedure: () -> Unit,
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
                        if (showInfoMessage) {
                            showInfoMessage = false
                        }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ZdravnicaAppTheme.dimens.size12),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderBalmButton(
                    isDisabled = false,
                    contentDescription = ORDER_DESCRIPTION,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success_outline),
                    text = stringResource(R.string.procedure_screen_balm_filled),
                    onClick = {

                    }
                )
                OrderBalmButton(
                    isDisabled = false,
                    contentDescription = ORDER_DESCRIPTION,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                    text = stringResource(R.string.procedure_screen_order),
                    onClick = {

                    }
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = ZdravnicaAppTheme.dimens.size12),
                text = stringResource(R.string.menu_screen_add_balm_instruction),
                style = ZdravnicaAppTheme.typography.bodyNormalRegular,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray500,
                textAlign = TextAlign.Center
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ZdravnicaAppTheme.dimens.size12),
                contentAlignment = Alignment.Center
            ) {
                OrderBalmButton(
                    isDisabled = false,
                    contentDescription = ORDER_DESCRIPTION,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                    text = stringResource(R.string.procedure_screen_order_balm),
                    onClick = { }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = if (isAnyBalmCountZero)
                        ZdravnicaAppTheme.dimens.size2
                    else
                        ZdravnicaAppTheme.dimens.size40
                ),
            contentAlignment = Alignment.Center
        ) {
            BigButton(
                modifier = Modifier
                    .wrapContentSize()
                    .onGloballyPositioned { coordinates ->
                        bigButtonPosition = coordinates
                            .positionInRoot()
                            .let {
                                IntOffset(it.x.toInt(), it.y.toInt())
                            }
                    },
                bigButtonModel = BigButtonModel(
                    buttonText = stringResource(R.string.procedure_screen_start_procedure),
                    textModifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                    isEnabled = !isAnyBalmCountZero,
                    onClick = {
                        if (isAnyBalmCountZero) {
                            isBigButtonClick = true
                            showInfoMessage = true
                            position = bigButtonPosition
                        } else {
                            startProcedure.invoke()
                        }
                    }
                ),
            )
        }
    }

    if (showInfoMessage) {
        val isRightIcon = position.x > screenWidthPx / COUNT_THREE || !firstIconClicked
        TooltipInfoMessage(
            message = stringResource(R.string.procedure_screen_tooltip_message),
            offset = position.copy(
                x = position.x - if (isBigButtonClick)
                    position.x
                else
                    WIDTH_OF_TOOLTIP,
                y = position.y - HEIGHT_OF_TOOLTIP
            ),
            modifier = Modifier.padding(
                top = ZdravnicaAppTheme.dimens.size8,
                start = if (isRightIcon)
                    ZdravnicaAppTheme.dimens.size24
                else
                    ZdravnicaAppTheme.dimens.size12
            ),
            isRightIcon = isRightIcon,
            isBigButton = isBigButtonClick
        )
    }
}

@Preview
@Composable
private fun CheckBalmCountAndOrderPrev() {
    getBalmInfoByTitle(R.string.select_product_skin)?.let {
        CheckBalmCountAndOrder(
            balmInfo = it,
        ){}
    }
}