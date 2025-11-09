package com.studybuddy.app.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.studybuddy.app.auth.AuthViewModel
import com.studybuddy.app.util.LanguageViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    remindersViewModel: RemindersViewModel,
    authViewModel: AuthViewModel,
    languageViewModel: LanguageViewModel
) {
    val userId = authViewModel.userState.value?.uid ?: return
    LaunchedEffect(userId) {
        remindersViewModel.setUserId(userId) // Tie reminders to current user
    }

    val reminders by remindersViewModel.reminders.collectAsState()
    var title by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Reminders", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Display existing reminders
        reminders.forEach { reminder ->
            val calendar = Calendar.getInstance().apply { timeInMillis = reminder.timeEpoch }
            val h = calendar.get(Calendar.HOUR_OF_DAY)
            val m = calendar.get(Calendar.MINUTE)
            Text("- ${reminder.title} at %02d:%02d".format(h, m))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add new reminder form
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Reminder Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = hour,
                onValueChange = { hour = it },
                label = { Text("Hour (0-23)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = minute,
                onValueChange = { minute = it },
                label = { Text("Minute (0-59)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val h = hour.toIntOrNull() ?: 0
                val m = minute.toIntOrNull() ?: 0

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, h)
                    set(Calendar.MINUTE, m)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val newReminder = ReminderEntity(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    title = title,
                    timeEpoch = calendar.timeInMillis
                )

                remindersViewModel.addReminder(newReminder)

                // Clear input fields
                title = ""
                hour = ""
                minute = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Reminder")
        }
    }
}
