package com.zdravnica.app.screens.selectProcedure.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.uikit.ERROR_ICON_DESCRIPTION
import com.zdravnica.uikit.resources.R

@Composable
fun IndicatorsStateInf(
    modifier: Modifier = Modifier,
    indicatorInfo: String,
    indicatorInstruction: String,
) {
    Card(
        elevation = ZdravnicaAppTheme.dimens.size4,
        shape = RoundedCornerShape(ZdravnicaAppTheme.dimens.size24),
        backgroundColor = Color.White,
        modifier = modifier
            .padding(
                top = ZdravnicaAppTheme.dimens.size16,
                start = ZdravnicaAppTheme.dimens.size12,
                end = ZdravnicaAppTheme.dimens.size12
            )
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(ZdravnicaAppTheme.dimens.size18)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                    contentDescription = ERROR_ICON_DESCRIPTION,
                    tint = ZdravnicaAppTheme.colors.baseAppColor.gray200,
                    modifier = Modifier.size(ZdravnicaAppTheme.dimens.size36)
                )
                Spacer(modifier = Modifier.width(ZdravnicaAppTheme.dimens.size8))
                Column {
                    Text(
                        text = indicatorInfo,
                        style = ZdravnicaAppTheme.typography.bodyNormalBold,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray200
                    )
                    Text(
                        text = indicatorInstruction,
                        style = ZdravnicaAppTheme.typography.bodySmallRegular,
                        color = ZdravnicaAppTheme.colors.baseAppColor.gray500
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun IndicatorsStateInfPrev() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        IndicatorsStateInf(
            indicatorInfo = "",
            indicatorInstruction = "",
        )
    }
}