package com.zdravnica.app.navigation.app.navgraphs

import androidx.compose.runtime.Stable
import com.zdravnica.app.navigation.app.models.AppDestinationsRouteNames

@Stable
sealed class AppNavGraph(val route: String) {
    data object Root : AppNavGraph(route = AppDestinationsRouteNames.ROOT)
    data object Connection : AppNavGraph(route = AppDestinationsRouteNames.CONNECTION)

    data object BluetoothDevicesDialog : AppNavGraph(route = AppDestinationsRouteNames.SHOW_DEVICE_DIALOG)
    data object Main : AppNavGraph(route = AppDestinationsRouteNames.MAIN)
}