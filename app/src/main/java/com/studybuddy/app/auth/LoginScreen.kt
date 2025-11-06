package com.studybuddy.app.auth

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // ✅ Google Sign-In setup
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("455747964921-4q1rmtifc3ji604japmp6av1qr1k9ehj.apps.googleusercontent.com") // Web Client ID
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
                authViewModel.signInWithGoogle(idToken) { ok, err ->
                    if (ok) {
                        scope.launch { snackbarHost.showSnackbar("Signed in successfully!") }
                        navController.navigate("dashboard")
                    } else {
                        scope.launch { snackbarHost.showSnackbar(err ?: "Google sign-in failed") }
                    }
                }
            }
        } catch (e: ApiException) {
            scope.launch { snackbarHost.showSnackbar("Google sign-in error: ${e.statusCode}") }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHost) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Study Buddy", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))

            // ✅ LOGIN BUTTON with validation
            Button(
                onClick = {
                    // Empty fields check
                    if (email.isBlank() || password.isBlank()) {
                        scope.launch { snackbarHost.showSnackbar("Please fill in all fields") }
                        return@Button
                    }
                    // Email format check
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        scope.launch { snackbarHost.showSnackbar("Please enter a valid email") }
                        return@Button
                    }
                    // Password length check
                    if (password.length < 6) {
                        scope.launch { snackbarHost.showSnackbar("Password must be at least 6 characters") }
                        return@Button
                    }

                    loading = true
                    authViewModel.login(email, password) { ok, err ->
                        loading = false
                        if (ok) {
                            scope.launch { snackbarHost.showSnackbar("Login successful!") }
                            navController.navigate("dashboard")
                        } else {
                            scope.launch { snackbarHost.showSnackbar(err ?: "Login failed") }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text(if (loading) "Logging in..." else "Login")
            }

            Spacer(Modifier.height(16.dp))

            // ✅ GOOGLE SIGN-IN BUTTON
            OutlinedButton(
                onClick = {
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign in with Google")
            }

            Spacer(Modifier.height(16.dp))

            // ✅ NAVIGATE TO REGISTER
            TextButton(onClick = { navController.navigate("register") }) {
                Text("Create an account")
            }
        }
    }
}
