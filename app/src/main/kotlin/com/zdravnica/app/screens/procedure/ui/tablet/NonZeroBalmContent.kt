package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ORDER_DESCRIPTION
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.components.buttons.ui.OrderBalmButton
import com.zdravnica.uikit.resources.R


@Composable
fun NonZeroBalmContent(
    onClickOrderBalmButton: () -> Unit,
    onClickBigButton: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = ZdravnicaAppTheme.dimens.size12),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderBalmButton(
                isDisabled = false,
                contentDescription = ORDER_DESCRIPTION,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                text = stringResource(R.string.procedure_screen_order_balm),
                onClick = onClickOrderBalmButton
            )
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size36))
            BigButton(
                modifier = Modifier
                    .padding(vertical = ZdravnicaAppTheme.dimens.size18)
                    .wrapContentSize(),
                bigButtonModel = BigButtonModel(
                    buttonText = stringResource(R.string.procedure_screen_start_procedure),
                    textModifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                    isEnabled = true,
                    onClick = onClickBigButton
                )
            )
        }
    }
}