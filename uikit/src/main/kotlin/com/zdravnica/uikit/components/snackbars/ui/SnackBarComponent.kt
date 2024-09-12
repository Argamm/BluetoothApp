package com.zdravnica.uikit.components.snackbars.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.SnackBarStateColor
import com.zdravnica.uikit.resources.R
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum

@Composable
fun SnackBarComponent(
    snackBarType: SnackBarTypeEnum,
    modifier: Modifier = Modifier,
    snackBarStateColor: SnackBarStateColor = ZdravnicaAppTheme.colors.snackBarStateColor,
    shape: Shape = ZdravnicaAppTheme.roundedCornerShape.shapeR8
) {
    val colors = ZdravnicaAppTheme.colors
    val backgroundColor by remember {
        mutableStateOf(
            when (snackBarType) {
                SnackBarTypeEnum.SNACK_BAR_SUCCESS -> snackBarStateColor.successBackgroundColor
                SnackBarTypeEnum.SNACK_BAR_WARNING -> snackBarStateColor.warningBackgroundColor
                SnackBarTypeEnum.SNACK_BAR_ERROR -> colors.baseAppColor.error500
            }
        )
    }

    val message = when (snackBarType) {
        SnackBarTypeEnum.SNACK_BAR_SUCCESS -> stringResource(R.string.snack_bar_success)
        SnackBarTypeEnum.SNACK_BAR_WARNING -> stringResource(R.string.snack_bar_warning)
        SnackBarTypeEnum.SNACK_BAR_ERROR -> stringResource(R.string.snack_bar_error)
    }

    Snackbar(
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        actionOnNewLine = false,
        shape = shape,
        modifier = modifier,
        elevation = 0.dp,
        content = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .requiredHeightIn(min = ZdravnicaAppTheme.dimens.size14)
                    .clip(shape)
                    .shadow(
                        elevation = ZdravnicaAppTheme.dimens.size8,
                        shape = shape,
                        clip = true
                    )
                    .background(backgroundColor)
                    .padding(
                        vertical = ZdravnicaAppTheme.dimens.size15,
                        horizontal = ZdravnicaAppTheme.dimens.size8
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(snackBarType.iconRes),
                    contentDescription = snackBarType.name,
                    modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size20),
                    tint = Color.White
                )

                Text(
                    text = message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = ZdravnicaAppTheme.dimens.size3),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium.copy(
                        color = Color.White,
                        textAlign = TextAlign.Start
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SnackBarComponentPreview() {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        SnackBarComponent(
            snackBarType = SnackBarTypeEnum.SNACK_BAR_ERROR,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}