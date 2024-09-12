package com.zdravnica.app.screens.connecting_page.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.zdravnica.app.screens.connecting_page.models.ConnectingPageViewState
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.utils.getDimensionBasedOnDeviceType
import com.zdravnica.app.utils.isTablet
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.APP_LOGO_DESCRIPTION
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.resources.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectingPageContentScreen(
    viewModel: ConnectingPageViewModel,
    viewState: ConnectingPageViewState,
    modifier: Modifier = Modifier,
    showAllBluetoothDevicesDialog: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var hasBluetoothPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            hasBluetoothPermission = true
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )
        } else {
            hasBluetoothPermission = true
        }
    }

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
