package com.zdravnica.resources.ui.theme.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zdravnica.uikit.resources.R

val fontFamily = FontFamily(
    Font(R.font.giga_sans_black_900, FontWeight.W900),
    Font(R.font.giga_sans_bold_800, FontWeight.W800),
    Font(R.font.giga_sans_semi_bold_700, FontWeight.W700),
    Font(R.font.giga_sans_regular_400, FontWeight.W400),
    Font(R.font.giga_sans_light_300, FontWeight.W300)
)
val typography
    @Composable get() =
        ZdravnicaAppTypography(
            headH1 = TextStyle(
                fontSize = 80.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W800,
                lineHeight = 86.sp
            ),
            headH2 = TextStyle(
                fontSize = 42.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W800,
                lineHeight = 48.sp
            ),
            headH3 = TextStyle(
                fontSize = 34.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W800,
                lineHeight = 38.sp
            ),
            headH4 = TextStyle(
                fontSize = 28.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W800,
                lineHeight = 32.sp
            ),
            headH5 = TextStyle(
                fontSize = 24.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W800,
                lineHeight = 28.sp
            ),
            bodyXLBold = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 24.sp
            ),
            bodyXLSemi = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 24.sp
            ),
            bodyXLMedium = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 24.sp
            ),
            bodyXLReg = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 24.sp
            ),
            bodyXLThin = TextStyle(
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 24.sp
            ),
            bodyLargeBold = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 22.sp
            ),
            bodyLargeSemi = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 22.sp
            ),
            bodyLargeMedium = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 22.sp
            ),
            bodyLargeRegular = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 22.sp
            ),
            bodyLargeThin = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 22.sp
            ),
            bodyMediumBold = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 20.sp
            ),
            bodyMediumSemi = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 20.sp
            ),
            bodyMediumMedium = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 20.sp
            ),
            bodyMediumRegular = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 20.sp
            ),
            bodyMediumThin = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 20.sp
            ),
            bodyNormalBold = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 18.sp
            ),
            bodyNormalSemi = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 18.sp
            ),
            bodyNormalMedium = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 18.sp
            ),
            bodyNormalRegular = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp
            ),
            bodyNormalThin = TextStyle(
                fontSize = 14.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 18.sp
            ),
            bodySmallBold = TextStyle(
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 14.sp
            ),
            bodySmallSemi = TextStyle(
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 14.sp
            ),
            bodySmallMedium = TextStyle(
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 14.sp
            ),
            bodySmallRegular = TextStyle(
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 14.sp
            ),
            bodySmallThin = TextStyle(
                fontSize = 12.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 14.sp
            ),
            bodyXSBold = TextStyle(
                fontSize = 10.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W700,
                lineHeight = 12.sp
            ),
            bodyXSSemi = TextStyle(
                fontSize = 10.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                lineHeight = 12.sp
            ),
            bodyXSMedium = TextStyle(
                fontSize = 10.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W500,
                lineHeight = 12.sp
            ),
            bodyXSRegular = TextStyle(
                fontSize = 10.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                lineHeight = 12.sp
            ),
            bodyXSThin = TextStyle(
                fontSize = 10.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W300,
                lineHeight = 12.sp
            )
        )