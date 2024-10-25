package com.zdravnica.app.screens.selectProcedure.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.selectProcedure.ui.ChooseProcedureGridLayout
import com.zdravnica.app.screens.selectProcedure.ui.IndicatorsStateInfo
import com.zdravnica.app.screens.selectProcedure.ui.SelectProcedureTopAppBar
import com.zdravnica.app.screens.selectProcedure.ui.TemperatureOrDurationAdjuster
import com.zdravnica.app.screens.selectProcedure.ui.TextWithSwitches
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureSideEffect
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureViewModel
import com.zdravnica.app.screens.statusScreen.StatusScreen
import com.zdravnica.bluetooth.data.COMMAND_FAN
import com.zdravnica.bluetooth.data.COMMAND_TEN
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.DELAY_DURATION_3000
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum
import com.zdravnica.uikit.components.snackbars.ui.SnackBarComponent
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.statusDetails.stateDataMap
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SelectProcedureTabletScreen(
    modifier: Modifier = Modifier,
    selectProcedureViewModel: SelectProcedureViewModel = koinViewModel(),
    isShowingSnackBar: Boolean = false,
    navigateToMenuScreen: () -> Unit,
    navigateToProcedureScreen: (Int) -> Unit,
    navigateToTheConnectionScreen: () -> Unit,
) {
    val viewState by selectProcedureViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val iconStates = viewState.iconStates
    var showFailedScreen by remember { mutableStateOf(false) }

    selectProcedureViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SelectProcedureSideEffect.OnNavigateToMenuScreen -> {
                selectProcedureViewModel.setSnackBarInvisible()
                navigateToMenuScreen.invoke()
            }

            is SelectProcedureSideEffect.OnProcedureCardClick -> {
                selectProcedureViewModel.setSnackBarInvisible()
                navigateToProcedureScreen.invoke(sideEffect.chipData.title)
            }

            is SelectProcedureSideEffect.OnBluetoothConnectionLost -> {
                showFailedScreen = true
            }
        }
    }

    LaunchedEffect(Unit) {
        selectProcedureViewModel.loadCommandStates()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                SelectProcedureTopAppBar(
                    temperature = viewState.temperature,
                    onRightIconClick = selectProcedureViewModel::navigateToMenuScreen,
                    iconStates = iconStates
                )
            },
            modifier = modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState
                ) {
                    item {
                        val isFanCommandFailed =
                            selectProcedureViewModel.isFailedSendingCommand(COMMAND_FAN)
                        val isTenCommandFailed =
                            selectProcedureViewModel.isFailedSendingCommand(COMMAND_TEN)

                        val statusInfoState = when {
                            iconStates[0] == IconState.DISABLED && isFanCommandFailed -> {
                                StatusInfoState.THERMOSTAT_ACTIVATION
                            }
                            iconStates[1] == IconState.DISABLED && isTenCommandFailed -> {
                                StatusInfoState.SENSOR_ERROR
                            }
                            else -> null
                        }

                        statusInfoState?.let { state ->
                            val statusInfoData = stateDataMap[state]
                            IndicatorsStateInfo(
                                indicatorInfo = stringResource(id = statusInfoData?.stateInfo ?: 0),
                                indicatorInstruction = stringResource(
                                    id = statusInfoData?.instruction ?: 0
                                )
                            )
                            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size12))
                        }
                        YTHorizontalDivider()

                        TextWithSwitches(
                            switchState = viewState.ikSwitchState,
                            onSwitchChange = {
                                selectProcedureViewModel.updateIkSwitchState(it)
                            }
                        )
                        YTHorizontalDivider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = ZdravnicaAppTheme.dimens.size16)
                        ) {
                            TemperatureOrDurationAdjuster(
                                isMinutes = false,
                                value = selectProcedureViewModel.temperature.value,
                                onValueChange = { selectProcedureViewModel.saveTemperature(it) },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size12))
                            TemperatureOrDurationAdjuster(
                                isMinutes = true,
                                value = selectProcedureViewModel.duration.value,
                                onValueChange = { selectProcedureViewModel.saveDuration(it) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        YTHorizontalDivider()

                        ChooseProcedureGridLayout(
                            bigChipsList = viewState.bigChipsList,
                            isTablet = true,
                            onCardClick = { chip ->
                                selectProcedureViewModel.onProcedureCardClick(chip)
                            }
                        )
                    }
                }
            }
        }
        if (viewState.isShowingSnackBar && isShowingSnackBar) {
            LaunchedEffect(Unit) {
                delay(DELAY_DURATION_3000)
                selectProcedureViewModel.setSnackBarInvisible()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
            ) {
                SnackBarComponent(
                    snackBarType = SnackBarTypeEnum.SNACK_BAR_SUCCESS,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }
    }

    if (showFailedScreen) {
        StatusScreen(
            state = StatusInfoState.CONNECTION_LOST,
            onCloseClick = { showFailedScreen = false },
            onSupportClick = {},
            onYesClick = {
                showFailedScreen = false
                navigateToTheConnectionScreen.invoke()
            },
        )
    }
}