package com.studybuddy.app.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.studybuddy.app.util.LanguageViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    languageViewModel: LanguageViewModel? = null // optional, can be null
) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Language")
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            // English button
            Button(onClick = {
                languageViewModel?.switchLanguage("en") ?: run {
                    val act = context as? Activity ?: return@Button
                    LocaleManager.setLocale(act, "en")
                }
            }) { Text("English") }

            // Afrikaans button
            Button(onClick = {
                languageViewModel?.switchLanguage("af") ?: run {
                    val act = context as? Activity ?: return@Button
                    LocaleManager.setLocale(act, "af")
                }
            }) { Text("Afrikaans") }

            // isiZulu button
            Button(onClick = {
                languageViewModel?.switchLanguage("zu") ?: run {
                    val act = context as? Activity ?: return@Button
                    LocaleManager.setLocale(act, "zu")
                }
            }) { Text("isiZulu") }

        }
    }
}
