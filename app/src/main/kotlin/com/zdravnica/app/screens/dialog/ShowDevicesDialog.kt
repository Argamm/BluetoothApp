package com.zdravnica.app.screens.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.connecting_page.models.DeviceUIModel
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.BLUETOOTH_ICON_DESCRIPTION
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.components.dialogs.YTBaseDialog
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.getViewModel

@Composable
fun ShowDevicesDialog(
    modifier: Modifier = Modifier,
    viewModel: ConnectingPageViewModel = getViewModel(),
    onDismiss: (() -> Unit)? = null,
    onSelectedItemDevice: ((item: DeviceUIModel) -> Unit)? = null
) {
    val viewState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.observeBluetoothDevices()
    }

    YTBaseDialog(
        modifier = modifier
            .fillMaxSize()
            .requiredHeightIn(max = ZdravnicaAppTheme.dimens.size500),
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                if (viewState.pairedDevices.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.paired_devices),
                            style = ZdravnicaAppTheme.typography.bodyLargeMedium.copy(
                                color = ZdravnicaAppTheme.colors.baseAppColor.info500
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ZdravnicaAppTheme.colors.baseAppColor.gray900)
                                .padding(ZdravnicaAppTheme.dimens.size16)
                        )
                    }
                    items(viewState.pairedDevices.withIndex().toList(), key = { indexedItem ->
                        "${indexedItem.value.macAddress}-${indexedItem.index}"
                    }) { (_, item) ->
                        Column {
                            TextButton(
                                onClick = { onSelectedItemDevice?.invoke(item) }
                            ) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    style = ZdravnicaAppTheme.typography.bodyNormalSemi.copy(
                                        color = ZdravnicaAppTheme.colors.baseAppColor.info500
                                    ),
                                    maxLines = COUNT_ONE,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            YTHorizontalDivider()
                        }
                    }
                }

                if (viewState.scannedDevices.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.scanned_devices),
                            style = ZdravnicaAppTheme.typography.bodyLargeMedium.copy(
                                color = ZdravnicaAppTheme.colors.baseAppColor.info500
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ZdravnicaAppTheme.colors.baseAppColor.gray900)
                                .padding(ZdravnicaAppTheme.dimens.size16)
                        )
                    }
                    items(viewState.scannedDevices.withIndex().toList(), key = { indexedItem ->
                        "${indexedItem.value.macAddress}-${indexedItem.index}"
                    }) { (_, item) ->
                        Column {
                            TextButton(
                                onClick = { onSelectedItemDevice?.invoke(item) }
                            ) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    style = ZdravnicaAppTheme.typography.bodyNormalSemi.copy(
                                        color = ZdravnicaAppTheme.colors.baseAppColor.info500
                                    ),
                                    maxLines = COUNT_ONE,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            YTHorizontalDivider()
                        }
                    }
                }

                if (viewState.pairedDevices.isEmpty() && viewState.scannedDevices.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .requiredHeightIn(min = ZdravnicaAppTheme.dimens.size204)
                                .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR24)
                                .background(ZdravnicaAppTheme.colors.baseAppColor.gray900),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bluetooth),
                                contentDescription = BLUETOOTH_ICON_DESCRIPTION,
                                modifier = Modifier.requiredSizeIn(
                                    minHeight = ZdravnicaAppTheme.dimens.size120,
                                    minWidth = ZdravnicaAppTheme.dimens.size120,
                                ),
                                tint = ZdravnicaAppTheme.colors.baseAppColor.info500
                            )
                        }
                    }
                }
            }
        },
        onDismiss = onDismiss
    )
}