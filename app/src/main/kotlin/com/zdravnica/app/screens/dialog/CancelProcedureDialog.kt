package com.zdravnica.app.screens.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.CLOSE_ICON_DESCRIPTION
import com.zdravnica.uikit.resources.R

@Composable
fun CancelProcedureDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    onClose: () -> Unit,
    onNoClick: () -> Unit,
    onYesClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
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
                                .clickable(onClick = onClose)
                        )
                    }
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size18))
                    Text(
                        text = titleText,
                        style = ZdravnicaAppTheme.typography.bodyMediumRegular,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                    )
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size24))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onNoClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            elevation = null
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    top = ZdravnicaAppTheme.dimens.size12,
                                    bottom = ZdravnicaAppTheme.dimens.size12
                                ),
                                text = stringResource(R.string.menu_screen_cancel),
                                style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                            )
                        }
                        Button(
                            onClick = onYesClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = ZdravnicaAppTheme.colors.baseAppColor.primary500),
                            shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = ZdravnicaAppTheme.dimens.size36,
                                    end = ZdravnicaAppTheme.dimens.size36,
                                    top = ZdravnicaAppTheme.dimens.size12,
                                    bottom = ZdravnicaAppTheme.dimens.size12
                                ),
                                text = stringResource(R.string.menu_screen_yes),
                                color = Color.White,
                                style = ZdravnicaAppTheme.typography.bodyMediumSemi
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCancelProcedureDialog() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        CancelProcedureDialog(
            titleText = "cancel?",
            onClose = {},
            onNoClick = {},
            onYesClick = {}
        )
    }
}