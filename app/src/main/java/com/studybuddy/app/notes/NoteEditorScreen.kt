package com.studybuddy.app.notes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun NoteEditorScreen(navController: NavController, viewModel: NotesViewModel, noteId: String) {
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
// If editing existing note, populate fields (simplified: lookup from current list)
val existing = viewModel.notes.collectAsState().value.find { it.id == noteId }
 LaunchedEffect(existing) {
existing?.let { title = it.title; content = it.content }
 }
    Scaffold(topBar = {
        TopAppBar(title = { Text(if (noteId == "new" || noteId.isEmpty()) "New Note" else "Edit Note") })
                      }, floatingActionButton = {
                          FloatingActionButton(onClick = {
                              if (noteId == "new" || noteId.isEmpty()) {
                                  viewModel.createNote(title, content)
                              } else {
                                  existing?.let {
                                      viewModel.updateNote(it.copy(title = title, content = content))
                                  }
                              }
                              navController.popBackStack()
                          }) { Text("Save") }
                      }) { padding ->
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.fillMaxWidth().height(300.dp))
        }
    }
}
