package com.zdravnica.app.navigation.app.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.zdravnica.app.navigation.app.navgraphs.AppNavGraph
import com.zdravnica.app.screens.connecting_page.ConnectingPageScreen
import com.zdravnica.app.screens.connecting_page.dialog.ShowDevicesDialog
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val connectivityViewModel:ConnectingPageViewModel = koinViewModel()

    NavHost(
        navController = navHostController,
        route = AppNavGraph.Root.route,
        startDestination = startDestination,
        builder = {
            composable(AppNavGraph.Connection.route) {
                ConnectingPageScreen(
                    viewModel = connectivityViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ZdravnicaAppTheme.colors.primaryBackgroundColor),
                    onShowAllDevicesDialog = {
                        navHostController.navigate(AppNavGraph.BluetoothDevicesDialog.route)
                    }
                )
            }

            dialog(AppNavGraph.BluetoothDevicesDialog.route) {
                ShowDevicesDialog(
                    viewModel = connectivityViewModel,
                    onDismiss = navHostController::navigateUp,
                    onSelectedItemDevice = {
                        connectivityViewModel.connectingToDevice(it)
                        navHostController.navigateUp()
                    }
                )
            }
        }
    )
}
