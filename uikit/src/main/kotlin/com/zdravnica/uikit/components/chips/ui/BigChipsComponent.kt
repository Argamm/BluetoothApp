package com.zdravnica.uikit.components.chips.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.BigChipsStateColor
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.components.chips.preview_params.BigChipsPreviewParams
import com.zdravnica.uikit.extensions.compose.clickableSingle

@Composable
fun BigChipsComponent(
    bigChipsStateModel: BigChipsStateModel,
    modifier: Modifier = Modifier,
    bigChipsStateColor: BigChipsStateColor = ZdravnicaAppTheme.colors.bigChipsStateColor,
    onBigChipsSelected: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.wrapContentSize(),
        shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
        border = BorderStroke(
            width = ZdravnicaAppTheme.dimens.size3, brush = Brush.linearGradient(
                bigChipsStateColor.borderStrokeGradientColors
            )
        ),
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR14)
            .background(
                brush = Brush.linearGradient(
                    bigChipsStateColor.backgroundGradientColors
                )
            )
            .clickableSingle(enabled = bigChipsStateModel.isEnabled) {
                onBigChipsSelected?.invoke()
            }
            .padding(ZdravnicaAppTheme.dimens.size12)) {

            Image(
                painter = painterResource(id = bigChipsStateModel.iconRes),
                contentDescription = "Big chips icon",
                modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size36),
            )

            Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size4))

            Text(
                text = bigChipsStateModel.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                style = ZdravnicaAppTheme.typography.bodyNormalSemi.copy(
                    color = if (bigChipsStateModel.isEnabled) {
                        bigChipsStateColor.enabledTitleColor
                    } else bigChipsStateColor.disabledContentColor
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size4))

            Text(
                text = bigChipsStateModel.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                style = ZdravnicaAppTheme.typography.bodyXSMedium.copy(
                    color = if (bigChipsStateModel.isEnabled) {
                        bigChipsStateColor.enabledDescriptionColor
                    } else bigChipsStateColor.disabledContentColor
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview
@Composable
private fun BigChipsComponentPreview(
    @PreviewParameter(BigChipsPreviewParams::class)
    bigChipsStateModel: BigChipsStateModel
) {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        BigChipsComponent(
            bigChipsStateModel = bigChipsStateModel, modifier = Modifier.requiredSize(
                width = ZdravnicaAppTheme.dimens.size204,
                height = ZdravnicaAppTheme.dimens.size140
            )
        )
    }
}