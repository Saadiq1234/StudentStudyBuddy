package com.studybuddy.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun LanguageAwareApp(
    languageViewModel: LanguageViewModel,
    content: @Composable (context: android.content.Context) -> Unit
) {
    val language by languageViewModel.language.collectAsState()
    val context = LocalContext.current
    val localizedContext = remember(language) {
        LanguageManager.setLocale(context, language)
    }

    content(localizedContext)
}
