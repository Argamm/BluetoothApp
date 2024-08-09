package com.zdravnica.app.screens.connecting_page.selectProcedure.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.components.chips.ui.BigChipsComponent
import com.zdravnica.uikit.resources.R

@Composable
fun ChooseProcedureGridLayout(
    bigChipsList: List<BigChipsStateModel>,
    onCardClick: (BigChipsStateModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ZdravnicaAppTheme.dimens.size16)
    ) {
        Text(
            text = stringResource(id = R.string.select_product_procedure),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = ZdravnicaAppTheme.dimens.size18,
                    bottom = ZdravnicaAppTheme.dimens.size18
                ),
            style = ZdravnicaAppTheme.typography.bodyLargeSemi.copy(
                color = ZdravnicaAppTheme.colors.baseAppColor.gray200
            ),
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .heightIn(max = ZdravnicaAppTheme.dimens.size1500)
        ) {
            items(bigChipsList) { chip ->
                BigChipsComponent(
                    bigChipsStateModel = chip,
                    modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size8),
                    onBigChipsSelected = { onCardClick(chip) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopTextAndGridLayout() {
    val sampleChips = listOf(
        BigChipsStateModel(
            isEnabled = true,
            title = "Chip 1",
            description = "Description 1",
            iconRes = R.mipmap.ic_intestine
        ),
        BigChipsStateModel(
            isEnabled = false,
            title = "Chip 2",
            description = "Description 2",
            iconRes = R.mipmap.ic_intestine
        ),
        BigChipsStateModel(
            isEnabled = true,
            title = "Chip 3",
            description = "Description 3",
            iconRes = R.mipmap.ic_intestine
        ),
        BigChipsStateModel(
            isEnabled = false,
            title = "Chip 4",
            description = "Description 4",
            iconRes = R.mipmap.ic_intestine
        )
    )
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ChooseProcedureGridLayout(bigChipsList = sampleChips) {}
    }
}
