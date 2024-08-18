package com.zdravnica.uikit.components.chips.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.BigChipsStateColor
import com.zdravnica.uikit.BIG_CHIPS_ICON_DESCRIPTION
import com.zdravnica.uikit.COUNT_THREE
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.components.chips.preview_params.BigChipsPreviewParams
import com.zdravnica.uikit.extensions.compose.clickableSingle

@Composable
fun BigChipsComponent(
    modifier: Modifier = Modifier,
    bigChipsStateModel: BigChipsStateModel,
    bigChipsStateColor: BigChipsStateColor = ZdravnicaAppTheme.colors.bigChipsStateColor,
    onBigChipsSelected: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                BorderStroke(
                    width = ZdravnicaAppTheme.dimens.size2,
                    color = ZdravnicaAppTheme.colors.baseAppColor.borderBigCard
                ),
                shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24
            )
            .padding(ZdravnicaAppTheme.dimens.size1)
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clickable { onBigChipsSelected?.invoke() }
                .semantics { role = androidx.compose.ui.semantics.Role.Button },
            shape = ZdravnicaAppTheme.roundedCornerShape.shapeR24,
            border = BorderStroke(
                width = ZdravnicaAppTheme.dimens.size3,
                brush = Brush.linearGradient(
                    bigChipsStateColor.borderStrokeGradientColors,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                )
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(ZdravnicaAppTheme.dimens.size144)
                    .width(ZdravnicaAppTheme.dimens.size164)
                    .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR14)
                    .background(
                        brush = Brush.linearGradient(
                            bigChipsStateColor.backgroundGradientColors
                        )
                    )
                    .clickableSingle(enabled = bigChipsStateModel.isEnabled) {
                        onBigChipsSelected?.invoke()
                    }
                    .padding(ZdravnicaAppTheme.dimens.size12)
            ) {
                if (bigChipsStateModel.iconRes != null) {
                    Image(
                        painter = painterResource(id = bigChipsStateModel.iconRes),
                        contentDescription = BIG_CHIPS_ICON_DESCRIPTION,
                        modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size36),
                    )
                }

                Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size4))

                Text(
                    text = stringResource(bigChipsStateModel.title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    style = ZdravnicaAppTheme.typography.bodyNormalSemi.copy(
                        color = if (bigChipsStateModel.isEnabled) {
                            bigChipsStateColor.enabledTitleColor
                        } else bigChipsStateColor.disabledContentColor
                    ),
                    maxLines = COUNT_THREE,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.requiredHeight(ZdravnicaAppTheme.dimens.size4))

                Text(
                    text = stringResource(bigChipsStateModel.description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    style = ZdravnicaAppTheme.typography.bodyXSMedium.copy(
                        color = if (bigChipsStateModel.isEnabled) {
                            bigChipsStateColor.enabledDescriptionColor
                        } else bigChipsStateColor.disabledContentColor
                    ),
                    maxLines = COUNT_THREE,
                    overflow = TextOverflow.Ellipsis
                )
            }
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