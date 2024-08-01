package com.zdravnica.resources.ui.theme.models

import androidx.compose.ui.graphics.Color
import com.zdravnica.resources.ui.theme.models.featureColors.IconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryButtonColor
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryButtonState
import com.zdravnica.resources.ui.theme.models.featureColors.PrimaryIconButtonStateColor
import com.zdravnica.resources.ui.theme.models.featureColors.SecondaryButtonState
import com.zdravnica.resources.ui.theme.models.featureColors.TertiaryButtonState


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
    secondary1000 = Color(0xFFFAF7F4)
)

val BaseDarkPalette = CurrentColor(
    primaryBackgroundColor = appColorFigma.white,
    primaryButtonColor = PrimaryButtonColor(
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
    )
)

val BaseLightPalette = CurrentColor(
    primaryBackgroundColor = appColorFigma.white,
    primaryButtonColor = PrimaryButtonColor(
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
    )
)