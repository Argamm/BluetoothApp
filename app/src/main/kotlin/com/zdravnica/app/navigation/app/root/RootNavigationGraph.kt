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
import com.zdravnica.app.screens.connecting_page.viewmodels.ConnectingPageViewModel
import com.zdravnica.app.screens.dialog.cancelProcedure.ui.CancelProcedureDialog
import com.zdravnica.app.screens.dialog.cancelProcedure.ui.teblet.CancelProcedureTabletDialog
import com.zdravnica.app.screens.dialog.ShowDevicesDialog
import com.zdravnica.app.screens.dialog.cancelProcedure.models.CancelProcedureDialogState
import com.zdravnica.app.screens.menuScreen.ui.MenuScreen
import com.zdravnica.app.screens.menuScreen.ui.tablet.MenuScreenTabletDialog
import com.zdravnica.app.screens.preparingTheCabin.ui.PreparingTheCabinScreen
import com.zdravnica.app.screens.procedure.ui.ProcedureScreen
import com.zdravnica.app.screens.procedure.ui.tablet.ProcedureTabletScreen
import com.zdravnica.app.screens.procedureProcess.ui.ProcedureProcessScreen
import com.zdravnica.app.screens.procedureProcess.ui.tablet.ProcedureProcessTabletScreen
import com.zdravnica.app.screens.selectProcedure.ui.SelectProcedureScreen
import com.zdravnica.app.screens.selectProcedure.ui.tablet.SelectProcedureTabletScreen
import com.zdravnica.app.screens.statusScreen.StatusScreen
import com.zdravnica.app.utils.isTablet
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.SLIDE_ANIMATION_DURATION_300
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val connectivityViewModel: ConnectingPageViewModel = koinViewModel()
    val isTablet = isTablet()
    val slideInFromRight = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
    )

    val slideOutToLeft = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
    )

    val slideInFromLeft = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
    )

    val slideOutToRight = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(SLIDE_ANIMATION_DURATION_300)
    )

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
                    },
                    navigateOnSelectProcedureScreen = { showSnackBar ->
                        if (isTablet) {
                            navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${showSnackBar}")
                        } else {
                            navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${showSnackBar}")
                        }
                    },
                    onNavigateUp = {
                        navHostController.navigateUp()
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

            composable(
                route = "${AppNavGraph.SelectProcedureScreen.route}/{showSnackBar}",
                arguments = listOf(navArgument("showSnackBar") {
                    type = NavType.BoolType
                })
            ) { backStackEntry ->
                val showSnackBar = backStackEntry.arguments?.getBoolean("showSnackBar")
                SelectProcedureScreen(
                    navigateToMenuScreen = {
                        navHostController.navigate(AppNavGraph.ManuScreen.route)
                    },
                    navigateToProcedureScreen = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.ProcedureScreen.route}/${chipTitle}")
                    },
                    isShowingSnackBar = showSnackBar ?: false,
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    }
                )
            }

            composable(
                route = AppNavGraph.ManuScreen.route,
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight },
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
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
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
                    state = CancelProcedureDialogState(
                        titleText = cancelDialog ?: "",
                        onClose = {
                            navHostController.navigateUp()
                        },
                        onNoClick = {
                            navHostController.navigateUp()
                        },
                        onYesClick = {
                            if (navigateToSelectProcedure == true) {
                                connectivityViewModel.turnOffAllWorkingProcesses()
                                navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}") {
                                    popUpTo(AppNavGraph.Connection.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            } else {
                                connectivityViewModel.sendStopCommand()
                                navHostController.navigate(AppNavGraph.Connection.route) {
                                    popUpTo(AppNavGraph.Connection.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType }),
                enterTransition = { slideInFromRight },
                exitTransition = { slideOutToLeft },
                popEnterTransition = { slideInFromLeft },
                popExitTransition = { slideOutToRight },
            ) { backStackEntry ->
                val chipData = backStackEntry.arguments?.getInt("chipTitle")

                ProcedureScreen(
                    chipTitle = chipData,
                    onNavigateUp = { navHostController.navigateUp() },
                    startProcedure = { chipTitle ->
                        connectivityViewModel.stopTurningOffProcesses()
                        navHostController.navigate("${AppNavGraph.PreparingTheCabinScreen.route}/${chipTitle}")
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
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
                        navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}")
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        if (isTablet) {
                            navHostController.navigate(
                                "${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}"
                            )
                        } else {
                            navHostController.navigate(
                                "${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}"
                            )
                        }
                    },
                    navigateToProcedureProcessScreen = {
                        if (isTablet) {
                            navHostController.navigate("${AppNavGraph.ProcedureProcessTabletScreen.route}/${chipTitle}")
                        } else {
                            navHostController.navigate("${AppNavGraph.ProcedureProcessScreen.route}/${chipTitle}")
                        }
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    }
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureProcessScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")

                ProcedureProcessScreen(
                    chipTitle = chipTitle,
                    navigateToMainScreen = {
                        navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}")
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate(
                            "${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}"
                        )
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    },
                    sendEndingCommands = {
                        connectivityViewModel.turnOffAllWorkingProcesses()
                    }
                )
            }

            composable(AppNavGraph.StatusScreen.route) {
                StatusScreen(
                    onCloseClick = {
                        navHostController.navigateUp()
                    },
                    onSupportClick = {

                    },
                    onYesClick = {
                        navHostController.navigateUp()
                    },
                )
            }

            //Tablets
            composable(
                route = "${AppNavGraph.SelectProcedureTabletScreen.route}/{showSnackBar}",
                arguments = listOf(navArgument("showSnackBar") {
                    type = NavType.BoolType
                })
            ) { backStackEntry ->
                val showSnackBar = backStackEntry.arguments?.getBoolean("showSnackBar")

                SelectProcedureTabletScreen(
                    navigateToMenuScreen = {
                        navHostController.navigate(AppNavGraph.ManuTabletScreen.route)
                    },
                    navigateToProcedureScreen = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.ProcedureTabletScreen.route}/${chipTitle}")
                    },
                    isShowingSnackBar = showSnackBar ?: false,
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    }
                )
            }
            composable(
                route = "${AppNavGraph.ProcedureTabletScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val chipData = backStackEntry.arguments?.getInt("chipTitle")

                ProcedureTabletScreen(
                    chipTitle = chipData,
                    onNavigateUp = { navHostController.navigateUp() },
                    startProcedure = { chipTitle ->
                        connectivityViewModel.stopTurningOffProcesses()
                        navHostController.navigate("${AppNavGraph.PreparingTheCabinScreen.route}/${chipTitle}")
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    }
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureProcessTabletScreen.route}/{chipTitle}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val chipData = backStackEntry.arguments?.getInt("chipTitle")
                ProcedureProcessTabletScreen(
                    chipTitle = chipData,
                    navigateToMainScreen = {
                        navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}")
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate(
                            "${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}"
                        )
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    },
                    sendEndingCommands = {
                        connectivityViewModel.turnOffAllWorkingProcesses()
                    }
                )
            }

            dialog(
                route = "${AppNavGraph.CancelProcedureTabletDialog.route}/{navigateToSelectProcedure}/{cancelDialog}",
                arguments = listOf(navArgument("navigateToSelectProcedure") {
                    type = NavType.BoolType
                }, navArgument("cancelDialog") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val navigateToSelectProcedure =
                    backStackEntry.arguments?.getBoolean("navigateToSelectProcedure")
                val cancelDialog = backStackEntry.arguments?.getString("cancelDialog")

                CancelProcedureTabletDialog(
                    state = CancelProcedureDialogState(
                        titleText = cancelDialog ?: "",
                        onClose = {
                            navHostController.navigateUp()
                        },
                        onNoClick = {
                            navHostController.navigateUp()
                        },
                        onYesClick = {
                            val route = if (navigateToSelectProcedure == true) {
                                "${AppNavGraph.SelectProcedureTabletScreen.route}/${false}"
                            } else {
                                AppNavGraph.Connection.route
                            }

                            connectivityViewModel.turnOffAllWorkingProcesses()
                            navHostController.navigate(route) {
                                popUpTo(AppNavGraph.Connection.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    ),
                )
            }

            dialog(
                route = AppNavGraph.ManuTabletScreen.route
            ) {
                MenuScreenTabletDialog(
                    onNavigateUp = {
                        navHostController.navigateUp()
                    },
                    navigateGToConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate("${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}")
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Connection.route)
                    }
                )
            }
        }
    )
}
