package com.studybuddy.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.studybuddy.app.auth.LoginScreen
import com.studybuddy.app.auth.RegisterScreen
import com.studybuddy.app.dashboard.DashboardScreen
import com.studybuddy.app.notes.NotesScreen
import com.studybuddy.app.notes.NoteEditorScreen
import com.studybuddy.app.reminders.RemindersScreen
import com.studybuddy.app.resources.ResourcesScreen
import com.studybuddy.app.settings.SettingsScreen
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.reminders.RemindersViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel,
    remindersViewModel: RemindersViewModel
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("dashboard") { DashboardScreen(navController, notesViewModel, remindersViewModel) }
        composable("notes") { NotesScreen(navController, notesViewModel) }
        composable("note/{noteId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("noteId") ?: ""
            NoteEditorScreen(navController, notesViewModel, id)
        }
        composable("reminders") { RemindersScreen(navController, remindersViewModel) }
        composable("resources") { ResourcesScreen(navController, notesViewModel) }
        composable("settings") { SettingsScreen(navController) }
    }
}
