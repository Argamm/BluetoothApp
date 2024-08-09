package com.zdravnica.uikit.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R

@Composable
fun YTBaseDialog(
    modifier: Modifier = Modifier,
    showCloseIcon: Boolean = true,
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = { onDismiss?.invoke() }
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR24)
                .background(color = ZdravnicaAppTheme.colors.primaryBackgroundColor)
                .padding(ZdravnicaAppTheme.dimens.size18)
        ) {
            Column {
                if (showCloseIcon) {
                    IconButton(
                        modifier = Modifier
                            .requiredSize(ZdravnicaAppTheme.dimens.size24)
                            .align(Alignment.End),
                        onClick = { onDismiss?.invoke() }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = R.drawable.ic_close
                            ),
                            contentDescription = "Close",
                            modifier = Modifier
                                .requiredSize(ZdravnicaAppTheme.dimens.size24),
                            tint = ZdravnicaAppTheme.colors.baseAppColor.gray200
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = ZdravnicaAppTheme.dimens.size30)
                ) {
                    title?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            title.invoke()
                        }
                    }

                    content?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = ZdravnicaAppTheme.dimens.size24),
                            contentAlignment = Alignment.Center
                        ) {
                            content.invoke()
                        }
                    }

                    buttons?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = ZdravnicaAppTheme.dimens.size36),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            buttons.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun YTBaseDialogRenterPreview() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        YTBaseDialog(
            content = { Box(modifier = Modifier.width(200.dp)) }
        )
    }
}
