package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenSideEffect
import com.zdravnica.app.screens.procedure.viewModels.ProcedureScreenViewModel
import com.zdravnica.app.screens.statusScreen.StatusScreen
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.BigChipType
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getAllChipTitles
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getBalmInfoByTitle
import com.zdravnica.uikit.components.statusDetails.StatusInfoState
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
    navigateToTheConnectionScreen: () -> Unit,
) {
    val context = LocalContext.current
    val allChipTitles = getAllChipTitles()
    val procedureScreenViewState by procedureScreenViewModel.container.stateFlow.collectAsStateWithLifecycle()
    var selectedOption: Int? by remember { mutableStateOf(chipTitle) }
    var chipData by remember {
        mutableStateOf(selectedOption?.let { selectedOption ->
            BigChipType.getChipDataByTitle(
                selectedOption
            )
        })
    }
    var balmInfo by remember { mutableStateOf(chipTitle?.let { getBalmInfoByTitle(it) }) }
    val stringBurdock = stringResource(R.string.menu_screen_burdock)
    val stringNut = stringResource(R.string.menu_screen_nut)
    val stringMint = stringResource(R.string.menu_screen_mint)
    val balmNameList = listOf(stringBurdock, stringNut, stringMint)
    var showFailedScreen by remember { mutableStateOf(false) }
    var statusInfoState by remember { mutableStateOf(StatusInfoState.THERMOSTAT_ACTIVATION) }

    procedureScreenViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProcedureScreenSideEffect.OnNavigateUp -> onNavigateUp?.invoke()
            is ProcedureScreenSideEffect.OnOptionSelected -> {
                selectedOption = sideEffect.selectedOption
                chipData = BigChipType.getChipDataByTitle(sideEffect.selectedOption)
                balmInfo = getBalmInfoByTitle(sideEffect.selectedOption)
            }

            is ProcedureScreenSideEffect.OnBluetoothConnectionLost -> {
                showFailedScreen = true
                statusInfoState = StatusInfoState.CONNECTION_LOST
            }
        }
    }

    LaunchedEffect(Unit) {
        procedureScreenViewModel.updateBalmCounts(balmNameList)
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
                                when (balmName) {
                                    stringBurdock -> procedureScreenViewState.firstBalmCount == 2f
                                    stringNut -> procedureScreenViewState.secondBalmCount == 2f
                                    stringMint -> procedureScreenViewState.thirdBalmCount == 2f
                                    else -> false
                                }
                            },
                            startProcedure = {
                                if (chipTitle != null) {
                                    procedureScreenViewModel.startProcedureWithCommands()
                                    startProcedure.invoke(chipTitle)
                                }
                            }, orderBalm = {

                            }, balmFilled = {
                                balmInfo.forEach { balm ->
                                    procedureScreenViewModel.balmFilled(
                                        balmName = context.getString(
                                            balm.balmName
                                        ),
                                        balmsName = balmNameList
                                    )
                                }
                            }
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
fun PreviewMenuScreen() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureScreen(startProcedure = {}, navigateToTheConnectionScreen = {})
    }
}