package com.kkdev.notable.ui.theme


import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val lightColorScheme = AppColorScheme(
    primary = appWhite, // Text color
    onPrimary = appBlack, // Text color on primary background
    surface = appWhite, // Background color
    onSurface = appBlack, // Text color on surface background
    background = appWhite, // Main background color
    onBackground = appBlack, // Text color on main background
    error = appLightblack, // Button color
    onError = appWhite // Text color on buttons
)

private val darkColorScheme = AppColorScheme(
    primary = appWhite, // Text color
    onPrimary = appBlack, // Text color on primary background
    surface = appWhite, // Background color
    onSurface = appBlack, // Text color on surface background
    background = appWhite, // Main background color
    onBackground = appBlack, // Text color on main background
    error = appLightblack, // Button color
    onError = appWhite // Text color on buttons
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp
    ),
    titleNormal = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp
    ),
    body = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    labelNormal = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    alertTitle = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    alertText = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    noteTitle = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    noteContent = TextStyle(
        fontFamily = urbanistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )
)

private val shape = AppShape(
    button = RoundedCornerShape(50.dp),
    container = RoundedCornerShape(14.dp)
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple()

    CompositionLocalProvider (
        LocalAppColorScheme provides colorScheme,
        LocalAppShape provides shape,
        LocalAppTypography provides typography,
        LocalAppSize provides size,
        LocalIndication provides rippleIndication,
        content = content
    )
}


object AppTheme{
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape:AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}