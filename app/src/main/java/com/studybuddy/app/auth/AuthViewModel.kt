package com.studybuddy.app.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow

// Make sure you define a UserEntity and UserRepository in your project
data class UserEntity(val uid: String, val firstName: String, val surname: String, val email: String)

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val userState = MutableStateFlow(firebaseAuth.currentUser)

    // Access the current user safely
    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    /** Email/password registration */
    fun register(
        email: String,
        password: String,
        firstName: String,
        surname: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid ?: ""
                    // Save additional user info in your database
                    val user = UserEntity(uid, firstName, surname, email)
                    // TODO: Insert user into your database, e.g., UserRepository.insert(user)
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    /** Email/password login */
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onResult(false, "Email and password cannot be empty")
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userState.value = firebaseAuth.currentUser
                    Log.d("AuthViewModel", "✅ Login success: ${firebaseAuth.currentUser?.uid}")
                    onResult(true, null)
                } else {
                    val error = task.exception?.localizedMessage ?: "Login failed"
                    Log.e("AuthViewModel", "❌ Login error: $error")
                    onResult(false, error)
                }
            }
    }

    /** Google Sign-In */
    fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userState.value = firebaseAuth.currentUser
                    Log.d("AuthViewModel", "✅ Google Sign-In success: ${firebaseAuth.currentUser?.uid}")
                    onResult(true, null)
                } else {
                    val error = task.exception?.localizedMessage ?: "Google sign-in failed"
                    Log.e("AuthViewModel", "❌ Google Sign-In error: $error")
                    onResult(false, error)
                }
            }
    }

    /** Sign out */
    fun signOut() {
        firebaseAuth.signOut()
        userState.value = null
        Log.d("AuthViewModel", "👋 User signed out")
    }
}
