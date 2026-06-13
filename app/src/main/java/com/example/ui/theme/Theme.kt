package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = HighDensityPrimary,
    onPrimary = HighDensityOnPrimary,
    primaryContainer = HighDensityPrimaryContainer,
    onPrimaryContainer = HighDensityOnPrimaryContainer,
    secondary = HighDensitySecondary,
    onSecondary = HighDensityOnSecondary,
    secondaryContainer = HighDensitySecondaryContainer,
    onSecondaryContainer = HighDensityOnSecondaryContainer,
    tertiary = HighDensityTertiary,
    onTertiary = HighDensityOnTertiary,
    tertiaryContainer = HighDensityTertiaryContainer,
    onTertiaryContainer = HighDensityOnTertiaryContainer,
    background = Color(0xFF170E35), // Brighter, rich deep violet-purple base
    onBackground = Color(0xFFFFFFFF), // High contrast white
    surface = Color(0xFF28185C), // Colorful deep royal purple surface
    onSurface = Color(0xFFFFFFFF), // High contrast white
    surfaceVariant = Color(0xFF3B287B), // Lighter purple variant
    onSurfaceVariant = Color(0xFFEAE5FF),
    error = HighDensityErr,
    onError = HighDensityOnErr,
    errorContainer = HighDensityErrContainer,
    onErrorContainer = HighDensityOnErrContainer,
    outline = HighDensityOutline
  )

private val LightColorScheme =
  lightColorScheme(
    primary = HighDensityPrimary,
    onPrimary = HighDensityOnPrimary,
    primaryContainer = HighDensityPrimaryContainer,
    onPrimaryContainer = HighDensityOnPrimaryContainer,
    secondary = HighDensitySecondary,
    onSecondary = HighDensityOnSecondary,
    secondaryContainer = HighDensitySecondaryContainer,
    onSecondaryContainer = HighDensityOnSecondaryContainer,
    tertiary = HighDensityTertiary,
    onTertiary = HighDensityOnTertiary,
    tertiaryContainer = HighDensityTertiaryContainer,
    onTertiaryContainer = HighDensityOnTertiaryContainer,
    background = HighDensityBg,
    onBackground = HighDensityOnBg,
    surface = Color(0xFFFFFFFF),
    onSurface = HighDensityOnBg,
    surfaceVariant = HighDensitySurfaceVariant,
    onSurfaceVariant = HighDensityOnSurfaceVariant,
    error = HighDensityErr,
    onError = HighDensityOnErr,
    errorContainer = HighDensityErrContainer,
    onErrorContainer = HighDensityOnErrContainer,
    outline = HighDensityOutline
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
