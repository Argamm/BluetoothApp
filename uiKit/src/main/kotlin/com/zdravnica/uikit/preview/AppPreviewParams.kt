package com.zdravnica.uikit.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    group = "Devices",
    showSystemUi = true,
    device = Devices.PIXEL,
    locale = "ru",
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
annotation class PhonePreview

@Preview(
    group = "Devices",
    showSystemUi = true,
    device = Devices.PIXEL_TABLET,
    widthDp = 640,
    heightDp = 360,
    locale = "ru",
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
annotation class TabletPreview

@PhonePreview
@TabletPreview
annotation class AppPreview

