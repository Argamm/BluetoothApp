package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.components.chips.models.BigChipType.Companion.getAllChipTitles
import com.zdravnica.uikit.resources.R

@Composable
fun ChooseProcedureChipGroup(
    modifier: Modifier = Modifier,
    options: List<Int>,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedOption) {
        selectedOption?.let {
            val index = options.indexOf(it)
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
    ) {
        items(options.size) { index ->
            val option = options[index]
            val isSelected = selectedOption == option
            Chip(
                text = stringResource(id = option),
                isSelected = isSelected,
                onClick = { onOptionSelected(option) }
            )
        }
    }
}

@Composable
fun Chip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected)
        ZdravnicaAppTheme.colors.baseAppColor.primary400
    else
        Color.White
    val textColor = if (isSelected) Color.White else ZdravnicaAppTheme.colors.baseAppColor.gray400
    val borderColor = if (isSelected)
        ZdravnicaAppTheme.colors.baseAppColor.primary400
    else
        ZdravnicaAppTheme.colors.baseAppColor.gray400

    Row(
        modifier = Modifier
            .padding(end = ZdravnicaAppTheme.dimens.size4)
            .clip(RoundedCornerShape(ZdravnicaAppTheme.dimens.size8))
            .border(
                ZdravnicaAppTheme.dimens.size1,
                borderColor,
                RoundedCornerShape(ZdravnicaAppTheme.dimens.size8)
            )
            .background(backgroundColor)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success_outline),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(start = ZdravnicaAppTheme.dimens.size8,
                        end = ZdravnicaAppTheme.dimens.size3)
                    .size(ZdravnicaAppTheme.dimens.size18)
            )
        }
        Text(
            modifier = Modifier.then(
                if (isSelected) {
                    Modifier.padding(
                        top = ZdravnicaAppTheme.dimens.size8,
                        bottom = ZdravnicaAppTheme.dimens.size8,
                        end = ZdravnicaAppTheme.dimens.size8
                    )
                } else
                    Modifier.padding(ZdravnicaAppTheme.dimens.size8)
            ),
            text = text,
            color = textColor,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
private fun ChooseProcedureChipGroupPrev() {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ChooseProcedureChipGroup(
            options = getAllChipTitles(),
            selectedOption = R.string.select_product_skin,
            onOptionSelected = { selectedOption = it }
        )
    }
}