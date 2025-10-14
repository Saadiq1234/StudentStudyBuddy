package com.studybuddy.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.ui.theme.StudyBuddyTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            StudyBuddyTheme {
                val navController = rememberNavController()
                val authVm = remember { AuthViewModel() }
                val notesVm = remember { NotesViewModel(applicationContext) }
                val remindersVm = remember { RemindersViewModel(applicationContext) }

                AppNavHost(
                    navController = navController,
                    authViewModel = authVm,
                    notesViewModel = notesVm,
                    remindersViewModel = remindersVm
                )
            }
        }
    }
}


