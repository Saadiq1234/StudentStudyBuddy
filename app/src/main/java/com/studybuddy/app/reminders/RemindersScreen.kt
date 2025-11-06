package com.studybuddy.app.reminders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(navController: NavController, viewModel: RemindersViewModel) {
    val context = LocalContext.current
    val reminders by viewModel.reminders.collectAsState()  // Live reminders list
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTime by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (title.text.isNotBlank() && selectedTime != null) {
                    viewModel.addReminder(title.text, selectedTime!!)
                    title = TextFieldValue("")
                    selectedTime = null
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Reminder")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Input Section
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Reminder Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val cal = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                cal.set(year, month, day, hour, minute)
                                selectedTime = cal.timeInMillis
                            },
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text(
                    if (selectedTime != null)
                        "Selected: ${
                            SimpleDateFormat(
                                "yyyy-MM-dd HH:mm",
                                Locale.getDefault()
                            ).format(Date(selectedTime!!))
                        }"
                    else "Select Date & Time"
                )
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Reminders List
            if (reminders.isEmpty()) {
                Text("No reminders yet", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(reminders) { reminder ->
                        ReminderItem(reminder = reminder, onDelete = { viewModel.delete(it) })
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderItem(reminder: ReminderEntity, onDelete: (ReminderEntity) -> Unit) {
    val formattedTime = remember(reminder.timeEpoch) {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(reminder.timeEpoch))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(reminder.title, style = MaterialTheme.typography.titleMedium)
                Text(formattedTime, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onDelete(reminder) }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Reminder")
            }
        }
    }
}
