package com.studybuddy.app.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.ui.theme.StudyBuddyTheme
import com.studybuddy.app.util.LanguageViewModel
import com.studybuddy.app.util.LocaleHelper
import com.studybuddy.app.notifications.NotificationHelper

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("lang", "en") ?: "en"
        val context = LocaleHelper.setLocale(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Request notifications permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        // Create notification channel
        NotificationHelper.createChannel(this)

        setContent {
            StudyBuddyTheme {
                val navController = rememberNavController()

                // Initialize ViewModels
                val authViewModel = remember { AuthViewModel() }
                val notesViewModel = remember { NotesViewModel(applicationContext) }
                val remindersViewModel = remember { RemindersViewModel(applicationContext) }
                val languageViewModel = remember { LanguageViewModel() }

                // Pass all ViewModels to AppNavHost
                AppNavHost(
                    navController = navController,
                    authViewModel = authViewModel,
                    notesViewModel = notesViewModel,
                    remindersViewModel = remindersViewModel,
                    languageViewModel = languageViewModel
                )
            }
        }
    }
}
