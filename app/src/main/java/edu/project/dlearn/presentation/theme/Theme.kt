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
import androidx.compose.ui.platform.LocalContext
import edu.project.dlearn.ui.theme.Typography



// TODO Mission A1 : remplacer par les tokens exacts extraits du Figma Dev Mode
private val LightColors = lightColorScheme()
private val DarkColors = darkColorScheme()

@Composable
fun LiteschreibIkiiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,   // toujours false — cf. principe design system
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}