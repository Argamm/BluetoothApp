package com.zdravnica.app.screens.dialog.cancelProcedure.ui.teblet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.zdravnica.app.screens.dialog.cancelProcedure.models.CancelProcedureDialogState
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.CLOSE_ICON_DESCRIPTION
import com.zdravnica.uikit.components.buttons.ui.ActionDialogButton
import com.zdravnica.uikit.resources.R

@Composable
fun CancelProcedureTabletDialog(
    modifier: Modifier = Modifier,
    state: CancelProcedureDialogState = CancelProcedureDialogState(),
) {
    Dialog(
        onDismissRequest = state.onClose,
    ) {
        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
        dialogWindowProvider?.window?.setDimAmount(0.9f)

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ZdravnicaAppTheme.dimens.size14
                )
        ) {
            Card(
                shape = ZdravnicaAppTheme.roundedCornerShape.shapeR16,
                backgroundColor = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(ZdravnicaAppTheme.dimens.size16)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = CLOSE_ICON_DESCRIPTION,
                            modifier = Modifier
                                .clickable(onClick = state.onClose)
                        )
                    }
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size18))
                    Text(
                        text = state.titleText,
                        style = ZdravnicaAppTheme.typography.bodyMediumRegular,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                    )
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size24))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ActionDialogButton(
                            text = stringResource(R.string.menu_screen_cancel),
                            onClick = state.onNoClick,
                            backgroundColor = Color.Transparent
                        )
                        ActionDialogButton(
                            text = stringResource(R.string.menu_screen_yes),
                            onClick = state.onYesClick,
                            backgroundColor = ZdravnicaAppTheme.colors.baseAppColor.primary500,
                            textColor = Color.White,
                            shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
                            paddingValues = PaddingValues(
                                start = ZdravnicaAppTheme.dimens.size36,
                                end = ZdravnicaAppTheme.dimens.size36,
                                top = ZdravnicaAppTheme.dimens.size12,
                                bottom = ZdravnicaAppTheme.dimens.size12
                            )
                        )
                    }
                }
            }
        }
    }
}