package com.studybuddy.app.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val userState = MutableStateFlow(firebaseAuth.currentUser)

    fun register(email: String, password: String, onResult: (Boolean, String?)->Unit) {
        viewModelScope.launch {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) userState.value = firebaseAuth.currentUser
                    onResult(task.isSuccessful, task.exception?.message)
                }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?)->Unit) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) userState.value = firebaseAuth.currentUser
                    onResult(task.isSuccessful, task.exception?.message)
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        userState.value = null
    }
}
