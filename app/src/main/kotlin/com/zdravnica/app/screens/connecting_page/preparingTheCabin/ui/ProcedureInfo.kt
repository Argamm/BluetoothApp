package com.zdravnica.app.screens.connecting_page.preparingTheCabin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.uikit.resources.R
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun ProcedureInfo(
    modifier: Modifier = Modifier,
    procedureName: String,
    temperature: Int,
    minutes: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = ZdravnicaAppTheme.dimens.size50),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = procedureName,
            style = ZdravnicaAppTheme.typography.bodyLargeSemi,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray200
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))

        Text(
            text = stringResource(
                R.string.preparing_the_cabin_time_and_temperature_info,
                temperature,
                minutes
            ),
            style = ZdravnicaAppTheme.typography.bodyMediumMedium,
            color = ZdravnicaAppTheme.colors.baseAppColor.primary500
        )
    }
}

@Preview
@Composable
fun PreviewProcedureInfo() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureInfo(
            procedureName = "Процедура 1",
            temperature = 64,
            minutes = 15
        )
    }
}
