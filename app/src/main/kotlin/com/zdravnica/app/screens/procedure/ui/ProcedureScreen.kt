package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenSideEffect
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenViewModel
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getAllChipTitles
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getBalmInfoByTitle
import com.zdravnica.uikit.components.topAppBar.SimpleTopAppBar
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import com.zdravnica.uikit.resources.R

@Composable
fun ProcedureScreen(
    modifier: Modifier = Modifier,
    procedureScreenViewModel: ProcedureScreenViewModel = koinViewModel(),
    chipTitle: Int? = null,
    onNavigateUp: (() -> Unit)? = null,
    startProcedure: (Int) -> Unit,
) {
    val context = LocalContext.current
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
                    ProcedureChipInformation(
                        iconRes = chipData?.iconRes,
                        titleRes = chipData?.title,
                        descriptionRes = chipData?.description
                    )
                }

                item {
                    balmInfo?.let { balmInfo ->
                        CheckBalmCountAndOrder(
                            modifier,
                            balmInfo,
                            isBalmCountZero = { balmName ->
                                procedureScreenViewModel.getBalmCount(
                                    balmName
                                ) == 0f
                            },
                            startProcedure = {
                                if (chipTitle != null) {
                                    procedureScreenViewModel.startProcedureWithCommands()
                                    startProcedure.invoke(chipTitle)
                                }
                            }, orderBalm = {

                            }, balmFilled = {
                                balmInfo.forEach { balm ->
                                    procedureScreenViewModel.balmFilled(balmName = context.getString(balm.balmName)) // Assuming balm.balmName is an Int resource ID
                                }
                            })
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewMenuScreen() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureScreen() {}
    }
}