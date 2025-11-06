package com.studybuddy.app.auth

import android.app.DatePickerDialog
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
import java.util.*

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHost = remember { SnackbarHostState() }
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    // ✅ Google Sign-In setup
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

    // ✅ Date picker for DOB
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            dob = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(snackbarHost = { SnackbarHost(snackbarHost) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create an Account", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            // ✅ Name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // ✅ Surname field
            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Surname") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // ✅ Date of Birth
            OutlinedTextField(
                value = dob,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePicker.show() },
                readOnly = true
            )

            Spacer(Modifier.height(12.dp))

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

            // ✅ CREATE ACCOUNT BUTTON with validation
            Button(
                onClick = {
                    when {
                        name.isBlank() || surname.isBlank() || dob.isBlank() ||
                                email.isBlank() || password.isBlank() -> {
                            scope.launch { snackbarHost.showSnackbar("Please fill in all fields") }
                            return@Button
                        }

                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            scope.launch { snackbarHost.showSnackbar("Please enter a valid email") }
                            return@Button
                        }

                        password.length < 6 -> {
                            scope.launch { snackbarHost.showSnackbar("Password must be at least 6 characters") }
                            return@Button
                        }

                        else -> {
                            loading = true
                            authViewModel.register(email, password) { ok, err ->
                                loading = false
                                if (ok) {
                                    // ✅ You could also save name/surname/dob in Firestore here
                                    scope.launch { snackbarHost.showSnackbar("Account created successfully!") }
                                    navController.navigate("dashboard")
                                } else {
                                    scope.launch { snackbarHost.showSnackbar(err ?: "Registration failed") }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text(if (loading) "Creating..." else "Create Account")
            }

            Spacer(Modifier.height(16.dp))

            // ✅ SIGN IN WITH GOOGLE BUTTON
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

            // ✅ BACK TO LOGIN
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Back to Login")
            }
        }
    }
}
