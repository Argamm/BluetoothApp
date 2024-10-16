package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.zdravnica.app.screens.procedure.ui.ChooseProcedureChipGroup
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenSideEffect
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getAllChipTitles
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getBalmInfoByTitle
import com.zdravnica.uikit.components.topAppBar.SimpleTopAppBar
import com.zdravnica.uikit.resources.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ProcedureTabletScreen(
    modifier: Modifier = Modifier,
    procedureScreenViewModel: ProcedureScreenViewModel = koinViewModel(),
    chipTitle: Int? = null,
    onNavigateUp: (() -> Unit)? = null,
    startProcedure: (Int) -> Unit,
) {
    val allChipTitles = getAllChipTitles()
    var selectedOption: Int? by remember { mutableStateOf(chipTitle) }
    var chipData by remember {
        mutableStateOf(selectedOption?.let { selectedOption ->
            BigChipType.getChipDataByTitle(
                selectedOption
            )
        })
    }
    var balmInfo by remember { mutableStateOf(chipTitle?.let { getBalmInfoByTitle(it) }) }

    procedureScreenViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProcedureScreenSideEffect.OnNavigateUp -> onNavigateUp?.invoke()
            is ProcedureScreenSideEffect.OnOptionSelected -> {
                selectedOption = sideEffect.selectedOption
                chipData = BigChipType.getChipDataByTitle(sideEffect.selectedOption)
                balmInfo = getBalmInfoByTitle(sideEffect.selectedOption)
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.procedure_screen_title),
                onNavigateUp = procedureScreenViewModel::onNavigateUp
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        top = ZdravnicaAppTheme.dimens.size12,
                        start = ZdravnicaAppTheme.dimens.size12,
                        end = ZdravnicaAppTheme.dimens.size12
                    )
            ) {
                item {
                    ChooseProcedureChipGroup(
                        options = allChipTitles,
                        selectedOption = selectedOption,
                        onOptionSelected = { selectedOption ->
                            procedureScreenViewModel.onOptionSelected(selectedOption)
                        }
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        chipData?.iconRes?.let { painterResource(id = it) }?.let {
                            Icon(
                                painter = it,
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .weight(1f)
                                    .width(ZdravnicaAppTheme.dimens.size500)
                                    .height(ZdravnicaAppTheme.dimens.size430)
                                    .align(Alignment.CenterVertically)
                                    .padding(top = ZdravnicaAppTheme.dimens.size36)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .padding(top = ZdravnicaAppTheme.dimens.size137)
                        ) {
                            ProcedureChipInformationTablet(
                                titleRes = chipData?.title,
                                descriptionRes = chipData?.description
                            )
                            balmInfo?.let { balmInfo ->
                                CheckBalmCountAndOrderTablet(
                                    modifier,
                                    balmInfo,
                                    isBalmCountZero = { balmName ->
                                        procedureScreenViewModel.getBalmCount(
                                            balmName
                                        ) == 0f
                                    },
                                    startProcedure = {
                                        if (chipTitle != null) {
                                            startProcedure.invoke(chipTitle)
                                        }
                                    }, orderBalm = {
//                                    procedureScreenViewModel.turnOff()//this is not correct,,, add right one
                                    }, balmFilled = {

                                    })
                            }
                        }
                    }
                }
            }
        }
    )
}