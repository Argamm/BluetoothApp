package com.zdravnica.app.screens.dialog.models

data class CancelProcedureDialogState(
    val titleText: String = "",
    val onClose: () -> Unit = {},
    val onNoClick: () -> Unit = {},
    val onYesClick: () -> Unit = {}
)