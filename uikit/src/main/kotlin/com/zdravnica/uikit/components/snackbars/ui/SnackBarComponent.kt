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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.SnackBarStateColor
import com.zdravnica.uikit.components.snackbars.models.SnackBarModel
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum
import com.zdravnica.uikit.components.snackbars.previewparams.SnackBarPreviewParams

@Composable
fun SnackBarComponent(
    snackBarModel: SnackBarModel,
    modifier: Modifier = Modifier,
    snackBarStateColor: SnackBarStateColor = ZdravnicaAppTheme.colors.snackBarStateColor,
    shape: Shape = ZdravnicaAppTheme.roundedCornerShape.shapeR8
) {
    val backgroundColor by remember {
        mutableStateOf(
            when (snackBarModel.snackBarType) {
                SnackBarTypeEnum.SNACK_BAR_SUCCESS -> snackBarStateColor.successBackgroundColor
                SnackBarTypeEnum.SNACK_BAR_WARNING -> snackBarStateColor.warningBackgroundColor
                SnackBarTypeEnum.SNACK_BAR_ERROR -> snackBarStateColor.errorBackgroundColor
            }
        )
    }

    val contentColor by remember {
        mutableStateOf(
            when (snackBarModel.snackBarType) {
                SnackBarTypeEnum.SNACK_BAR_SUCCESS -> snackBarStateColor.successContentColor
                SnackBarTypeEnum.SNACK_BAR_WARNING -> snackBarStateColor.warningContentColor
                SnackBarTypeEnum.SNACK_BAR_ERROR -> snackBarStateColor.errorContentColor
            }
        )
    }

    Snackbar(
        backgroundColor = Color.Transparent,
        contentColor = contentColor,
        actionOnNewLine = snackBarModel.actionOnNewLine,
        shape = shape,
        modifier = modifier,
        elevation = ZdravnicaAppTheme.dimens.size1,
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
                        vertical = ZdravnicaAppTheme.dimens.size24,
                        horizontal = ZdravnicaAppTheme.dimens.size8
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ZdravnicaAppTheme.dimens.size4)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(snackBarModel.snackBarType.iconRes),
                    contentDescription = snackBarModel.snackBarType.name,
                    modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size20),
                    tint = contentColor
                )

                Text(
                    text = snackBarModel.snackBarData.message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    style = ZdravnicaAppTheme.typography.bodyNormalMedium.copy(
                        color = contentColor,
                        textAlign = TextAlign.Start
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
}

@Preview
@Composable
private fun SnackBarComponentPreview(
    @PreviewParameter(SnackBarPreviewParams::class)
    snackBarModel: SnackBarModel
) {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        SnackBarComponent(
            snackBarModel = snackBarModel,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}