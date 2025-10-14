package com.studybuddy.app.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Text("Language")
        Spacer(Modifier.height(8.dp))
        Row {
            Button(onClick = {
                val act = (LocalContext.current as? Activity) ?: return@Button
                LocaleManager.setLocale(act, "en")
            }) { Text("English") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val act = (LocalContext.current as? Activity) ?: return@Button
                LocaleManager.setLocale(act, "zu")
            }) { Text("isiZulu") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val act = (LocalContext.current as? Activity) ?: return@Button
                LocaleManager.setLocale(act, "af")
            }) { Text("Afrikaans") }
        }
    }
}
