package com.studybuddy.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.auth.LoginScreen
import com.studybuddy.app.auth.RegisterScreen
import com.studybuddy.app.dashboard.DashboardScreen
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.notes.NotesScreen
import com.studybuddy.app.notes.NoteEditorScreen
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.reminders.RemindersScreen
import com.studybuddy.app.resources.ResourcesScreen
import com.studybuddy.app.settings.SettingsScreen
import com.studybuddy.app.util.LanguageViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel,
    remindersViewModel: RemindersViewModel,
    languageViewModel: LanguageViewModel
) {
    NavHost(navController = navController, startDestination = "login") {

        // Login screen
        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel,
                languageViewModel = languageViewModel
            )
        }

        // Register screen
        composable("register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel,
                languageViewModel = languageViewModel
            )
        }

        // Dashboard screen
        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                notesViewModel = notesViewModel,
                remindersViewModel = remindersViewModel,
                languageViewModel = languageViewModel,
                authViewModel = authViewModel
            )
        }

        // Notes list screen
        composable("notes") {
            NotesScreen(
                navController = navController,
                notesViewModel = notesViewModel
            )
        }

        // Note editor screen
        composable("note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            NoteEditorScreen(
                navController = navController,
                notesViewModel = notesViewModel,
                authViewModel = authViewModel, // <--- fixed
                noteId = noteId
            )
        }

        // Reminders screen
        composable("reminders") {
            RemindersScreen(
                navController = navController,
                remindersViewModel = remindersViewModel,
                authViewModel = authViewModel, // <--- fixed if needed
                languageViewModel = languageViewModel // optional if used
            )
        }

        // Resources screen
        composable("resources") {
            ResourcesScreen(
                navController = navController,
                languageViewModel = languageViewModel
            )
        }

        // Settings screen
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}
