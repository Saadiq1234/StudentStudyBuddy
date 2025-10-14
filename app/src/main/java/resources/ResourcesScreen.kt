package com.studybuddy.app.resources

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ResourcesScreen(navController: NavController, notesViewModel: Any /* placeholder */) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<Doc>>(emptyList()) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search books/resources") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val resp = ApiClient.service.search(query)
                    results = resp.docs ?: emptyList()
                } catch (e: Exception) {
                    // handle
                }
            }
        }) {
            Text("Search")
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(results) { doc ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(doc.title ?: "No title", style = MaterialTheme.typography.titleMedium)
                        doc.author_name?.let { auths ->
                            Text(auths.joinToString(", "), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
