package com.zdravnica.app.screens.procedureProcess.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.zdravnica.app.screens.procedureProcess.viewModels.ProcedureProcessSideEffect
import com.zdravnica.app.screens.procedureProcess.viewModels.ProcedureProcessViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.models.BigButtonModel
import com.zdravnica.uikit.components.buttons.ui.BigButton
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.push.ProcedureStateInfo
import com.zdravnica.uikit.components.topAppBar.ProcedureProcessTopAppBar
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ProcedureProcessScreen(
    modifier: Modifier = Modifier,
    procedureProcessViewModel: ProcedureProcessViewModel = koinViewModel(),
    chipTitle: Int?,
    navigateToMainScreen: () -> Unit,
    navigateToCancelDialogPage: (Boolean, String) -> Unit,
) {
    val context = LocalContext.current
    val procedureProcessViewState by procedureProcessViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val cancelDialog = stringResource(id = R.string.preparing_the_cabin_cancel_procedure_question)
    var isTimerFinished by remember { mutableStateOf(false) }
    val viewState by procedureProcessViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val iconStates = viewState.iconStates

    procedureProcessViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProcedureProcessSideEffect.OnNavigateToMainScreen -> navigateToMainScreen.invoke()
            is ProcedureProcessSideEffect.OnNavigateToCancelDialogPage -> navigateToCancelDialogPage.invoke(
                true,
                cancelDialog
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                procedureProcessViewModel.onChangeCancelDialogPageVisibility(false)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(procedureProcessViewModel) {
        procedureProcessViewModel.observeSensorData()
    }

    LaunchedEffect(isTimerFinished) {
        if (isTimerFinished) {
            procedureProcessViewModel.updateTimerStatus(true)
            procedureProcessViewModel.sendEndingCommands()
        }
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
                backgroundColor = Color.White
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

                    if (!isTimerFinished) {
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
                                                    context
                                                )
                                            )
                                        }
                                }
                            }

                        )
                    } else {
                        ProcedureStateInfo(
                            firstText = stringResource(R.string.procedure_process_procedure_end),
                            secondText = stringResource(R.string.procedure_process_cooling)
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(
                            if (!isTimerFinished)
                                ZdravnicaAppTheme.dimens.size36
                            else
                                ZdravnicaAppTheme.dimens.size12
                        )
                    )

                    HealthMetricsDisplay(
                        temperatureValue = "${procedureProcessViewState.sensorTemperature}°C",
                        calorieValue = "${procedureProcessViewState.calorieValue} кк.",
                        pulseValue = "${procedureProcessViewState.pulse}/60",
                    )

                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size24))

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
                    Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size30))
                }
            }
        }
    )
}

@Preview
@Composable
private fun ProcedureProcessScreenPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureProcessScreen(
            chipTitle = null,
            navigateToMainScreen = {},
            navigateToCancelDialogPage = { _, _ -> })
    }
}