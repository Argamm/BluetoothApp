package com.zdravnica.app.screens.connecting_page.selectProcedure.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.screens.connecting_page.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.models.IconButtonModel
import com.zdravnica.uikit.components.buttons.models.IconButtonType
import com.zdravnica.uikit.components.buttons.ui.IconButtonsComponent
import com.zdravnica.uikit.components.chips.models.getChipDataList
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import kotlinx.coroutines.launch

@Composable
fun SelectProcedureScreen(
    modifier: Modifier = Modifier,
    viewState: SelectProcedureViewState,
) {
    var fourSwitchState by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    var scrollToEnd by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val bigChipTypes = getChipDataList()
    val sampleChips = bigChipTypes.map { it.chipData }

    LaunchedEffect(scrollToEnd) {
        if (scrollToEnd) {
            coroutineScope.launch {
                val targetIndex = sampleChips.size
                val itemHeight = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
                val totalHeight = itemHeight * targetIndex

                listState.animateScrollBy(
                    value = totalHeight.toFloat(),
                    animationSpec = tween(durationMillis = 3000)
                )
                isButtonVisible = false
                scrollToEnd = false
            }
        }
    }

    Scaffold(
        topBar = {
            SelectProcedureTopAppBar(
                temperature = viewState.temperature,
                fourSwitchState = fourSwitchState,
                onRightIconClick = {
                    //The right item click
                })
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
                    TextWithSwitches(
                        switchState = fourSwitchState,
                        onSwitchChange = {
                            fourSwitchState = it
                        }
                    )
                    YTHorizontalDivider()
                    TemperatureOrDurationAdjuster(isMinutes = false)
                    YTHorizontalDivider()
                    TemperatureOrDurationAdjuster(isMinutes = true)
                    YTHorizontalDivider()
                    ChooseProcedureGridLayout(
                        bigChipsList = sampleChips,
                        onCardClick = { chip ->
                            //navigate to clicked chip information page
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
                                isButtonVisible = false
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
        SelectProcedureScreen(modifier = Modifier.fillMaxSize(), viewState = SelectProcedureViewState(false, 50))
    }
}
