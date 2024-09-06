package com.zdravnica.app.screens.procedure.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme

@Composable
fun ProcedureChipInformationTablet(
    modifier: Modifier = Modifier,
    titleRes: Int?,
    descriptionRes: Int?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(ZdravnicaAppTheme.dimens.size16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        brush = Brush.linearGradient(
                            colors = ZdravnicaAppTheme.colors.timeAndTemperatureColor
                        )
                    )
                ) {
                    append(titleRes?.let { stringResource(id = it) })
                }
            },
            style = ZdravnicaAppTheme.typography.headH2,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))

        Text(
            text = descriptionRes?.let { stringResource(id = it) } ?: "",
            style = ZdravnicaAppTheme.typography.bodyNormalRegular,
            color = ZdravnicaAppTheme.colors.baseAppColor.gray400,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}