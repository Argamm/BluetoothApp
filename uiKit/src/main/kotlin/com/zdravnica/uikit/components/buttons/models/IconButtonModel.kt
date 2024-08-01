package com.zdravnica.uikit.components.buttons.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.zdravnica.uiKit.resources.R

@Immutable
data class IconButtonModel(
    val isEnabled: Boolean = true,
    @DrawableRes val iconRes: Int = R.drawable.ic_line_down,
    val contentDescription: String? = null,
    val onClick: (() -> Unit)? = null,
    val type: IconButtonType = IconButtonType.PRIMARY
)


@Stable
enum class IconButtonType {
    PRIMARY, SECONDARY, TERTARY
}

