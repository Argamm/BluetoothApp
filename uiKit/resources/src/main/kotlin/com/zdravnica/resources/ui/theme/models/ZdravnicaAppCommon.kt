package com.zdravnica.resources.ui.theme.models

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.zdravnica.resources.ui.theme.models.featureColors.IconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryButtonColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryIconButtonStateColor

@Immutable
data class ZdravnicaAppColor(
    val white: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,
    val gray1000: Color,
    val primary0: Color,
    val primary100: Color,
    val primary200: Color,
    val primary300: Color,
    val primary400: Color,
    val primary500: Color,
    val primary600: Color,
    val primary700: Color,
    val primary800: Color,
    val primary900: Color,
    val primary1000: Color,
    val secondary0: Color,
    val secondary100: Color,
    val secondary200: Color,
    val secondary300: Color,
    val secondary400: Color,
    val secondary500: Color,
    val secondary600: Color,
    val secondary700: Color,
    val secondary800: Color,
    val secondary900: Color,
    val secondary1000: Color,
)

@Immutable
data class CurrentColor(
    val primaryBackgroundColor: Color,
    val primaryButtonColor: PrimaryButtonColor,
    val primaryIconButtonStateColor: PrimaryIconButtonStateColor,
    val iconButtonStateColor: IconButtonStateColor
)

@Immutable
data class ZdravnicaAppTypography(
    val headH1: TextStyle,
    val headH2: TextStyle,
    val headH3: TextStyle,
    val headH4: TextStyle,
    val headH5: TextStyle,
    val bodyXLBold: TextStyle,
    val bodyXLSemi: TextStyle,
    val bodyXLMedium: TextStyle,
    val bodyXLReg: TextStyle,
    val bodyXLThin: TextStyle,
    val bodyLargeBold: TextStyle,
    val bodyLargeSemi: TextStyle,
    val bodyLargeMedium: TextStyle,
    val bodyLargeRegular: TextStyle,
    val bodyLargeThin: TextStyle,
    val bodyMediumBold: TextStyle,
    val bodyMediumSemi: TextStyle,
    val bodyMediumMedium: TextStyle,
    val bodyMediumRegular: TextStyle,
    val bodyMediumThin: TextStyle,
    val bodyNormalBold: TextStyle,
    val bodyNormalSemi: TextStyle,
    val bodyNormalMedium: TextStyle,
    val bodyNormalRegular: TextStyle,
    val bodyNormalThin: TextStyle,
)

@Immutable
data class ZdravnicaAppRoundedCornerShape(
    val shapeR14: RoundedCornerShape,
    val shapeR16: RoundedCornerShape,
    val shapeR24: RoundedCornerShape
)

@Immutable
data class ZdravnicaAppDimens(
    val size1: Dp,
    val size3: Dp,
    val size4: Dp,
    val size12: Dp,
    val size16: Dp,
    val size18: Dp,
    val size24: Dp,
    val size44: Dp,
    val size48: Dp,
    val size56: Dp,
    val size198: Dp,
    val size250: Dp
)

object ZdravnicaAppTheme {
    val colors: CurrentColor
        @Composable get() = LocalCurrentColor.current

    val typography: ZdravnicaAppTypography
        @Composable get() = LocalZdravnicaAppTypography.current

    val roundedCornerShape: ZdravnicaAppRoundedCornerShape
        @Composable get() = LocalZdravnicaAppRoundedCornerShape.current

    val dimens: ZdravnicaAppDimens
        @Composable get() = LocalZdravnicaAppDimens.current

}

val LocalCurrentColor = staticCompositionLocalOf<CurrentColor> {
    error("No colors provided")
}
val LocalZdravnicaAppTypography = staticCompositionLocalOf<ZdravnicaAppTypography> {
    error("No font provided")
}

val LocalZdravnicaAppRoundedCornerShape = staticCompositionLocalOf<ZdravnicaAppRoundedCornerShape> {
    error("No shape provided")
}

val LocalZdravnicaAppDimens = staticCompositionLocalOf<ZdravnicaAppDimens> {
    error("No dimens provided")
}

enum class ZdravnicarStyle {
    Main
}

enum class LocalLanguageEnum(val languageISO: String) {
    ENGLISH(languageISO = "en"), RUSSIAN(languageISO = "ru")
}



