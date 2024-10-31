package com.zdravnica.app.screens.procedureProcess.ui.tablet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.procedureProcess.ui.HealthMetricsDisplay
import com.zdravnica.app.screens.procedureProcess.ui.TimerProcess
import com.zdravnica.app.screens.procedureProcess.viewModels.ProcedureProcessSideEffect
import com.zdravnica.app.screens.procedureProcess.viewModels.ProcedureProcessViewModel
import com.zdravnica.app.screens.statusScreen.StatusScreen
import com.zdravnica.app.utils.isLandscape
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.push.ProcedureStateInfo
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.topAppBar.ProcedureProcessTopAppBar
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ProcedureProcessTabletScreen(
    modifier: Modifier = Modifier,
    procedureProcessViewModel: ProcedureProcessViewModel = koinViewModel(),
    chipTitle: Int?,
    navigateToMainScreen: () -> Unit,
    navigateToCancelDialogPage: (Boolean, String) -> Unit,
    navigateToTheConnectionScreen: () -> Unit,
    sendEndingCommands: () -> Unit,
) {
    val ctx = LocalContext.current
    val procedureProcessViewState by procedureProcessViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val cancelDialog = stringResource(id = R.string.preparing_the_cabin_cancel_procedure_question)
    var isTimerFinished by remember { mutableStateOf(false) }
    val viewState by procedureProcessViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val iconStates = viewState.iconStates
    var showFailedScreen by remember { mutableStateOf(false) }
    var statusInfoState by remember { mutableStateOf(StatusInfoState.THERMOSTAT_ACTIVATION) }
    val lifecycleOwner = LocalLifecycleOwner.current

    procedureProcessViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProcedureProcessSideEffect.OnNavigateToMainScreen -> navigateToMainScreen.invoke()
            is ProcedureProcessSideEffect.OnNavigateToCancelDialogPage -> navigateToCancelDialogPage.invoke(
                true,
                cancelDialog
            )

            is ProcedureProcessSideEffect.OnNavigateToFailedTenCommandScreen -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.SENSOR_ERROR
            }

            is ProcedureProcessSideEffect.OnNavigateToFailedTemperatureCommandScreen -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.TEMPERATURE_EXCEEDED
            }

            is ProcedureProcessSideEffect.OnNavigateToFailedFanCommandScreen -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.THERMOSTAT_ACTIVATION
            }

            is ProcedureProcessSideEffect.OnBluetoothConnectionLost -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.CONNECTION_LOST
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    procedureProcessViewModel.onChangeCancelDialogPageVisibility(false)
                    procedureProcessViewModel.observeSensorData()
                }
                Lifecycle.Event.ON_STOP -> {
                    procedureProcessViewModel.stopObservingSensorData()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(isTimerFinished) {
        if (isTimerFinished) {
            procedureProcessViewModel.updateTimerStatus(true)
            sendEndingCommands.invoke()
        }
    }

    LaunchedEffect(Unit) {
        procedureProcessViewModel.updateIconStates()
    }

    LaunchedEffect(procedureProcessViewModel) {
        procedureProcessViewModel.observeSensorData()
    }

    BackHandler {
        procedureProcessViewModel.onChangeCancelDialogPageVisibility(true)
        navigateToCancelDialogPage.invoke(true, cancelDialog)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (procedureProcessViewState.isDialogVisible) {
                    Modifier.blur(ZdravnicaAppTheme.dimens.size15)
                } else Modifier
            ),
        backgroundColor = Color.White,
        topBar = {
            ProcedureProcessTopAppBar(
                temperature = procedureProcessViewState.sensorTemperature,
                iconStates = iconStates,
                backgroundColor = Color.White,
                isTemperatureDifferenceLarge = procedureProcessViewState.isTemperatureDifferenceLarge,
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size38))

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = ZdravnicaAppTheme.dimens.size86),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    start = if (isLandscape())
                                        ZdravnicaAppTheme.dimens.size152
                                    else
                                        ZdravnicaAppTheme.dimens.size50
                                )
                        ) {
                            if (!isTimerFinished) {
                                Column {
                                    if (procedureProcessViewModel.balmFeeding.value) {
                                        ProcedureStateInfo(
                                            firstText = stringResource(R.string.procedure_process_balm_supply),
                                        )
                                        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))
                                    }

                                    TimerProcess(totalSeconds = procedureProcessViewModel.duration.value,
                                        onTimerFinish = {
                                            isTimerFinished = true
                                        },
                                        onNineMinutesLeft = {
                                            procedureProcessViewModel.turnOnKMPR()
                                        },
                                        onTurnOffCommand = {
                                            procedureProcessViewModel.turnOffKMPR()
                                        },
                                        onFourMinutesLeft = {
                                            procedureProcessViewModel.turnOnKMPR()
                                        },
                                        onTurnOffCommandAfterFour = {
                                            procedureProcessViewModel.turnOffKMPR()
                                        },
                                        onMinutesLeftWithCredits = {
                                            if (chipTitle != null) {
                                                BigChipType.getBalmInfoByTitle(chipTitle)
                                                    ?.let {
                                                        procedureProcessViewModel.startSTVCommandSequence(
                                                            it, BigChipType.getAllBalmNames(
                                                                ctx
                                                            )
                                                        )
                                                    }
                                            }
                                        }
                                    )
                                }
                            } else {
                                ProcedureStateInfo(
                                    firstText = stringResource(R.string.procedure_process_procedure_end),
                                    secondText = stringResource(R.string.procedure_process_cooling)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    end = if (isLandscape())
                                        ZdravnicaAppTheme.dimens.size143
                                    else
                                        ZdravnicaAppTheme.dimens.size50
                                )
                        ) {

                            HealthMetricsDisplay(
                                temperatureValue = "${procedureProcessViewState.skinTemperature}°C",
                                calorieValue = "${procedureProcessViewState.calorieValue} кк.",
                                pulseValue = "${procedureProcessViewState.pulse}/60",
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size36))

                    if (!isTimerFinished) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = ZdravnicaAppTheme.dimens.size24)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    procedureProcessViewModel.onChangeCancelDialogPageVisibility(
                                        true
                                    )
                                    procedureProcessViewModel.navigateToCancelDialogPage()
                                },
                            text = stringResource(R.string.preparing_the_cabin_cancel_procedure),
                            style = ZdravnicaAppTheme.typography.bodyMediumSemi,
                            color = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        BigButton(
                            modifier = Modifier
                                .wrapContentSize(),
                            bigButtonModel = BigButtonModel(
                                buttonText = stringResource(R.string.procedure_process_to_main_screen),
                                textModifier = Modifier
                                    .wrapContentSize()
                                    .padding(horizontal = ZdravnicaAppTheme.dimens.size19),
                                isEnabled = true,
                                onClick = procedureProcessViewModel::navigateToMainScreen
                            ),
                            bigBtnStateColors = ZdravnicaAppTheme.colors.bigButtonStateColor.copy(
                                borderStrokeColor = ZdravnicaAppTheme.colors.baseAppColor.primary500,
                                enabledBackground = ZdravnicaAppTheme.colors.baseAppColor.primary500
                            )
                        )
                    }
                }
            }
        }
    )

    if (showFailedScreen) {
        StatusScreen(
            state = statusInfoState,
            onCloseClick = { showFailedScreen = false },
            onSupportClick = {},
            onYesClick = {
                showFailedScreen = false
                if (statusInfoState == StatusInfoState.CONNECTION_LOST) {
                    navigateToTheConnectionScreen.invoke()
                }
            },
        )
    }
}

@Preview
@Composable
private fun ProcedureProcessScreenPrevT() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureProcessTabletScreen(
            chipTitle = null,
            navigateToMainScreen = {},
            navigateToCancelDialogPage = { _, _ -> },
            navigateToTheConnectionScreen = {},
            sendEndingCommands = {},
        )
    }
}