package com.zdravnica.app.screens.statusScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zdravnica.uikit.resources.R
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton

@Composable
fun BottomButtons(
    modifier: Modifier = Modifier,
    onSupportClick: () -> Unit,
    onActionClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ZdravnicaAppTheme.dimens.size36),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        TextButton(
            onClick = onSupportClick,
            modifier = Modifier
                .padding()
        ) {
            Text(
                text = stringResource(R.string.status_info_support),
                style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray200
            )
        }

        BigButton(
            modifier = Modifier
                .wrapContentSize(),
            bigButtonModel = BigButtonModel(
                buttonText = stringResource(R.string.status_info_ok),
                textModifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                isEnabled = true,
                onClick = onActionClick
            ),
            bigBtnStateColors = ZdravnicaAppTheme.colors.bigButtonStateColor.copy(
                borderStrokeColor = ZdravnicaAppTheme.colors.baseAppColor.primary500,
                enabledBackground = ZdravnicaAppTheme.colors.baseAppColor.primary500
            )
        )
    }
}
