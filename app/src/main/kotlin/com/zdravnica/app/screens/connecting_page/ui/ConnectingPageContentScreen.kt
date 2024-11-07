package com.zdravnica.app.screens.connecting_page.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.location.LocationManager
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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
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
    val locationClient = LocationServices.getSettingsClient(context)
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val locationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
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

    if (!locationEnabled) {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        val locationSettingsRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()

        var locationDialogShown by remember { mutableStateOf(false) }

        locationClient.checkLocationSettings(locationSettingsRequest)
            .addOnCompleteListener { task ->
                try {
                    task.getResult(ApiException::class.java)
                } catch (ex: ApiException) {
                    when (ex.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            if (!locationDialogShown) {
                                (ex as ResolvableApiException).startResolutionForResult(
                                    context as Activity,
                                    REQUEST_CODE_LOCATION_SETTINGS
                                )
                                locationDialogShown = true
                            }
                        }

                        else -> {
                            Toast.makeText(
                                context,
                                "Couldn't change location settings",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
    }

    LaunchedEffect(viewModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
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
                ZdravnicaAppTheme.dimens.size100
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

private const val REQUEST_CODE_LOCATION_SETTINGS = 1001
