package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zdravnica.app.screens.procedure.ui.BalmInfoText
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.ChipBalmInfoModel
import com.zdravnica.uikit.resources.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckBalmCountAndOrderTablet(
    modifier: Modifier = Modifier,
    balmInfo: List<ChipBalmInfoModel>,
    startProcedure: () -> Unit,
    isBalmCountZero: (String) -> Boolean,
    orderBalm: () -> Unit,
    balmFilled: () -> Unit
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
            verticalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
        ) {
            balmInfo.forEachIndexed { _, balm ->
                val balmCountIsZero = isBalmCountZero(context.getString(balm.balmName))

                if (balm.key != 4 && balm.key != 5) {
                    BalmInfoText(
                        text = stringResource(id = balm.balmName),
                        isBalmCountZero = balmCountIsZero,
                    )
                }
            }
        }

        if (isAnyBalmCountZero) {
            ZeroBalmContent(
                onClickOrderBalmButton = {
                    orderBalm.invoke()
                },
                onClickBalmFilledButton = {
                    balmFilled.invoke()
                },
            )
        } else {
            NonZeroBalmContent(
                onClickOrderBalmButton = { orderBalm.invoke() },
                onClickBigButton = {
                    startProcedure.invoke()
                }
            )
        }
    }
}