package com.zdravnica.app.navigation.app.navgraphs

import androidx.compose.runtime.Stable
import com.zdravnica.app.navigation.app.models.AppDestinationsRouteNames

@Stable
sealed class AppNavGraph(val route: String) {
    data object Root : AppNavGraph(route = AppDestinationsRouteNames.ROOT)
    data object Connection : AppNavGraph(route = AppDestinationsRouteNames.CONNECTION)

    data object BluetoothDevicesDialog : AppNavGraph(route = AppDestinationsRouteNames.SHOW_DEVICE_DIALOG)
    data object Main : AppNavGraph(route = AppDestinationsRouteNames.MAIN)
    data object CancelProcedureDialog : AppNavGraph(route = AppDestinationsRouteNames.SHOW_CANCEL_PROCEDURE_DIALOG)
    data object SelectProcedureScreen : AppNavGraph(route = AppDestinationsRouteNames.SELECT_PROCEDURE_SCREEN)
    data object ManuScreen : AppNavGraph(route = AppDestinationsRouteNames.MENU_SCREEN)
    data object ProcedureScreen : AppNavGraph(route = AppDestinationsRouteNames.PROCEDURE_SCREEN)
    data object PreparingTheCabinScreen : AppNavGraph(route = AppDestinationsRouteNames.PREPARING_THE_CABIN_SCREEN)
    data object ProcedureProcessScreen : AppNavGraph(route = AppDestinationsRouteNames.PROCEDURE_PROCESS_SCREEN)
}