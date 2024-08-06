package com.zdravnica.uikit.components.snackbars.previewparams

import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.zdravnica.uikit.components.snackbars.models.SnackBarModel
import com.zdravnica.uikit.components.snackbars.models.SnackBarTypeEnum

internal class SnackBarPreviewParams : PreviewParameterProvider<SnackBarModel> {
    override val values: Sequence<SnackBarModel>
        get() = listOf(
            SnackBarModel(
                snackBarData = object : SnackbarData {
                    override val actionLabel: String
                        get() = ""
                    override val duration: SnackbarDuration
                        get() = SnackbarDuration.Short
                    override val message: String
                        get() = "Ошибка подключения"

                    override fun dismiss() = Unit

                    override fun performAction() = Unit
                },
                snackBarType = SnackBarTypeEnum.SNACK_BAR_ERROR
            ),
            SnackBarModel(
                snackBarData = object : SnackbarData {
                    override val actionLabel: String
                        get() = ""
                    override val duration: SnackbarDuration
                        get() = SnackbarDuration.Short
                    override val message: String
                        get() = "Подключение установлено"

                    override fun dismiss() = Unit

                    override fun performAction() = Unit
                },
                snackBarType = SnackBarTypeEnum.SNACK_BAR_SUCCESS,

                ),

            SnackBarModel(
                snackBarData = object : SnackbarData {
                    override val actionLabel: String
                        get() = ""
                    override val duration: SnackbarDuration
                        get() = SnackbarDuration.Short
                    override val message: String
                        get() = "Подключено к чужому устройству"

                    override fun dismiss() = Unit

                    override fun performAction() = Unit
                },
                snackBarType = SnackBarTypeEnum.SNACK_BAR_WARNING
            )
        ).asSequence()

}
