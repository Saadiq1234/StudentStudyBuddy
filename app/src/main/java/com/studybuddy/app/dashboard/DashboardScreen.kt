package com.studybuddy.app.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.R
import com.studybuddy.app.util.LanguageViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    remindersViewModel: RemindersViewModel,
    languageViewModel: LanguageViewModel,
    authViewModel: AuthViewModel
) {
    val userId = authViewModel.userState.value?.uid ?: return

    LaunchedEffect(userId) {
        notesViewModel.setUserId(userId)
        remindersViewModel.setUserId(userId)
    }

    val notes by notesViewModel.notes.collectAsState()
    val reminders by remindersViewModel.reminders.collectAsState()
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dashboard_title)) },
                actions = {
                    TextButton(onClick = {
                        authViewModel.signOut()
                        navController.navigate("login") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    }) {
                        Text(stringResource(R.string.login))
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notes section
            item {
                Text(stringResource(R.string.your_notes), style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (notes.isEmpty()) {
                item { Text(stringResource(R.string.no_notes)) }
            } else {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("note/${note.id}") },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = note.content.take(100) + if (note.content.length > 100) "..." else "",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Last updated: ${dateFormatter.format(Date(note.timestamp))}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            // Reminders section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.your_reminders), style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (reminders.isEmpty()) {
                item { Text(stringResource(R.string.no_reminders)) }
            } else {
                items(reminders) { reminder ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(reminder.title, style = MaterialTheme.typography.titleMedium)
                            Text(timeFormatter.format(Date(reminder.timeEpoch)), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Add Note / Reminder buttons
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { navController.navigate("notes") }) {
                        Text(stringResource(R.string.add_note))
                    }
                    Button(onClick = { navController.navigate("reminders") }) {
                        Text(stringResource(R.string.add_reminder))
                    }
                }
            }
        }
    }
}
