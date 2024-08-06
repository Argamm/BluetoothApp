package com.zdravnica.uikit.base_type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class ProcedureTypes(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int
) {
    data object Skin : ProcedureTypes(
        titleRes = 0,
        descriptionRes = 0,
        iconRes = 0,
    )

}