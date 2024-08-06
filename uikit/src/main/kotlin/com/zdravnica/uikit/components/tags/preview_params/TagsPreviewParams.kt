package com.zdravnica.uikit.components.tags.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.tags.models.TagModel
import com.zdravnica.uikit.components.tags.models.TagStatus

private const val TAG_TEXT = "Текст"

internal class TagsPreviewParams : PreviewParameterProvider<TagModel> {
    override val values: Sequence<TagModel>
        get() = listOf(
            TagModel(
                text = TAG_TEXT,
                status = TagStatus.ON
            ),
            TagModel(
                text = TAG_TEXT,
                status = TagStatus.OFF
            ),

            ).asSequence()
}
