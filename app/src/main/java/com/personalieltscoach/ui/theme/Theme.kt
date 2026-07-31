package com.personalieltscoach.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val LightColors = lightColorScheme(
    primary = Color(0xFF006A70),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA4F0F2),
    onPrimaryContainer = Color(0xFF002022),
    secondary = Color(0xFF4A6264),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCDE7E8),
    onSecondaryContainer = Color(0xFF051F21),
    tertiary = Color(0xFF7B5736),
    tertiaryContainer = Color(0xFFFFDDBB),
    background = Color(0xFFF5FAF9),
    onBackground = Color(0xFF171D1D),
    surface = Color(0xFFFCFDFC),
    onSurface = Color(0xFF171D1D),
    surfaceVariant = Color(0xFFDAE5E4),
    onSurfaceVariant = Color(0xFF3F4949),
    outline = Color(0xFF6F7979),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF82D4D7),
    onPrimary = Color(0xFF003739),
    primaryContainer = Color(0xFF004F53),
    onPrimaryContainer = Color(0xFFA4F0F2),
    secondary = Color(0xFFB1CBCC),
    secondaryContainer = Color(0xFF334B4C),
    tertiary = Color(0xFFE9BF94),
    tertiaryContainer = Color(0xFF60401F),
    background = Color(0xFF0E1515),
    surface = Color(0xFF111818),
    surfaceVariant = Color(0xFF3F4949),
    errorContainer = Color(0xFF93000A)
)

private val CoachTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 23.sp,
        lineHeight = 29.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 21.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

private val CoachShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(30.dp)
)

@Composable
fun CoachTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = CoachTypography,
        shapes = CoachShapes,
        content = content
    )
}
