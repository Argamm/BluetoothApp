package com.zdravnica.app.screens.preparingTheCabin.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.preparingTheCabin.models.rememberProcedureProgressCircleState
import com.zdravnica.app.screens.preparingTheCabin.viewModels.PreparingTheCabinScreenSideEffect
import com.zdravnica.app.screens.preparingTheCabin.viewModels.PreparingTheCabinScreenViewModel
import com.zdravnica.app.screens.statusScreen.StatusScreen
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
import com.zdravnica.uikit.PINK_BACK_PROGRESS_FROM
import com.zdravnica.uikit.PINK_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.RED_BACK_PROGRESS_FROM
import com.zdravnica.uikit.RED_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.WHITE_BACK_PROGRESS
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.topAppBar.ProcedureProcessTopAppBar
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PreparingTheCabinScreen(
    modifier: Modifier = Modifier,
    preparingTheCabinScreenViewModel: PreparingTheCabinScreenViewModel = koinViewModel(),
    chipTitleId: Int? = null,
    navigateToSelectProcedureScreen: () -> Unit,
    navigateToCancelDialogPage: (Boolean, String) -> Unit,
    navigateToProcedureProcessScreen: () -> Unit,
    navigateToTheConnectionScreen: (StatusInfoState) -> Unit,
    stopAllProcessesExceptFanUntilCool: () -> Unit,
    runFanOnlyUntilThermostatStable: () -> Unit,
    onTemperatureSensorWarning: () -> Unit,
) {
    val preparingTheCabinScreenViewState by preparingTheCabinScreenViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val progress by preparingTheCabinScreenViewModel.progress.collectAsStateWithLifecycle()
    val iconStates = preparingTheCabinScreenViewState.iconStates
    val colors = ZdravnicaAppTheme.colors.baseAppColor
    val cancelDialog = stringResource(id = R.string.preparing_the_cabin_cancel_procedure_question)
    var showAnimationCircle by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    var showFailedScreen by remember { mutableStateOf(false) }
    var statusInfoState by remember { mutableStateOf<StatusInfoState?>(null) }

    val targetBackgroundColor = when {
        progress <= WHITE_BACK_PROGRESS -> Color.White
        progress in PINK_BACK_PROGRESS_FROM..PINK_BACK_PROGRESS_UNTIL -> colors.secondary900
        progress in RED_BACK_PROGRESS_FROM..RED_BACK_PROGRESS_UNTIL -> colors.error900
        progress == COUNT_TO_100 -> colors.success1000
        else -> Color.White
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_3000), label = ""
    )

    preparingTheCabinScreenViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PreparingTheCabinScreenSideEffect.OnNavigateToSelectProcedureScreen -> {
                preparingTheCabinScreenViewModel.stopObservingSensorData()
                navigateToSelectProcedureScreen.invoke()
            }

            is PreparingTheCabinScreenSideEffect.OnNavigateToCancelDialogPage -> {
                navigateToCancelDialogPage.invoke(true, cancelDialog)
            }

            is PreparingTheCabinScreenSideEffect.OnNavigateToFailedTenCommandScreen -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.SENSOR_ERROR
            }

            is PreparingTheCabinScreenSideEffect.OnNavigateToFailedTemperatureCommandScreen -> {
                stopAllProcessesExceptFanUntilCool.invoke()
                showFailedScreen = true
                statusInfoState = StatusInfoState.TEMPERATURE_EXCEEDED
            }

            is PreparingTheCabinScreenSideEffect.OnBluetoothConnectionLost -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.CONNECTION_LOST
            }

            is PreparingTheCabinScreenSideEffect.OnThermostatActivation -> {
                runFanOnlyUntilThermostatStable.invoke()
                statusInfoState = StatusInfoState.THERMOSTAT_ACTIVATION
                showFailedScreen = true
            }

            is PreparingTheCabinScreenSideEffect.OnTemperatureSensorWarning -> {
                onTemperatureSensorWarning.invoke()
                statusInfoState = StatusInfoState.SENSOR_ERROR
                showFailedScreen = true
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    preparingTheCabinScreenViewModel.observeSensorData()
                }

                Lifecycle.Event.ON_RESUME -> {
                    preparingTheCabinScreenViewModel.onChangeCancelDialogPageVisibility(false)
                }

                Lifecycle.Event.ON_STOP -> {
                    preparingTheCabinScreenViewModel.stopObservingSensorData()
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(Unit) {
        preparingTheCabinScreenViewModel.updateIconStates()
    }

    BackHandler {
        preparingTheCabinScreenViewModel.onChangeCancelDialogPageVisibility(true)
        navigateToCancelDialogPage.invoke(true, cancelDialog)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (preparingTheCabinScreenViewState.isDialogVisible) {
                    Modifier.blur(ZdravnicaAppTheme.dimens.size15)
                } else Modifier
            ),
        backgroundColor = backgroundColor,
        topBar = {
            ProcedureProcessTopAppBar(
                modifier = Modifier.background(backgroundColor),
                temperature = preparingTheCabinScreenViewState.sensorTemperature,
                iconStates = iconStates,
                backgroundColor = backgroundColor,
                isTemperatureDifferenceLarge = preparingTheCabinScreenViewState.isTemperatureDifferenceLarge,
            )
        },

        content = { paddingValues ->
            if (!showAnimationCircle) {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        chipTitleId?.let { stringResource(id = it) }?.let {
                            ProcedureInfo(
                                procedureName = it,
                                temperature = preparingTheCabinScreenViewModel.temperature.value,
                                minutes = preparingTheCabinScreenViewModel.duration.value / ONE_MINUTE_IN_SEC
                            )
                        }

                        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size56))

                        ProcedureProgressCircle(
                            state = rememberProcedureProgressCircleState(
                                progress = progress,
                                borderColor = backgroundColor
                            )
                        )
                        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size36))

                        ControlProcedure(
                            procedureState = if (progress < COUNT_TO_100) {
                                stringResource(R.string.preparing_the_cabin_waiting)
                            } else {
                                stringResource(R.string.preparing_the_cabin_ready)
                            },
                            progress = progress,
                            onCancelProcedure = {
                                preparingTheCabinScreenViewModel.onChangeCancelDialogPageVisibility(
                                    true
                                )
                                preparingTheCabinScreenViewModel.navigateToCancelDialogPage()
                            },
                            onProcedureComplete = {
                                showAnimationCircle = true
                            }
                        )

                        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size30))
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimationCircle(animationEnd = {
                        preparingTheCabinScreenViewModel.stopObservingSensorData()
                        navigateToProcedureProcessScreen.invoke()
                    })
                }
            }
        }
    )

    if (showFailedScreen) {
        statusInfoState?.let { statusState ->
            StatusScreen(
                state = statusState,
                onCloseClick = {
                    showFailedScreen = false
                    navigateToTheConnectionScreen.invoke(statusState)
                },
                onSupportClick = {},
                onYesClick = {
                    showFailedScreen = false
                    navigateToTheConnectionScreen.invoke(statusState)
                },
                onBackPressed = {
                    showFailedScreen = false
                    navigateToTheConnectionScreen.invoke(statusState)
                }
            )
        }
        preparingTheCabinScreenViewModel.stopObservingSensorData()
    }
}

@Preview
@Composable
private fun PreparingTheCabinScreenPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        PreparingTheCabinScreen(
            navigateToSelectProcedureScreen = {},
            navigateToCancelDialogPage = { _, _ -> },
            navigateToProcedureProcessScreen = {},
            navigateToTheConnectionScreen = {},
            stopAllProcessesExceptFanUntilCool = {},
            runFanOnlyUntilThermostatStable = {},
            onTemperatureSensorWarning = {},
        )
    }
}
