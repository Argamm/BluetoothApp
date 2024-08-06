package com.zdravnica.uikit.components.tags.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppExerciseTheme
import com.zdravnica.resources.ui.theme.models.ZdravnicaAppTheme
import com.zdravnica.resources.ui.theme.models.featureColors.TagsVariantColors
import com.zdravnica.uikit.components.tags.models.TagModel
import com.zdravnica.uikit.components.tags.models.TagStatus
import com.zdravnica.uikit.components.tags.preview_params.TagsPreviewParams
import com.zdravnica.uikit.extensions.compose.clickableSingle

@Composable
fun TagComponent(
    tagStateModel: TagModel,
    modifier: Modifier = Modifier,
    stateColor: TagsVariantColors = ZdravnicaAppTheme.colors.tagsVariantColors,
    textStyle: TextStyle = ZdravnicaAppTheme.typography.bodyXSMedium,
    onTagClick: (() -> Unit)? = null

) {
    Row(modifier = modifier
        .wrapContentWidth()
        .heightIn(min = ZdravnicaAppTheme.dimens.size24)
        .clip(ZdravnicaAppTheme.roundedCornerShape.shapeR12)
        .background(
            color = when (tagStateModel.status) {
                TagStatus.ON -> stateColor.enabledBackGroundColor
                TagStatus.OFF -> stateColor.disabledBackgroundColor
            }
        )
        .clickableSingle { onTagClick?.invoke() }
        .padding(
            start = ZdravnicaAppTheme.dimens.size4,
            top = ZdravnicaAppTheme.dimens.size4,
            end = ZdravnicaAppTheme.dimens.size8,
            bottom = ZdravnicaAppTheme.dimens.size4
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(tagStateModel.iconRes),
            contentDescription = "Icon time",
            modifier = Modifier.requiredSize(ZdravnicaAppTheme.dimens.size12),
            tint = when (tagStateModel.status) {
                TagStatus.ON -> stateColor.enabledContentColor
                TagStatus.OFF -> stateColor.disabledContentColor
            }
        )

        Spacer(modifier = Modifier.requiredWidth(ZdravnicaAppTheme.dimens.size1))

        Text(
            text = tagStateModel.text,
            modifier = Modifier.wrapContentSize(),
            style = textStyle,
            color = when (tagStateModel.status) {
                TagStatus.ON -> stateColor.enabledContentColor
                TagStatus.OFF -> stateColor.disabledContentColor
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TagsComponentPreview(
    @PreviewParameter(TagsPreviewParams::class) tagStateModel: TagModel
) {
    ZdravnicaAppExerciseTheme(darkThem = false) {
        TagComponent(
            tagStateModel = tagStateModel, modifier = Modifier.requiredSizeIn(
                minHeight = ZdravnicaAppTheme.dimens.size20,
                minWidth = ZdravnicaAppTheme.dimens.size56
            )
        )
    }
}
