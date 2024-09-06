package com.zdravnica.app.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp


@Composable
fun  isTablet () : Boolean {
    return LocalConfiguration.current.screenWidthDp >= 600
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
fun  getDimensionBasedOnDeviceType (isTablet: Dp, isMobile: Dp ) : Dp {
    return  when (isTablet()) { true -> isTablet else -> isMobile }
}
@Composable
fun  getTextStyleBasedOnDeviceType (isTablet: TextStyle, isMobile: TextStyle ) : TextStyle {
    return  when (isTablet()) { true -> isTablet else -> isMobile }
}

@Composable
fun getModifierBasedOnDeviceType(isTablet: Modifier, isMobile: Modifier): Modifier {
    return when (isTablet()) { true -> isTablet else -> isMobile }
}