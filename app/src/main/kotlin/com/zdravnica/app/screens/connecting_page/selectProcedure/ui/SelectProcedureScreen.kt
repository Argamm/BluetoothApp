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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.screens.connecting_page.selectProcedure.models.SelectProcedureViewState
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.buttons.models.IconButtonModel
import com.zdravnica.uikit.components.buttons.models.IconButtonType
import com.zdravnica.uikit.components.buttons.ui.IconButtonsComponent
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.components.dividers.YTHorizontalDivider
import com.zdravnica.uikit.resources.R
import kotlinx.coroutines.launch

@Composable
fun SelectProcedureScreen(
    viewState: SelectProcedureViewState,
    modifier: Modifier = Modifier,
) {
    var fourSwitchState by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    var scrollToEnd by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val sampleChips = listOf(
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_skin),
            description = stringResource(R.string.select_product_skin_description),
            iconRes = R.mipmap.ic_skin
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_lungs),
            description = stringResource(R.string.select_product_lungs_description),
            iconRes = R.mipmap.ic_lungs
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_heart),
            description = stringResource(R.string.select_product_heart_description),
            iconRes = R.mipmap.ic_heart
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_pancreas),
            description = stringResource(R.string.select_product_pancreas_description),
            iconRes = R.mipmap.ic_pancreas
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_intestine),
            description = stringResource(R.string.select_product_intestine_description),
            iconRes = R.mipmap.ic_intestine
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_uterus),
            description = stringResource(R.string.select_product_uterus_description),
            iconRes = R.mipmap.ic_uterus
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_brain),
            description = stringResource(R.string.select_product_brain_description),
            iconRes = R.mipmap.ic_brain
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_stomach),
            description = stringResource(R.string.select_product_stomach_description),
            iconRes = R.mipmap.ic_stomach
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_knee_joint),
            description = stringResource(R.string.select_product_knee_joint_description),
            iconRes = R.mipmap.ic_knee_joint
        ),
        BigChipsStateModel(
            isEnabled = false,
            title = stringResource(R.string.select_product_nose),
            description = stringResource(R.string.select_product_nose_description),
            iconRes = R.mipmap.ic_nose
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_custom_mix),
            description = stringResource(R.string.select_product_custom_mix_description),
            iconRes = null
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = stringResource(R.string.select_product_without_balm),
            description = stringResource(R.string.select_product_without_balm_description),
            iconRes = null
        )
    )

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
            SelectProductTopAppBar(
                temperature = viewState.temperature,
                fourSwitchState,
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
                state = listState // Attach the state to the LazyColumn
            ) {
                item {
                    TextWithSwitches(
                        switchState = fourSwitchState,
                        onSwitchChange = {
                            fourSwitchState = it
                        }
                    )
                    YTHorizontalDivider()
                    ValueAdjuster(isMinutes = false)
                    YTHorizontalDivider()
                    ValueAdjuster(isMinutes = true)
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
        SelectProcedureScreen(SelectProcedureViewState(false, 50))
    }
}
