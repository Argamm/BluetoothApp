package com.zdravnica.app.screens.connecting_page.selectProcedure.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.TOP_APP_BAR_MENU_ICON_DESCRIPTION
import com.zdravnica.uikit.TOP_APP_BAR_TEMPERATURE_DESCRIPTION
import com.zdravnica.uikit.base_type.IconState
import com.zdravnica.uikit.components.fourItemIndicator.IndicatorFourIcons

@Composable
fun SelectProcedureTopAppBar(
    modifier: Modifier = Modifier,
    temperature: Int,
    fourSwitchState: Boolean,
    onRightIconClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.zdravnica.uikit.resources.R.drawable.ic_temp),
                    contentDescription = TOP_APP_BAR_TEMPERATURE_DESCRIPTION,
                    tint = Color.Black
                )
                Text(
                    text = stringResource(
                        com.zdravnica.uikit.resources.R.string.select_product_temperature_value,
                        temperature
                    ), color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))

                val iconStates = remember(fourSwitchState) {//this data must get from bluetooth
                    mutableStateListOf(
                        IconState.ENABLED,
                        IconState.ENABLED,
                        IconState.ENABLED,
                        if (fourSwitchState) IconState.ENABLED else IconState.DISABLED
                    )
                }

                IndicatorFourIcons(
                    iconStates
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier
                    .padding(end = ZdravnicaAppTheme.dimens.size12)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onRightIconClick() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = com.zdravnica.uikit.resources.R.drawable.ic_menu),
                        contentDescription = TOP_APP_BAR_MENU_ICON_DESCRIPTION,
                        tint = Color.Black,
                    )
                }
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun PreviewCustomTopAppBar() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        SelectProcedureTopAppBar(
            temperature = 54,
            fourSwitchState = true
        ) {}
    }
}