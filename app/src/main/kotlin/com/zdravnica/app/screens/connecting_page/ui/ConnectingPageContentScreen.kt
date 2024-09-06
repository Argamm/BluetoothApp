package com.zdravnica.app.screens.connecting_page.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.app.utils.getDimensionBasedOnDeviceType
import com.zdravnica.app.utils.isTablet
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.APP_LOGO_DESCRIPTION
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.resources.R

// TODO add launcher for get permissions BT and location
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectingPageContentScreen(
    viewState: ConnectingPageViewState,
    modifier: Modifier = Modifier,
    showAllBluetoothDevicesDialog: (() -> Unit)? = null
) {

    val btLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            showAllBluetoothDevicesDialog?.invoke()
        }
    }

    Column(
        modifier = modifier.padding(
            horizontal = if (isTablet())
                ZdravnicaAppTheme.dimens.size280
            else
                ZdravnicaAppTheme.dimens.size48
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo_splash_screen),
            contentDescription = APP_LOGO_DESCRIPTION,
            modifier = Modifier
                .requiredSize(
                    getDimensionBasedOnDeviceType(
                        isTablet = ZdravnicaAppTheme.dimens.size250,
                        isMobile = ZdravnicaAppTheme.dimens.size250
                    )
                )
        )

        Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size24))

        BigButton(
            bigButtonModel = BigButtonModel(
                buttonText = stringResource(id = R.string.connecting_with_bt_button_text),
                textModifier = Modifier
                    .wrapContentSize()
                    .basicMarquee(),
                isEnabled = true,
                onClick = {
                    if (viewState.isBtConnected.not()) {
                        btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                    } else {
                        showAllBluetoothDevicesDialog?.invoke()
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
