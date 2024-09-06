package com.zdravnica.app.screens.selectProcedure.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureSideEffect
import com.zdravnica.app.screens.selectProcedure.viewModels.SelectProcedureViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ANIMATION_DURATION_3000
import com.zdravnica.uikit.COUNT_FOUR
import com.zdravnica.uikit.COUNT_ONE
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.buttons.models.IconButtonModel
import com.zdravnica.uikit.components.buttons.models.IconButtonType
import com.zdravnica.uikit.components.buttons.ui.IconButtonsComponent
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getChipDataList
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
import com.zdravnica.uikit.components.statusDetails.stateDataMap
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SelectProcedureScreen(
    modifier: Modifier = Modifier,
    selectProcedureViewModel: SelectProcedureViewModel = koinViewModel(),
    navigateToMenuScreen: () -> Unit,
    navigateToProcedureScreen: (Int) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val bigChipTypes = getChipDataList()
    val sampleChips = bigChipTypes.map { it.chipData }
    var ikSwitchState by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    var scrollToEnd by remember { mutableStateOf(false) }

    val iconStates = remember(ikSwitchState) {
        mutableStateListOf(
            IconState.ENABLED,//this data must come from bluetooth
            IconState.ENABLED,
            IconState.ENABLED,
            if (ikSwitchState) IconState.ENABLED else IconState.DISABLED
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

    LaunchedEffect(scrollToEnd) {
        if (scrollToEnd) {
            coroutineScope.launch {
                val targetIndex = sampleChips.size
                val itemHeight = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
                val totalHeight = itemHeight * targetIndex

                listState.animateScrollBy(
                    value = totalHeight.toFloat(),
                    animationSpec = tween(durationMillis = ANIMATION_DURATION_3000)
                )
                isButtonVisible = false
                scrollToEnd = false
            }
        }
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        if (listState.firstVisibleItemIndex == COUNT_ONE) {
            isButtonVisible = true
        }
        if (listState.firstVisibleItemIndex == COUNT_FOUR) {
            isButtonVisible = false
            scrollToEnd = false
        }
    }

    Scaffold(
        topBar = {
            SelectProcedureTopAppBar(
                temperature = selectProcedureViewModel.temperature.value,
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
                        switchState = ikSwitchState,
                        onSwitchChange = {
                            ikSwitchState = it
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
                                scrollToEnd = true
                            }
                        )
                    )
                }
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
