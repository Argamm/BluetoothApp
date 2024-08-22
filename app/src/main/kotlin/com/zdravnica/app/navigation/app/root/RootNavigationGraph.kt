package com.zdravnica.app.navigation.app.root

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.zdravnica.app.navigation.app.navgraphs.AppNavGraph
import com.zdravnica.app.screens.connecting_page.ConnectingPageScreen
import com.zdravnica.app.screens.preparingTheCabin.ui.PreparingTheCabinScreen
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.screens.dialog.CancelProcedureDialog
import com.zdravnica.app.screens.dialog.ShowDevicesDialog
import com.zdravnica.app.screens.menuScreen.ui.MenuScreen
import com.zdravnica.app.screens.procedure.ui.ProcedureScreen
import com.zdravnica.app.screens.selectProcedure.ui.SelectProcedureScreen
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.SLIDE_ANIMATION_DURATION_300
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val connectivityViewModel: ConnectingPageViewModel = koinViewModel()

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
//                        navHostController.navigate(AppNavGraph.BluetoothDevicesDialog.route)
                        navHostController.navigate(AppNavGraph.SelectProcedureScreen.route)
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

            composable(AppNavGraph.SelectProcedureScreen.route) {
                SelectProcedureScreen(
                    navigateToMenuScreen = {
                        navHostController.navigate(AppNavGraph.ManuScreen.route)
                    },
                    navigateToProcedureScreen = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.ProcedureScreen.route}/${chipTitle}")
                    }
                )
            }

            composable(
                route = AppNavGraph.ManuScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
                    )
                }
            ) {
                MenuScreen(
                    onNavigateUp = {
                        navHostController.navigateUp()
                    },
                    navigateGToConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate("${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}")
                    }
                )
            }

            dialog(
                route = "${AppNavGraph.CancelProcedureDialog.route}/{navigateToSelectProcedure}/{cancelDialog}",
                arguments = listOf(navArgument("navigateToSelectProcedure") {
                    type = NavType.BoolType
                }, navArgument("cancelDialog") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val navigateToSelectProcedure =
                    backStackEntry.arguments?.getBoolean("navigateToSelectProcedure")
                val cancelDialog = backStackEntry.arguments?.getString("cancelDialog")

                CancelProcedureDialog(
                    titleText = cancelDialog ?: "",
                    onClose = {
                        navHostController.navigateUp()
                    },
                    onNoClick = {
                        navHostController.navigateUp()
                    },
                    onYesClick = {
                        if (navigateToSelectProcedure == true)
                            navHostController.navigate(AppNavGraph.SelectProcedureScreen.route){
                                popUpTo(AppNavGraph.Connection.route) {
                                    inclusive = true
                                }
                            }
                        else
                            navHostController.navigate(AppNavGraph.Connection.route){
                                popUpTo(AppNavGraph.Connection.route) {
                                    inclusive = true
                                }
                            }
                    }
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val chipData = backStackEntry.arguments?.getInt("chipTitle")

                ProcedureScreen(
                    chipTitle = chipData,
                    onNavigateUp = { navHostController.navigateUp() },
                    startProcedure = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.PreparingTheCabinScreen.route}/${chipTitle}")
                    }
                )
            }

            composable(
                route = "${AppNavGraph.PreparingTheCabinScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")

                PreparingTheCabinScreen(
                    chipTitleId = chipTitle,
                    navigateToSelectProcedureScreen = {
                        navHostController.navigate(AppNavGraph.SelectProcedureScreen.route)
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate(
                            "${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}"
                        )
                    }
                )
            }
        }
    )
}
