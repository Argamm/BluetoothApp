package com.zdravnica.app.screens.selectProcedure.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureSideEffect
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_FOUR
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.buttons.models.IconButtonModel
import com.zdravnica.uikit.components.buttons.models.IconButtonType
import com.zdravnica.uikit.components.buttons.ui.IconButtonsComponent
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getChipDataList
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum
import com.zdravnica.uikit.components.snackbars.ui.SnackBarComponent
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.statusDetails.stateDataMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SelectProcedureScreen(
    modifier: Modifier = Modifier,
    selectProcedureViewModel: SelectProcedureViewModel = koinViewModel(),
    navigateToMenuScreen: () -> Unit,
    navigateToProcedureScreen: (Int) -> Unit,
    isShowingSnackBar: Boolean = false
) {
    val viewState by selectProcedureViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isButtonVisible by remember { derivedStateOf { listState.firstVisibleItemIndex <= COUNT_THREE } }
    val sampleChips = getChipDataList().map { it.chipData }
    var currentSnackBarModel by remember { mutableStateOf<Boolean?>(null) }
    val iconStates = remember(viewState.ikSwitchState) {
        mutableStateListOf(
            IconState.ENABLED,//TODO this data must come from bluetooth, will moved to state soon
            IconState.ENABLED,
            IconState.ENABLED,
            if (viewState.ikSwitchState) IconState.ENABLED else IconState.DISABLED
        )
    }
    selectProcedureViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SelectProcedureSideEffect.OnNavigateToMenuScreen -> navigateToMenuScreen.invoke()
            is SelectProcedureSideEffect.OnProcedureCardClick -> {
                navigateToProcedureScreen.invoke(sideEffect.chipData.title)
            }
        }
    }

    LaunchedEffect(isShowingSnackBar) {
        if (isShowingSnackBar) {
            currentSnackBarModel = true
        }
    }

    LaunchedEffect(selectProcedureViewModel) {
        selectProcedureViewModel.observeSensorData()
    }

    LaunchedEffect(viewState.scrollToEnd) {
        if (viewState.scrollToEnd) {
            coroutineScope.launch {
                val targetIndex = sampleChips.size
                val itemHeight = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
                val totalHeight = itemHeight * targetIndex

                listState.animateScrollBy(
                    value = totalHeight.toFloat(),
                    animationSpec = tween(durationMillis = ANIMATION_DURATION_3000)
                )
                selectProcedureViewModel.updateIsButtonVisible(false)
                selectProcedureViewModel.updateScrollToEnd(false)
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (firstVisibleItemIndex, _) ->
                if (firstVisibleItemIndex == COUNT_FOUR) {
                    selectProcedureViewModel.updateIsButtonVisible(false)
                    selectProcedureViewModel.updateScrollToEnd(false)
                }
            }
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
                        val statusInfoState = when (IconState.DISABLED) {
                            iconStates[0] -> StatusInfoState.THERMOSTAT_ACTIVATION
                            iconStates[1] -> StatusInfoState.SENSOR_ERROR
                            else -> null
                        }

                        statusInfoState?.let { state ->
                            val statusInfoData = stateDataMap[state]
                            IndicatorsStateInf(
                                indicatorInfo = stringResource(id = statusInfoData?.stateInfo ?: 0),
                                indicatorInstruction = stringResource(
                                    id = statusInfoData?.instruction ?: 0
                                )
                            )
                            Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size12))
                        }
                    }
                    item {
                        TextWithSwitches(
                            switchState = viewState.ikSwitchState,
                            onSwitchChange = {
                                selectProcedureViewModel.updateIkSwitchState(it)
                                selectProcedureViewModel.switchIk()
                            }
                        )
                        YTHorizontalDivider()
                    }
                    item {
                        TemperatureOrDurationAdjuster(
                            isMinutes = false,
                            value = selectProcedureViewModel.temperature.value,
                            onValueChange = { selectProcedureViewModel.saveTemperature(it) }
                        )
                        YTHorizontalDivider()
                    }
                    item {
                        TemperatureOrDurationAdjuster(
                            isMinutes = true,
                            value = selectProcedureViewModel.duration.value,
                            onValueChange = { selectProcedureViewModel.saveDuration(it) }
                        )
                        YTHorizontalDivider()
                    }
                    item {
                        ChooseProcedureGridLayout(
                            bigChipsList = sampleChips,
                            onCardClick = { chip ->
                                selectProcedureViewModel.onProcedureCardClick(chip)
                            }
                        )
                    }
                }

                if (isButtonVisible) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(ZdravnicaAppTheme.dimens.size16)
                    ) {
                        IconButtonsComponent(
                            iconButtonModel = IconButtonModel(
                                isEnabled = true,
                                type = IconButtonType.PRIMARY,
                                onClick = {
                                    selectProcedureViewModel.updateScrollToEnd(true)
                                }
                            )
                        )
                    }
                }
            }
        }
        currentSnackBarModel?.let { snackBarModel ->
            LaunchedEffect(snackBarModel) {
                delay(3000)
                currentSnackBarModel = null
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
}

@Preview
@Composable
fun PreviewSelectProcedureScreen() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        SelectProcedureScreen(navigateToMenuScreen = {}, navigateToProcedureScreen = {})
    }
}
