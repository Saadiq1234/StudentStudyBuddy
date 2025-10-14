package com.studybuddy.app.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NotesScreen(navController: NavController, viewModel: NotesViewModel) {
    val notes by viewModel.notes.collectAsState()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("note/new")
        }) { Text("+") } }) { padding ->
        Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            Text("Your Notes", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            if (notes.isEmpty()) {
                Text("No notes yet. Tap + to add one.")
            } else {
                LazyColumn {
                    items(notes) { note ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable {
                            navController.navigate("note/${note.id}")
                        }) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(note.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(6.dp))
                                Text(note.content.take(200))
                            }
                        }
                    }
                }
            }
        }
    }
}