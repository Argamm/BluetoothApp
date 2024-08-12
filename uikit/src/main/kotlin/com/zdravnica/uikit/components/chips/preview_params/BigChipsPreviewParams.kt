package com.zdravnica.uikit.components.chips.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.chips.models.BigChipsStateModel
import com.zdravnica.uikit.resources.R

internal class BigChipsPreviewParams : PreviewParameterProvider<BigChipsStateModel> {
    override val values: Sequence<BigChipsStateModel>
        get() = listOf(
            BigChipsStateModel(
                isEnabled = true,
                title = R.string.select_product_without_balm,
                description = R.string.select_product_without_balm_description,
                iconRes = null
            ),
            BigChipsStateModel(
                isEnabled = false,
                title = R.string.select_product_nose,
                description = R.string.select_product_nose_description,
                iconRes = R.mipmap.ic_nose
            ),
        ).asSequence()
}

