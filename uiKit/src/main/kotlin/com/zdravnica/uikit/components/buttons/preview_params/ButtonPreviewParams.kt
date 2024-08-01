package com.zdravnica.uikit.components.buttons.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.buttons.models.PrimaryButtonModel

private const val BUTTON_TXT = "Начать процедуру"

internal class PrimaryButtonPreviewParams : PreviewParameterProvider<PrimaryButtonModel> {
    override val values: Sequence<PrimaryButtonModel>
        get() = listOf(
            PrimaryButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = true
            ),
            PrimaryButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = false
            ),

            ).asSequence()
}

