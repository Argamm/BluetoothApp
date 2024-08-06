package com.zdravnica.uikit.components.tags.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.zdravnica.uikit.resources.R

@Immutable
data class TagModel(
    val text: String,
    val status: TagStatus = TagStatus.ON,
    @DrawableRes val iconRes: Int = R.drawable.ic_time
)

enum class TagStatus {
    ON, OFF
}
