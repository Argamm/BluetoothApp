package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ORDER_DESCRIPTION
import com.zdravnica.uikit.components.buttons.ui.OrderBalmButton
import com.zdravnica.uikit.resources.R

@Composable
fun ZeroBalmContent(
    onClickOrderBalmButton: () -> Unit,
    onClickBalmFilledButton: () -> Unit,
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
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success_outline),
                text = stringResource(R.string.procedure_screen_balm_filled),
                onClick = onClickBalmFilledButton
            )
            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size36))
            OrderBalmButton(
                isDisabled = false,
                contentDescription = ORDER_DESCRIPTION,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                text = stringResource(R.string.procedure_screen_order),
                onClick = onClickOrderBalmButton
            )
        }
    }

    Text(
        modifier = Modifier
            .padding(top = ZdravnicaAppTheme.dimens.size12),
        text = stringResource(R.string.menu_screen_add_balm_instruction),
        style = ZdravnicaAppTheme.typography.bodyNormalRegular,
        color = ZdravnicaAppTheme.colors.baseAppColor.gray500,
        textAlign = TextAlign.Center
    )
}