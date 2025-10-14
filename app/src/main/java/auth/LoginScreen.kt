package com.studybuddy.app.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHost) }) { padding ->
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text("Study Buddy", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))
            Button(onClick = { authViewModel.login(email, password) { ok, err -> if (ok) navController.navigate("dashboard") else scope.launch { snackbarHost.showSnackbar(err ?: "Login failed") }
            }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Create an account")
        }
    }
    Spacer(Modifier.height(12.dp))
// Google SSO: placeholder button. Implement GoogleSignIn flow separately.
    OutlinedButton(onClick = { /* TODO: Google SignIn */ }, modifier = Modifier.fillMaxWidth()) {
        Text("Sign in with Google")
    }
}
