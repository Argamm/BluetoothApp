package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.ORDER_DESCRIPTION
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.components.buttons.ui.OrderBalmButton
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getBalmInfoByTitle
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import com.zdravnica.uikit.components.tooltip.TooltipPopup
import com.zdravnica.uikit.resources.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckBalmCountAndOrder(
    modifier: Modifier = Modifier,
    balmInfo: List<ChipBalmInfoModel>,
    isBalmCountZero: (String) -> Boolean,
    startProcedure: () -> Unit,
    orderBalm: () -> Unit,
    balmFilled: () -> Unit,
) {
    val context = LocalContext.current
    val isAnyBalmCountZero = balmInfo.any { isBalmCountZero(context.getString(it.balmName)) }

    Column(
        modifier = modifier
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
                .padding(top = ZdravnicaAppTheme.dimens.size12),
            horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4),
            verticalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
        ) {
            balmInfo.forEachIndexed { _, balm ->
                val balmCountIsZero = isBalmCountZero(context.getString(balm.balmName))

                BalmInfoText(
                    text = stringResource(id = balm.balmName),
                    isBalmCountZero = balmCountIsZero,
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
                        balmFilled.invoke()
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
                    onClick = { orderBalm.invoke() }
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

            TooltipPopup(
                modifier = Modifier
                    .padding(start = ZdravnicaAppTheme.dimens.size8),
                isEnableToClick = true,
                requesterView = { modifier ->
                    BigButton(
                        modifier = modifier
                            .wrapContentSize(),
                        bigButtonModel = BigButtonModel(
                            buttonText = stringResource(R.string.procedure_screen_start_procedure),
                            textModifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                            isEnabled = !isAnyBalmCountZero,
                            onClick = {
                                if (isAnyBalmCountZero) {
//                            isBigButtonClick = true
//                            showInfoMessage = true
//                            position = bigButtonPosition
                                } else {
                                    startProcedure.invoke()
                                }
                            }
                        ),
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
}

@Preview
@Composable
private fun CheckBalmCountAndOrderPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        getBalmInfoByTitle(R.string.select_product_skin)?.let {
            CheckBalmCountAndOrder(
                balmInfo = it,
                isBalmCountZero = { false },
                startProcedure = {},
                orderBalm = {}
            ) {}
        }
    }
}