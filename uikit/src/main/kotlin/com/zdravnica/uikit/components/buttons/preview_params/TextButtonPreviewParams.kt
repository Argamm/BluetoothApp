package com.zdravnica.uikit.components.buttons.preview_params

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.buttons.models.TextButtonModel
import com.zdravnica.uikit.components.buttons.models.TextButtonType

private const val BUTTON_TXT = "Label"

internal class TextButtonPreviewParams : PreviewParameterProvider<TextButtonModel> {
    override val values: Sequence<TextButtonModel>
        get() = listOf(
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = true,
                textButtonType = TextButtonType.PRIMARY
            ),
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = false,
                textButtonType = TextButtonType.PRIMARY
            ),
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = true,
                textButtonType = TextButtonType.SECONDARY
            ),
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = false,
                textButtonType = TextButtonType.SECONDARY
            ),
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = true,
                textButtonType = TextButtonType.TERTIARY
            ),
            TextButtonModel(
                buttonText = BUTTON_TXT,
                isEnabled = false,
                textButtonType = TextButtonType.TERTIARY
            )

        ).asSequence()
}

