package edu.project.dlearn.presentation.theme

import android.app.Activity
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

private val LightColors = lightColorScheme(
    primary = Primary40,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary40,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary40,
    onTertiary = OnTertiary,
    error = Error40,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    outlineVariant = OutlineVariant
)

private val DarkColors = darkColorScheme(
    primary = Primary80,
    onPrimary = OnPrimaryContainer,
    primaryContainer = Primary40,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary80,
    onSecondary = OnSecondaryContainer,
    secondaryContainer = Secondary40,
    onSecondaryContainer = OnSecondary,
    tertiary = Tertiary80,
    onTertiary = OnTertiaryContainer,
    tertiaryContainer = Tertiary40,
    onTertiaryContainer = OnTertiary,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Error40,
    onErrorContainer = Color.White,
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = BorderDark,
    outlineVariant = BorderDark
)

@Composable
fun LiteschreibTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Désactivé par défaut pour garder une identité visuelle fidèle aux maquettes Figma
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LiteschreibTypography,
        shapes = LiteschreibShapes,
        content = content
    )
}
