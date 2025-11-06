package com.studybuddy.app.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.studybuddy.app.R
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.notifications.NotificationHelper
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.ui.theme.StudyBuddyTheme
import com.studybuddy.app.util.LocaleHelper
import com.studybuddy.app.util.LanguageViewModel  // ✅ Added import

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("lang", "en") ?: "en"
        val context = LocaleHelper.setLocale(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase
        FirebaseApp.initializeApp(this)

        // ✅ Request notification permission (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        // ✅ Create notification channel
        NotificationHelper.createChannel(this)

        // ✅ Show test notification
        NotificationHelper.showNotification(
            this,
            title = getString(R.string.app_name),
            body = getString(R.string.login_title)  // Make sure you add this in strings.xml
        )

        // ✅ Compose UI
        setContent {
            StudyBuddyTheme {
                val navController = rememberNavController()
                val authVm = remember { AuthViewModel() }
                val notesVm = remember { NotesViewModel(applicationContext) }
                val remindersVm = remember { RemindersViewModel(applicationContext) }
                val languageVm = remember { LanguageViewModel() }  // ✅ Added

                AppNavHost(
                    navController = navController,
                    authViewModel = authVm,
                    notesViewModel = notesVm,
                    remindersViewModel = remindersVm,
                    languageViewModel = languageVm // ✅ Pass into AppNavHost
                )
            }
        }
    }
}
