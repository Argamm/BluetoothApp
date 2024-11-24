package com.zdravnica.uikit.components.snackbars.models

import com.zdravnica.uikit.resources.R

enum class SnackBarTypeEnum(
    val iconRes: Int
) {
    SNACK_BAR_SUCCESS(
        iconRes = R.drawable.ic_success_solid
    ),
    SNACK_BAR_ERROR(
        iconRes = R.drawable.ic_error
    ),
    SNACK_BAR_WARNING(
        iconRes = R.drawable.ic_error
    ),
    SNACK_BAR_WAITING_FOR_FUN(
        iconRes = R.drawable.ic_time
    )
}
