package com.zdravnica.app.screens.menuScreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.MAX_TEMPERATURE
import com.zdravnica.uikit.TEMPERATURE_ICON_DESCRIPTION
import com.zdravnica.uikit.resources.R

@Composable
fun MenuTemperatureInfo(
    modifier: Modifier = Modifier,
    temperature: Short,
) {
    val primaryColor =
        if (temperature > MAX_TEMPERATURE)
            ZdravnicaAppTheme.colors.baseAppColor.error500
        else
            ZdravnicaAppTheme.colors.baseAppColor.primary500

    Card(
        elevation = ZdravnicaAppTheme.dimens.size8,
        shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
        backgroundColor = if (temperature > MAX_TEMPERATURE)
            ZdravnicaAppTheme.colors.baseAppColor.error1000
        else
            Color.White,
        modifier = modifier
            .padding(top = ZdravnicaAppTheme.dimens.size16)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(ZdravnicaAppTheme.dimens.size16)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_temp),
                contentDescription = TEMPERATURE_ICON_DESCRIPTION,
                modifier = Modifier.size(ZdravnicaAppTheme.dimens.size48),
                tint = primaryColor
            )
            Column {
                Text(
                    text = stringResource(
                        R.string.select_product_temperature_value,
                        temperature
                    ),
                    style = ZdravnicaAppTheme.typography.headH3,
                    color = primaryColor
                )
                Text(
                    text = stringResource(R.string.menu_screen_temperature_title),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium,
                    color = primaryColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTemperatureInfo() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        MenuTemperatureInfo(
            temperature = 75,
        )
    }
}