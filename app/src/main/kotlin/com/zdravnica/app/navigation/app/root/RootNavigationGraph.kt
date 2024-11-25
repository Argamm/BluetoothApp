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
import com.zdravnica.app.screens.dialog.ShowDevicesDialog
import com.zdravnica.app.screens.dialog.cancelProcedure.models.CancelProcedureDialogState
import com.zdravnica.app.screens.dialog.cancelProcedure.ui.CancelProcedureDialog
import com.zdravnica.app.screens.dialog.cancelProcedure.ui.teblet.CancelProcedureTabletDialog
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
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
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

    fun handleStatusInfoState(statusInfoState: StatusInfoState) {
        when (statusInfoState) {
            StatusInfoState.THERMOSTAT_ACTIVATION -> {
                if (isTablet) {
                    navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${StatusInfoState.THERMOSTAT_ACTIVATION}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${StatusInfoState.THERMOSTAT_ACTIVATION}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            StatusInfoState.CONNECTION_LOST -> {
                navHostController.navigate(AppNavGraph.Root.route) {
                    popUpTo(AppNavGraph.Connection.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }

            StatusInfoState.SENSOR_ERROR -> {
                if (isTablet) {
                    navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${StatusInfoState.SENSOR_ERROR}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${StatusInfoState.SENSOR_ERROR}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            StatusInfoState.TEMPERATURE_EXCEEDED -> {
                if (isTablet) {
                    navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${StatusInfoState.TEMPERATURE_EXCEEDED}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                } else {
                    navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${StatusInfoState.TEMPERATURE_EXCEEDED}") {
                        popUpTo(AppNavGraph.Root.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            StatusInfoState.NULL -> {

            }
        }
    }

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
//                        navHostController.navigate("${AppNavGraph.ProcedureProcessScreen.route}/${aaa}/${true}")
                        navHostController.navigate(AppNavGraph.BluetoothDevicesDialog.route)
                    },
                    navigateOnSelectProcedureScreen = { showSnackBar ->
                        if (isTablet) {
                            navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${showSnackBar}/${"a"}") {
                                popUpTo(AppNavGraph.Root.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        } else {
                            navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${showSnackBar}/${"a"}") {
                                popUpTo(AppNavGraph.Root.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
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
                route = "${AppNavGraph.SelectProcedureScreen.route}/{showSnackBar}/{statusInfoState}",
                arguments = listOf(navArgument("showSnackBar") {
                    type = NavType.BoolType
                }, navArgument("statusInfoState") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val showSnackBar = backStackEntry.arguments?.getBoolean("showSnackBar")
                val statusInfoState = backStackEntry.arguments?.getString("statusInfoState")
                SelectProcedureScreen(
                    statusInfoStateString = statusInfoState ?: "",
                    navigateToMenuScreen = {
                        navHostController.navigate(AppNavGraph.ManuScreen.route)
                    },
                    navigateToProcedureScreen = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.ProcedureScreen.route}/${chipTitle}")
                    },
                    isShowingSnackBar = showSnackBar ?: false,
                    handleStatusInfoState = { statusState ->
                        handleStatusInfoState(statusState)
                    },
                    navigateOnConnectionScreen = {
                        connectivityViewModel.sendStopCommand()
                        connectivityViewModel.stopConnectionObserving()

                        navHostController.navigate(AppNavGraph.Root.route) {
                            popUpTo(AppNavGraph.Root.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
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
                        navHostController.navigate("${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${1}")
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Root.route) {
                            popUpTo(AppNavGraph.Connection.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            dialog(
                route = "${AppNavGraph.CancelProcedureDialog.route}/{navigateToSelectProcedure}/{cancelDialog}/{chipTitle}",
                arguments = listOf(navArgument("navigateToSelectProcedure") {
                    type = NavType.BoolType
                }, navArgument("cancelDialog") {
                    type = NavType.StringType
                }, navArgument("chipTitle") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val navigateToSelectProcedure =
                    backStackEntry.arguments?.getBoolean("navigateToSelectProcedure")
                val cancelDialog = backStackEntry.arguments?.getString("cancelDialog")
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")

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
                                if (chipTitle != 1) {
                                    navHostController.navigate("${AppNavGraph.ProcedureProcessScreen.route}/${chipTitle}/${true}") {
                                        popUpTo("${AppNavGraph.SelectProcedureScreen.route}/${false}/${"a"}") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                } else {
                                    connectivityViewModel.turnOffAllExpectFun()
                                    connectivityViewModel.turnOffAllWorkingProcesses()
                                    navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${"a"}") {
                                        popUpTo("${AppNavGraph.SelectProcedureScreen.route}/${false}/${"a"}") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            } else {
                                connectivityViewModel.stopConnectionObserving()
                                connectivityViewModel.sendStopCommand()

                                navHostController.navigate(AppNavGraph.Root.route) {
                                    popUpTo(AppNavGraph.Root.route) {
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
                    navigateToTheConnectionScreen = { statusInfoState ->
                        handleStatusInfoState(statusInfoState)
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
                        navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${"a"}")
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        if (isTablet) {
                            navHostController.navigate(
                                "${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${chipTitle}"
                            )
                        } else {
                            navHostController.navigate(
                                "${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${chipTitle}"
                            )
                        }
                    },
                    navigateToProcedureProcessScreen = {
                        if (isTablet) {
                            navHostController.navigate("${AppNavGraph.ProcedureProcessTabletScreen.route}/${chipTitle}/${false}")
                        } else {
                            navHostController.navigate("${AppNavGraph.ProcedureProcessScreen.route}/${chipTitle}/${false}")
                        }
                    },
                    navigateToTheConnectionScreen = { statusInfoState ->
                        handleStatusInfoState(statusInfoState)
                    },
                    stopAllProcessesExceptFanUntilCool = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.stopAllProcessesExceptFanUntilCool()
                    },
                    runFanOnlyUntilThermostatStable = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.runFanOnlyUntilThermostatStable()
                    },
                    onTemperatureSensorWarning = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.turnOffAllWorkingProcesses()
                    }
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureProcessScreen.route}/{chipTitle}/{isCanceledProcedure}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType },
                    navArgument("isCanceledProcedure") {
                        type = NavType.BoolType
                    })
            ) { backStackEntry ->
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")
                val isCanceledProcedure =
                    backStackEntry.arguments?.getBoolean("isCanceledProcedure")

                ProcedureProcessScreen(
                    chipTitle = chipTitle,
                    timerFinished = isCanceledProcedure ?: false,
                    navigateToMainScreen = {
                        navHostController.navigate("${AppNavGraph.SelectProcedureScreen.route}/${false}/${"a"}") {
                            popUpTo(AppNavGraph.Connection.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate(
                            "${AppNavGraph.CancelProcedureDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${chipTitle}"
                        )
                    },
                    navigateToTheConnectionScreen = { statusInfoState ->
                        handleStatusInfoState(statusInfoState)
                    },
                    sendEndingCommands = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.turnOffAllWorkingProcesses()
                    },
                    stopAllProcessesExceptFanUntilCool = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.stopAllProcessesExceptFanUntilCool()
                    },
                    runFanOnlyUntilThermostatStable = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.runFanOnlyUntilThermostatStable()
                    },
                    onTemperatureSensorWarning = {
                        connectivityViewModel.turnOffAllExpectFun()
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
                    onBackPressed = {
                        navHostController.navigateUp()
                    }
                )
            }

            //Tablets
            composable(
                route = "${AppNavGraph.SelectProcedureTabletScreen.route}/{showSnackBar}/{statusInfoState}",
                arguments = listOf(navArgument("showSnackBar") {
                    type = NavType.BoolType
                }, navArgument("statusInfoState") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val showSnackBar = backStackEntry.arguments?.getBoolean("showSnackBar")
                val statusInfoState = backStackEntry.arguments?.getString("statusInfoState")

                SelectProcedureTabletScreen(
                    navigateToMenuScreen = {
                        navHostController.navigate(AppNavGraph.ManuTabletScreen.route)
                    },
                    navigateToProcedureScreen = { chipTitle ->
                        navHostController.navigate("${AppNavGraph.ProcedureTabletScreen.route}/${chipTitle}")
                    },
                    isShowingSnackBar = showSnackBar ?: false,
                    statusInfoStateString = statusInfoState ?: "",
                    navigateToTheConnectionScreen = { statusState ->
                        handleStatusInfoState(statusState)
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
                        navHostController.navigate(AppNavGraph.Root.route) {
                            popUpTo(AppNavGraph.Connection.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = "${AppNavGraph.ProcedureProcessTabletScreen.route}/{chipTitle}/{isCanceledProcedure}",
                arguments = listOf(navArgument("chipTitle") { type = NavType.IntType },
                    navArgument("isCanceledProcedure") {
                        type = NavType.BoolType
                    })
            ) { backStackEntry ->
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")
                val isCanceledProcedure =
                    backStackEntry.arguments?.getBoolean("isCanceledProcedure")

                ProcedureProcessTabletScreen(
                    chipTitle = chipTitle,
                    timerFinished = isCanceledProcedure ?: false,
                    navigateToMainScreen = {
                        navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${"a"}")
                    },
                    navigateToCancelDialogPage = { navigateToSelectProcedure, cancelDialog ->
                        navHostController.navigate(
                            "${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${chipTitle}"
                        )
                    },
                    navigateToTheConnectionScreen = { statusInfoState ->
                        handleStatusInfoState(statusInfoState)
                    },
                    sendEndingCommands = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.turnOffAllWorkingProcesses()
                    },
                    stopAllProcessesExceptFanUntilCool = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.stopAllProcessesExceptFanUntilCool()
                    },
                    runFanOnlyUntilThermostatStable = {
                        connectivityViewModel.turnOffAllExpectFun()
                        connectivityViewModel.runFanOnlyUntilThermostatStable()
                    }
                )
            }

            dialog(
                route = "${AppNavGraph.CancelProcedureTabletDialog.route}/{navigateToSelectProcedure}/{cancelDialog}/{chipTitle}",
                arguments = listOf(navArgument("navigateToSelectProcedure") {
                    type = NavType.BoolType
                }, navArgument("cancelDialog") {
                    type = NavType.StringType
                }, navArgument("chipTitle") { type = NavType.IntType })
            ) { backStackEntry ->
                val navigateToSelectProcedure =
                    backStackEntry.arguments?.getBoolean("navigateToSelectProcedure")
                val cancelDialog = backStackEntry.arguments?.getString("cancelDialog")
                val chipTitle = backStackEntry.arguments?.getInt("chipTitle")

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
                            if (navigateToSelectProcedure == true) {
                                if (chipTitle != 1) {
                                    navHostController.navigate("${AppNavGraph.ProcedureProcessTabletScreen.route}/${chipTitle}/${true}")
                                } else {
                                    connectivityViewModel.turnOffAllExpectFun()
                                    connectivityViewModel.turnOffAllWorkingProcesses()
                                    navHostController.navigate("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${"a"}") {
                                        popUpTo("${AppNavGraph.SelectProcedureTabletScreen.route}/${false}/${"a"}") {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            } else {
                                connectivityViewModel.sendStopCommand()
                                connectivityViewModel.stopConnectionObserving()

                                navHostController.navigate(AppNavGraph.Root.route) {
                                    popUpTo(AppNavGraph.Connection.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
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
                        navHostController.navigate("${AppNavGraph.CancelProcedureTabletDialog.route}/${navigateToSelectProcedure}/${cancelDialog}/${1}")
                    },
                    navigateToTheConnectionScreen = {
                        navHostController.navigate(AppNavGraph.Root.route) {
                            popUpTo(AppNavGraph.Connection.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    )
}
