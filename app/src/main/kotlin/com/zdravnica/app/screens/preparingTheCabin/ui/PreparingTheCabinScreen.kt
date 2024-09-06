package com.zdravnica.app.screens.preparingTheCabin.ui

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
import androidx.compose.runtime.mutableIntStateOf
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
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.COUNT_TO_100
import com.zdravnica.uikit.DELAY_1000_ML
import com.zdravnica.uikit.ONE_MINUTE_IN_SEC
import com.zdravnica.uikit.PINK_BACK_PROGRESS_FROM
import com.zdravnica.uikit.PINK_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.RED_BACK_PROGRESS_FROM
import com.zdravnica.uikit.RED_BACK_PROGRESS_UNTIL
import com.zdravnica.uikit.WHITE_BACK_PROGRESS
import com.zdravnica.uikit.components.topAppBar.ProcedureProcessTopAppBar
import com.zdravnica.uikit.resources.R
import com.zdravnica.uikit.utils.calculateProgress
import kotlinx.coroutines.delay
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
) {
    val preparingTheCabinScreenViewState by preparingTheCabinScreenViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val colors = ZdravnicaAppTheme.colors.baseAppColor
    val cancelDialog = stringResource(id = R.string.preparing_the_cabin_cancel_procedure_question)
    var showAnimationCircle by remember { mutableStateOf(false) }
    var currentTemperature by remember { mutableIntStateOf(0) }
    var progress by remember {
        mutableIntStateOf(
            calculateProgress(
                currentTemperature,
                preparingTheCabinScreenViewModel.temperature.value
            )
        )
    }

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
            is PreparingTheCabinScreenSideEffect.OnNavigateToSelectProcedureScreen ->
                navigateToSelectProcedureScreen.invoke()

            is PreparingTheCabinScreenSideEffect.OnNavigateToCancelDialogPage ->
                navigateToCancelDialogPage.invoke(
                    true, cancelDialog
                )
        }
    }

    /*
    * do we need loop here? and calculating the progress happening in the view side,
    * lets move it to some use case, you can use Flow for that and emit "state" whenev
    * er the progress proceed some level to change the color.
    * It is not goot practice to do all that stuff in launch effect in UI layer
    *
    * TODO this logic will be removed or replaced when from Bluetooth will receive data
    */
    LaunchedEffect(currentTemperature) {
        while (currentTemperature < preparingTheCabinScreenViewModel.temperature.value) {
            delay(DELAY_1000_ML)
            currentTemperature += COUNT_ONE
            progress = calculateProgress(
                currentTemperature,
                preparingTheCabinScreenViewModel.temperature.value
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                preparingTheCabinScreenViewModel.onChangeCancelDialogPageVisibility(false)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (preparingTheCabinScreenViewState.uiModel.isDialogVisible) {
                    Modifier.blur(ZdravnicaAppTheme.dimens.size15)
                } else Modifier
            ),
        backgroundColor = backgroundColor,
        topBar = {
            ProcedureProcessTopAppBar(
                modifier = Modifier.background(backgroundColor),
                temperature = currentTemperature,
                fourSwitchState = false,
                backgroundColor = backgroundColor
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
                            procedureState = if (progress < COUNT_TO_100)
                                stringResource(R.string.preparing_the_cabin_waiting)
                            else
                                stringResource(R.string.preparing_the_cabin_ready),
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
                        navigateToProcedureProcessScreen.invoke()
                    })
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreparingTheCabinScreenPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        PreparingTheCabinScreen(
            navigateToSelectProcedureScreen = {},
            navigateToCancelDialogPage = { _, _ -> },
            navigateToProcedureProcessScreen = {}
        )
    }
}
