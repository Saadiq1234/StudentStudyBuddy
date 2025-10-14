package com.studybuddy.app.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesViewModel(private val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val repository = NoteRepository(context)
    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                repository.getNotesFlow(uid).collectLatest { list ->
                    _notes.value = list
                }
            }
        }
    }

    fun createNote(title: String, content: String) {
        val uid = auth.currentUser?.uid ?: "anon"
        val note = NoteEntity(userId = uid, title = title, content = content)
        viewModelScope.launch {
            repository.insertLocal(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch { repository.insertLocal(note) }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch { AppDatabase.getInstance(context).noteDao().delete(note) }
    }

    fun manualSync() {
        viewModelScope.launch { repository.syncUnsynced() }
    }
}
