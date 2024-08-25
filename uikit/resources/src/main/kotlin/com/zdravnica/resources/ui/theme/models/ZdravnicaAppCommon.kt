package com.zdravnica.resources.ui.theme.models

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.zdravnica.resources.ui.theme.models.featureColors.BigButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.BigChipsStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.IconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryIconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.SnackBarStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.SwitchStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.TagsVariantColors
import com.zdravnica.resources.ui.theme.models.featureColors.TextButtonStateColor

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
    val error0: Color,
    val error100: Color,
    val error200: Color,
    val error300: Color,
    val error400: Color,
    val error500: Color,
    val error600: Color,
    val error700: Color,
    val error800: Color,
    val error900: Color,
    val error1000: Color,
    val success0: Color,
    val success100: Color,
    val success200: Color,
    val success300: Color,
    val success400: Color,
    val success500: Color,
    val success600: Color,
    val success700: Color,
    val success800: Color,
    val success900: Color,
    val success1000: Color,
    val info0: Color,
    val info100: Color,
    val info200: Color,
    val info300: Color,
    val info400: Color,
    val info500: Color,
    val info600: Color,
    val info700: Color,
    val info800: Color,
    val info900: Color,
    val info1000: Color,
    val warning0: Color,
    val warning100: Color,
    val warning200: Color,
    val warning300: Color,
    val warning400: Color,
    val warning500: Color,
    val warning600: Color,
    val warning700: Color,
    val warning800: Color,
    val warning900: Color,
    val warning1000: Color,
    val borderBigCard: Color,

)

@Immutable
data class CurrentColor(
    val baseAppColor: ZdravnicaAppColor,
    val primaryBackgroundColor: Color,
    val bigButtonStateColor: BigButtonStateColor,
    val primaryIconButtonStateColor: PrimaryIconButtonStateColor,
    val iconButtonStateColor: IconButtonStateColor,
    val textButtonStateColor: TextButtonStateColor,
    val tagsVariantColors: TagsVariantColors,
    val bigChipsStateColor: BigChipsStateColor,
    val snackBarStateColor: SnackBarStateColor,
    val timeAndTemperatureColor: List<Color>,
    val switchStateColor: SwitchStateColor,
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
    val bodySmallBold: TextStyle,
    val bodySmallSemi: TextStyle,
    val bodySmallMedium: TextStyle,
    val bodySmallRegular: TextStyle,
    val bodySmallThin: TextStyle,
    val bodyXSBold: TextStyle,
    val bodyXSSemi: TextStyle,
    val bodyXSMedium: TextStyle,
    val bodyXSRegular: TextStyle,
    val bodyXSThin: TextStyle
)

@Immutable
data class ZdravnicaAppRoundedCornerShape(
    val shapeR8: RoundedCornerShape,
    val shapeR12: RoundedCornerShape,
    val shapeR14: RoundedCornerShape,
    val shapeR16: RoundedCornerShape,
    val shapeR20: RoundedCornerShape,
    val shapeR24: RoundedCornerShape
)

@Immutable
data class ZdravnicaAppDimens(
    val size1: Dp,
    val size2: Dp,
    val size3: Dp,
    val size4: Dp,
    val size5: Dp,
    val size6: Dp,
    val size8: Dp,
    val size10: Dp,
    val size11: Dp,
    val size12: Dp,
    val size13: Dp,
    val size14: Dp,
    val size15: Dp,
    val size16: Dp,
    val size18: Dp,
    val size19: Dp,
    val size20: Dp,
    val size22: Dp,
    val size24: Dp,
    val size25: Dp,
    val size26: Dp,
    val size27: Dp,
    val size30: Dp,
    val size32: Dp,
    val size35: Dp,
    val size36: Dp,
    val size38: Dp,
    val size40: Dp,
    val size43: Dp,
    val size44: Dp,
    val size48: Dp,
    val size50: Dp,
    val size56: Dp,
    val size60: Dp,
    val size68: Dp,
    val size81: Dp,
    val size82: Dp,
    val size120: Dp,
    val size126: Dp,
    val size140: Dp,
    val size144: Dp,
    val size164: Dp,
    val size198: Dp,
    val size200: Dp,
    val size204: Dp,
    val size250: Dp,
    val size280: Dp,
    val size320: Dp,
    val size1000: Dp,
    val size1500: Dp,
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



