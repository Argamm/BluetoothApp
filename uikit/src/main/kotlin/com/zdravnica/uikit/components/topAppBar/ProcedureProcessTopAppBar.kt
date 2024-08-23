package com.zdravnica.uikit.components.topAppBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.TOP_APP_BAR_TEMPERATURE_DESCRIPTION
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.fourItemIndicator.IndicatorFourIcons
import com.zdravnica.uikit.resources.R

@Composable
fun ProcedureProcessTopAppBar(
    modifier: Modifier = Modifier,
    temperature: Int,
    fourSwitchState: Boolean,
    backgroundColor: Color
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = ZdravnicaAppTheme.dimens.size6)
                        .size(ZdravnicaAppTheme.dimens.size60),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_temp),
                    contentDescription = TOP_APP_BAR_TEMPERATURE_DESCRIPTION,
                    tint = ZdravnicaAppTheme.colors.baseAppColor.primary500
                )
                Text(
                    modifier = Modifier.padding(top = ZdravnicaAppTheme.dimens.size12),
                    text = stringResource(
                        R.string.select_product_temperature_value,
                        temperature
                    ),
                    color = ZdravnicaAppTheme.colors.baseAppColor.primary500,
                    style = ZdravnicaAppTheme.typography.headH2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))

                val iconStates = remember(fourSwitchState) {//this data must get from bluetooth
                    mutableStateListOf(
                        IconState.ENABLED,
                        IconState.ENABLED,
                        IconState.ENABLED,
                        IconState.ENABLED
                    )
                }

                IndicatorFourIcons(
                    iconStates
                )
                Spacer(modifier = Modifier.padding(end = ZdravnicaAppTheme.dimens.size12))

            }
        },
        elevation = 0.dp
    )
}

@Preview
@Composable
fun PreviewCustomTopAppBar() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureProcessTopAppBar(
            temperature = 54,
            fourSwitchState = true,
            backgroundColor = Color.White
        )
    }
}