package com.zdravnica.uikit.components.buttons.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.buttons.models.BigButtonModel

private const val BUTTON_TXT = "Начать процедуру"

internal class PrimaryButtonPreviewParams : PreviewParameterProvider<BigButtonModel> {
    override val values: Sequence<BigButtonModel>
        get() = listOf(
            BigButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = true
            ),
            BigButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = false
            ),

            ).asSequence()
}

