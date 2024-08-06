package com.zdravnica.resources.ui.theme.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ZdravnicaAppExerciseTheme(
    style: ZdravnicarStyle = ZdravnicarStyle.Main,
    darkThem: Boolean,
    content: @Composable () -> Unit
) {
    val color = when (darkThem) {
        true -> {
            when (style) {
                ZdravnicarStyle.Main -> BaseDarkPalette
            }
        }

        else -> when (style) {
            ZdravnicarStyle.Main -> BaseLightPalette
        }
    }
    CompositionLocalProvider(
        LocalCurrentColor provides color,
        LocalZdravnicaAppTypography provides typography,
        LocalZdravnicaAppRoundedCornerShape provides shapes,
        LocalZdravnicaAppDimens provides dimens,
        content = content
    )
}