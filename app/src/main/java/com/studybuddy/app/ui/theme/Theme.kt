package com.studybuddy.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0D47A1), // deep blue
    onPrimary = Color.White,
    secondary = Color(0xFF2E7D32), // green
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF6F9FB),
    onSurface = Color(0xFF111827)
)

@Composable
fun StudyBuddyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
