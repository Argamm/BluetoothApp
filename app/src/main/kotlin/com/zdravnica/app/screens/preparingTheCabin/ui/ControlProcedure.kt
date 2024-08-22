package com.zdravnica.app.screens.preparingTheCabin.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.resources.R

@Composable
fun ControlProcedure(
    procedureState: String,
    progress: Int,
    onCancelProcedure: () -> Unit,
    onProcedureComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ZdravnicaAppTheme.dimens.size16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = procedureState,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray300,
            style = ZdravnicaAppTheme.typography.bodyMediumRegular
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size68))

        if (progress < COUNT_TO_100) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ZdravnicaAppTheme.dimens.size24)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onCancelProcedure.invoke()
                    },
                text = stringResource(R.string.preparing_the_cabin_cancel_procedure),
                style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                textAlign = TextAlign.Center
            )

        } else {
            BigButton(
                modifier = Modifier
                    .wrapContentSize(),
                bigButtonModel = BigButtonModel(
                    buttonText = stringResource(R.string.procedure_screen_start_procedure),
                    textModifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                    isEnabled = true,
                    onClick = onProcedureComplete
                ),
                bigBtnStateColors = ZdravnicaAppTheme.colors.bigButtonStateColor.copy(
                    borderStrokeColor = ZdravnicaAppTheme.colors.baseAppColor.success700,
                    enabledBackground = ZdravnicaAppTheme.colors.baseAppColor.success500
                )
            )
        }
    }
}