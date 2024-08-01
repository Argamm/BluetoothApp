package com.zdravnica.app.screens.connecting_page

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.utils.getDimensionBasedOnDeviceType
import com.zdravnica.bluetooth.data.BluetoothController
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uiKit.resources.R
import com.zdravnica.uikit.components.buttons.models.PrimaryButtonModel
import com.zdravnica.uikit.components.buttons.ui.PrimaryButton
import com.zdravnica.uikit.preview.AppPreview
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectingPageScreen(
    modifier: Modifier = Modifier,
    bluetoothController: BluetoothController = get(),
    viewModel: ConnectingPageViewModel = koinViewModel()
) {

    val viewState = viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val btLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

        }
    }
    Column(
        modifier = modifier.padding(horizontal = ZdravnicaAppTheme.dimens.size48),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo_splash_screen),
            contentDescription = "App logo",
            modifier = Modifier.requiredSize(
                getDimensionBasedOnDeviceType(
                    isTablet = ZdravnicaAppTheme.dimens.size250,
                    isMobile = ZdravnicaAppTheme.dimens.size250
                )
            )
        )

        Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size24))

        PrimaryButton(
            primaryButtonModel = PrimaryButtonModel(
                buttonText = stringResource(id = R.string.connecting_with_bt_button_text),
                textModifier = Modifier
                    .wrapContentSize()
                    .basicMarquee(),
                isEnabled = true,
                onClick = {
                    if (bluetoothController.isEnabledBluetooth().not()) {
                        btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                    } else {
                        viewModel.startScan()
                        bluetoothController.startDiscovery()
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@AppPreview
@Composable
private fun ConnectingPageScreenPreview() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ConnectingPageScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(ZdravnicaAppTheme.colors.primaryBackgroundColor)
        )
    }
}
