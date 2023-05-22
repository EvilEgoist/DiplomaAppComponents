package ru.diploma.appcomponents.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.diploma.appcomponents.core.R
import androidx.compose.material3.Typography

val albertSansFontFamily = FontFamily(
    Font(R.font.albert_sans_light, FontWeight.Light),
    Font(R.font.albert_sans_thin, FontWeight.Thin),
    Font(R.font.albert_sans_regular, FontWeight.Normal),
    Font(R.font.albert_sans_medium, FontWeight.Medium),
    Font(R.font.albert_sans_semibold, FontWeight.SemiBold),
    Font(R.font.albert_sans_extrabold, FontWeight.ExtraBold),
)

val figeronaFontFamily = FontFamily(
    Font(R.font.figerona)
)

val bakbakFontFamily = FontFamily(
    Font(R.font.bakbak_regular)
)


val Typography.lightHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.background
    )

val Typography.cardDate: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.background
    )

val Typography.mediumHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = figeronaFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.background
    )

val Typography.bigLightHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 26.sp,
        color = MaterialTheme.colorScheme.background
    )

val Typography.bigDarkHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = bakbakFontFamily,
        fontSize = 26.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )

val Typography.mediumDarkHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )

val Typography.darkRegularHeader: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )

val Typography.darkRegularText: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )

val Typography.hintTextStyle: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.outline
    )

val Typography.regularTextWithoutColor: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = albertSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )