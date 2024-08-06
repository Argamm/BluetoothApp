package com.zdravnica.resources.ui.theme.models

import androidx.compose.ui.graphics.Color
import com.zdravnica.resources.ui.theme.models.featureColors.BigButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.BigChipsStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.IconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryButtonState
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryIconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.SecondaryButtonState
import com.zdravnica.resources.ui.theme.models.featureColors.SnackBarStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.TagsVariantColors
import com.zdravnica.resources.ui.theme.models.featureColors.TertiaryButtonState
import com.zdravnica.resources.ui.theme.models.featureColors.TextButtonStateColor


val appColorFigma = ZdravnicaAppColor(
    white = Color(0xFFFFFFFF),
    gray100 = Color(0xFF010606),
    gray200 = Color(0xFF262626),
    gray300 = Color(0xFF404040),
    gray400 = Color(0xFF666666),
    gray500 = Color(0xFF808080),
    gray600 = Color(0xFFA0A0A0),
    gray700 = Color(0xFFB3B3B3),
    gray800 = Color(0xFFD9D9D9),
    gray900 = Color(0xFFE6E6E6),
    gray1000 = Color(0xFFF2F2F2),
    primary0 = Color(0xFF0D0907),
    primary100 = Color(0xFF36261B),
    primary200 = Color(0xFF281C14),
    primary300 = Color(0xFF553C2A),
    primary400 = Color(0xFF6A4B35),
    primary500 = Color(0xFF855E42),
    primary600 = Color(0xFFA38671),
    primary700 = Color(0xFFB19886),
    primary800 = Color(0xFFDACFC6),
    primary900 = Color(0xFFE7DFD9),
    primary1000 = Color(0xFFF3EFEC),
    secondary0 = Color(0xFF15120F),
    secondary100 = Color(0xFF2A231D),
    secondary200 = Color(0xFF3F352C),
    secondary300 = Color(0xFF69584A),
    secondary400 = Color(0xFFA78D76),
    secondary500 = Color(0xFFD1B093),
    secondary600 = Color(0xFFDAC0A9),
    secondary700 = Color(0xFFE8D7C9),
    secondary800 = Color(0xFFF1E7DF),
    secondary900 = Color(0xFFF6EFE9),
    secondary1000 = Color(0xFFFAF7F4),
    error0 = Color(0xFF140606),
    error100 = Color(0xFF270B0B),
    error200 = Color(0xFF3B1111),
    error300 = Color(0xFF621C1C),
    error400 = Color(0xFF9C2D2D),
    error500 = Color(0xFFC33838),
    error600 = Color(0xFFD26A6A),
    error700 = Color(0xFFE19B9B),
    error800 = Color(0xFFEDC3C3),
    error900 = Color(0xFFF3D7D7),
    error1000 = Color(0xFFF9EBEB),
    success0 = Color(0xFF030E02),
    success100 = Color(0xFF071B03),
    success200 = Color(0xFF0A2905),
    success300 = Color(0xFF114408),
    success400 = Color(0xFF1A6C0D),
    success500 = Color(0xFF218710),
    success600 = Color(0xFF59A54C),
    success700 = Color(0xFF90C387),
    success800 = Color(0xFFBCDBB7),
    success900 = Color(0xFFD3E7CF),
    success1000 = Color(0xFFE9F3E7),
    info0 = Color(0xFF05080B),
    info100 = Color(0xFF091015),
    info200 = Color(0xFF0E1920),
    info300 = Color(0xFF182936),
    info400 = Color(0xFF264256),
    info500 = Color(0xFF2F526B),
    info600 = Color(0xFF637D90),
    info700 = Color(0xFF97A8B5),
    info800 = Color(0xFFC1CBD3),
    info900 = Color(0xFFD5DCE1),
    info1000 = Color(0xFFEAEEF0),
    warning0 = Color(0xFF190F08),
    warning100 = Color(0xFF321E10),
    warning200 = Color(0xFF4B2E18),
    warning300 = Color(0xFF7D4C28),
    warning400 = Color(0xFFC87A3F),
    warning500 = Color(0xFFFA984F),
    warning600 = Color(0xFFFBB27B),
    warning700 = Color(0xFFFDCBA7),
    warning800 = Color(0xFFFDE0CA),
    warning900 = Color(0xFFFEEADC),
    warning1000 = Color(0xFFFEF5ED)
)

val BaseDarkPalette = CurrentColor(
    baseAppColor = appColorFigma,
    primaryBackgroundColor = appColorFigma.white,
    bigButtonStateColor = BigButtonStateColor(
        enabledBackground = appColorFigma.primary500,
        pressedBackground = appColorFigma.primary500,
        disabledBackground = appColorFigma.gray900,
        borderStrokeColor = appColorFigma.secondary700,
        enabledContentColor = appColorFigma.white,
        disabledContentColor = appColorFigma.gray700
    ),
    primaryIconButtonStateColor = PrimaryIconButtonStateColor(
        defaultBackgroundGradientColors = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        pressedBackgroundGradientColors = listOf(
            Color(0x00000000),
            Color(0x1A000000),
        ),
        disabledBackgroundGradientColor = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        defaultBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        pressedBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        disabledBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        defaultContentColor = appColorFigma.gray200,
        pressedContentColor = appColorFigma.gray200,
        disabledContentColor = appColorFigma.gray800
    ),
    iconButtonStateColor = IconButtonStateColor(
        primaryButtonStateColor = PrimaryButtonState(
            defaultBackgroundColor = appColorFigma.primary500,
            pressedBackgroundColor = appColorFigma.primary300,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.white,
            pressedContentColor = appColorFigma.white,
            disabledContentColor = appColorFigma.gray700
        ),
        secondaryButtonState = SecondaryButtonState(
            defaultBackgroundColor = appColorFigma.secondary1000,
            pressedBackgroundColor = appColorFigma.secondary800,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray200,
            disabledContentColor = appColorFigma.gray700
        ),
        tertiaryButtonState = TertiaryButtonState(
            defaultBackgroundColor = Color.Transparent,
            pressedBackgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray500,
            disabledContentColor = appColorFigma.gray800
        )
    ),
    textButtonStateColor = TextButtonStateColor(
        primaryButtonStateColor = PrimaryButtonState(
            defaultBackgroundColor = appColorFigma.primary500,
            pressedBackgroundColor = appColorFigma.primary300,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.white,
            pressedContentColor = appColorFigma.white,
            disabledContentColor = appColorFigma.gray700
        ),
        secondaryButtonState = SecondaryButtonState(
            defaultBackgroundColor = appColorFigma.secondary1000,
            pressedBackgroundColor = appColorFigma.secondary800,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray200,
            disabledContentColor = appColorFigma.gray700
        ),
        tertiaryButtonState = TertiaryButtonState(
            defaultBackgroundColor = Color.Transparent,
            pressedBackgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray500,
            disabledContentColor = appColorFigma.gray800
        )
    ),
    tagsVariantColors = TagsVariantColors(
        enabledBackGroundColor = appColorFigma.gray1000,
        enabledContentColor = appColorFigma.gray300,
        disabledBackgroundColor = appColorFigma.error1000,
        disabledContentColor = appColorFigma.error500
    ),
    bigChipsStateColor = BigChipsStateColor(
        backgroundGradientColors = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        enabledTitleColor = appColorFigma.gray200,
        enabledDescriptionColor = appColorFigma.gray400,
        disabledContentColor = appColorFigma.gray800,
        borderStrokeGradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC),
        )
    ),
    snackBarStateColor = SnackBarStateColor(
        successBackgroundColor = appColorFigma.success500,
        successContentColor = appColorFigma.white,
        warningBackgroundColor = appColorFigma.warning500,
        warningContentColor = appColorFigma.white,
        errorBackgroundColor = appColorFigma.error500,
        errorContentColor = appColorFigma.white
    )
)

val BaseLightPalette = CurrentColor(
    baseAppColor = appColorFigma,
    primaryBackgroundColor = appColorFigma.white,
    bigButtonStateColor = BigButtonStateColor(
        enabledBackground = appColorFigma.primary500,
        pressedBackground = appColorFigma.primary500,
        disabledBackground = appColorFigma.gray900,
        borderStrokeColor = appColorFigma.secondary700,
        enabledContentColor = appColorFigma.white,
        disabledContentColor = appColorFigma.gray700
    ),
    primaryIconButtonStateColor = PrimaryIconButtonStateColor(
        defaultBackgroundGradientColors = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        pressedBackgroundGradientColors = listOf(
            Color(0x00000000),
            Color(0x1A000000),
        ),
        disabledBackgroundGradientColor = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        defaultBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        pressedBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        disabledBorderGradientColor = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC)
        ),
        defaultContentColor = appColorFigma.gray200,
        pressedContentColor = appColorFigma.gray200,
        disabledContentColor = appColorFigma.gray800
    ),
    iconButtonStateColor = IconButtonStateColor(
        primaryButtonStateColor = PrimaryButtonState(
            defaultBackgroundColor = appColorFigma.primary500,
            pressedBackgroundColor = appColorFigma.primary300,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.white,
            pressedContentColor = appColorFigma.white,
            disabledContentColor = appColorFigma.gray700
        ),
        secondaryButtonState = SecondaryButtonState(
            defaultBackgroundColor = appColorFigma.secondary1000,
            pressedBackgroundColor = appColorFigma.secondary800,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray200,
            disabledContentColor = appColorFigma.gray700
        ),
        tertiaryButtonState = TertiaryButtonState(
            defaultBackgroundColor = Color.Transparent,
            pressedBackgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray500,
            disabledContentColor = appColorFigma.gray800
        )
    ),
    textButtonStateColor = TextButtonStateColor(
        primaryButtonStateColor = PrimaryButtonState(
            defaultBackgroundColor = appColorFigma.primary500,
            pressedBackgroundColor = appColorFigma.primary300,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.white,
            pressedContentColor = appColorFigma.white,
            disabledContentColor = appColorFigma.gray700
        ),
        secondaryButtonState = SecondaryButtonState(
            defaultBackgroundColor = appColorFigma.secondary1000,
            pressedBackgroundColor = appColorFigma.secondary800,
            disabledBackgroundColor = appColorFigma.gray900,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray200,
            disabledContentColor = appColorFigma.gray700
        ),
        tertiaryButtonState = TertiaryButtonState(
            defaultBackgroundColor = Color.Transparent,
            pressedBackgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            defaultContentColor = appColorFigma.gray200,
            pressedContentColor = appColorFigma.gray500,
            disabledContentColor = appColorFigma.gray800
        )
    ),
    tagsVariantColors = TagsVariantColors(
        enabledBackGroundColor = appColorFigma.gray1000,
        enabledContentColor = appColorFigma.gray300,
        disabledBackgroundColor = appColorFigma.error1000,
        disabledContentColor = appColorFigma.error500
    ),
    bigChipsStateColor = BigChipsStateColor(
        backgroundGradientColors = listOf(
            Color(0xFFF4F4F4),
            Color(0xFFFEFEFE)
        ),
        enabledTitleColor = appColorFigma.gray200,
        enabledDescriptionColor = appColorFigma.gray400,
        disabledContentColor = appColorFigma.gray800,
        borderStrokeGradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFECECEC),
        )
    ),
    snackBarStateColor = SnackBarStateColor(
        successBackgroundColor = appColorFigma.success500,
        successContentColor = appColorFigma.white,
        warningBackgroundColor = appColorFigma.warning500,
        warningContentColor = appColorFigma.white,
        errorBackgroundColor = appColorFigma.error500,
        errorContentColor = appColorFigma.white
    )
)