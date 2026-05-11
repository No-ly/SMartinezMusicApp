package com.example.smartinezmusicapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MusicLightScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = PurpleLight,
    onPrimaryContainer = PurpleDark,
    secondary = PurpleMedium,
    onSecondary = Color.White,
    tertiary = PurpleGradientStart,
    background = LightBackground,
    onBackground = TextDark,
    surface = WhiteCard,
    onSurface = TextDark,
    surfaceVariant = PurpleSurface,
    onSurfaceVariant = TextMedium,
    outline = TextLight
)

@Composable
fun SMartinezMusicAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MusicLightScheme,
        typography = Typography,
        content = content
    )
}
