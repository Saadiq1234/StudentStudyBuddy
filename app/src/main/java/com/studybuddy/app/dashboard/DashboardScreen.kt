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
import java.text.SimpleDateFormat
import java.util.*

data class Reminder(
    val id: String,
    val title: String,
    val timeEpoch: Long
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    notesViewModel: NotesViewModel,
    remindersViewModel: RemindersViewModel
) {
    val notes by notesViewModel.notes.collectAsState(initial = emptyList())
    val reminders by remindersViewModel.reminders.collectAsState(initial = emptyList())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

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
            item { Text(stringResource(R.string.your_notes), style = MaterialTheme.typography.headlineSmall) }
            if (notes.isEmpty()) {
                item { Text(stringResource(R.string.no_notes)) }
            } else {
                items(notes) { note ->
                    Text(
                        text = note.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { navController.navigate("note/${note.id}") }
                    )
                    Divider()
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Text(stringResource(R.string.your_reminders), style = MaterialTheme.typography.headlineSmall) }
            if (reminders.isEmpty()) {
                item { Text(stringResource(R.string.no_reminders)) }
            } else {
                items(reminders) { reminder ->
                    val formattedTime = timeFormatter.format(Date(reminder.timeEpoch))
                    Text(
                        text = "${reminder.title} - $formattedTime",
                        modifier = Modifier.fillMaxWidth().padding(4.dp)
                    )
                    Divider()
                }
            }

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
