package com.studybuddy.app.auth

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.studybuddy.app.notes.NotesViewModel
import com.studybuddy.app.reminders.RemindersViewModel
import com.studybuddy.app.util.LanguageViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.PasswordVisualTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    languageViewModel: LanguageViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    // Notes & Reminders ViewModels
    val notesViewModel = remember { NotesViewModel(context) }
    val remindersViewModel = remember { RemindersViewModel(context) }

    // Google Sign-In setup
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("455747964921-4q1rmtifc3ji604japmp6av1qr1k9ehj.apps.googleusercontent.com")
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                loading = true
                authViewModel.signInWithGoogle(idToken) { ok, err ->
                    loading = false
                    if (ok) {
                        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                        notesViewModel.loadNotesForUser(uid)
                        remindersViewModel.loadRemindersForUser(uid)
                        scope.launch { snackbarHostState.showSnackbar("Google sign-in successful!") }
                        navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                    } else {
                        scope.launch { snackbarHostState.showSnackbar(err ?: "Google sign-in failed") }
                    }
                }
            }
        } catch (e: ApiException) {
            scope.launch { snackbarHostState.showSnackbar("Google sign-in error: ${e.statusCode}") }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Study Buddy", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (loading) return@Button
                    if (email.isBlank() || password.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar("Please fill in all fields") }
                        return@Button
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        scope.launch { snackbarHostState.showSnackbar("Enter a valid email") }
                        return@Button
                    }
                    if (password.length < 6) {
                        scope.launch { snackbarHostState.showSnackbar("Password must be at least 6 characters") }
                        return@Button
                    }

                    loading = true
                    authViewModel.login(email, password) { ok, err ->
                        loading = false
                        if (ok) {
                            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                            notesViewModel.loadNotesForUser(uid)
                            remindersViewModel.loadRemindersForUser(uid)
                            scope.launch { snackbarHostState.showSnackbar("Login successful!") }
                            navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                        } else {
                            scope.launch { snackbarHostState.showSnackbar(err ?: "Login failed") }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text(if (loading) "Logging in..." else "Login")
            }

            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = { launcher.launch(googleSignInClient.signInIntent) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Sign in with Google") }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = { navController.navigate("register") }) { Text("Create an account") }
        }
    }
}
