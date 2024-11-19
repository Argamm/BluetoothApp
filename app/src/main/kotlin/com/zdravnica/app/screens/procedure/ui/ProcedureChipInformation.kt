package com.zdravnica.app.screens.procedure.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.resources.R
import com.zdravnica.uikit.utils.buildGradientAnnotatedString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProcedureChipInformation(
    modifier: Modifier = Modifier,
    iconRes: Int?,
    titleRes: Int?,
    descriptionRes: Int?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(ZdravnicaAppTheme.dimens.size16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        iconRes?.let { painterResource(id = it) }?.let {
            Icon(
                painter = it,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(ZdravnicaAppTheme.dimens.size200)
            )
        }

        Spacer(modifier = Modifier.height(ZdravnicaAppTheme.dimens.size8))

        Text(
            text = buildGradientAnnotatedString(
                titleRes,
                ZdravnicaAppTheme.colors.timeAndTemperatureColor
            ),
            style = ZdravnicaAppTheme.typography.headH3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .basicMarquee()
                .fillMaxWidth()
                .padding(horizontal = ZdravnicaAppTheme.dimens.size16)
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

@Preview(showBackground = true)
@Composable
fun PreviewProcedureChipInformation() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        ProcedureChipInformation(
            iconRes = R.mipmap.ic_skin,
            titleRes = R.string.select_product_brain,
            descriptionRes = R.string.select_product_skin_description
        )
    }
}