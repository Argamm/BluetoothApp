package com.zdravnica.uikit.components.chips.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.resources.R

private const val TITLE_TEXT = "Заголовок"
private const val DESCRIPTION_TEXT = "Описание"

internal class BigChipsPreviewParams : PreviewParameterProvider<BigChipsStateModel> {
    override val values: Sequence<BigChipsStateModel>
        get() = listOf(
            BigChipsStateModel(
                isEnabled = true,
                title = TITLE_TEXT,
                description = DESCRIPTION_TEXT,
                iconRes = R.mipmap.ic_intestine
            ),
            BigChipsStateModel(
                isEnabled = false,
                title = TITLE_TEXT,
                description = DESCRIPTION_TEXT,
                iconRes = R.mipmap.ic_intestine
            ),
        ).asSequence()
}

