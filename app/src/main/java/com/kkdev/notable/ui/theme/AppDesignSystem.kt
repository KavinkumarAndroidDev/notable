package com.kkdev.notable.ui.theme


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
    val error: Color,
    val onError: Color

)

data class AppTypography(
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val body:TextStyle,
    val labelLarge: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle,
    val alertTitle: TextStyle,
    val alertText: TextStyle,
    val noteTitle: TextStyle,
    val noteContent: TextStyle
)

data class AppShape(
    val container: Shape,
    val button: Shape

)

data class AppSize(
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        error =Color.Unspecified,
        onError = Color.Unspecified,
    )
}
val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal =TextStyle.Default,
        body =TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default,
        alertTitle = TextStyle.Default,
        alertText = TextStyle.Default,
        noteTitle = TextStyle.Default,
        noteContent = TextStyle.Default
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified
    )
}

