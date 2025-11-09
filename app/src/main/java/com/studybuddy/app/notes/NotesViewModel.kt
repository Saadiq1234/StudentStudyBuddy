package com.studybuddy.app.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.studybuddy.app.data.AppDatabase

class NotesViewModel(private val context: Context) : ViewModel() {

    private val noteDao = AppDatabase.getInstance(context).noteDao()
    private val repository = NoteRepository(noteDao)

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    private var userId: String? = null

    fun setUserId(uid: String) {
        userId = uid
        loadNotesForUser(uid)
    }

    fun loadNotesForUser(uid: String) {
        viewModelScope.launch {
            repository.getNotesForUserFlow(uid).collectLatest {
                _notes.value = it
            }
        }
    }

    fun addNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.insert(note)
            userId?.let { loadNotesForUser(it) } // refresh after adding
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
            userId?.let { loadNotesForUser(it) } // refresh after deleting
        }
    }
}
