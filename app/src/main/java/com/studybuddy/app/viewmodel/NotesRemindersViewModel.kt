package com.studybuddy.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.studybuddy.app.data.AppDatabase
import com.studybuddy.app.notes.NoteEntity
import com.studybuddy.app.notes.NoteRepository
import com.studybuddy.app.reminders.ReminderEntity
import com.studybuddy.app.reminders.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class NotesViewModel(context: Context) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid ?: "anon"

    private val noteDao = AppDatabase.getInstance(context).noteDao()
    private val repository = NoteRepository(noteDao, uid)

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        viewModelScope.launch {
            repository.getNotesFlow().collectLatest { _notes.value = it }
        }
    }

    fun addNote(title: String, content: String) {
        val note = NoteEntity(
            id = UUID.randomUUID().toString(),
            userId = uid,
            title = title,
            content = content,
            timestamp = System.currentTimeMillis(),
            synced = false
        )
        viewModelScope.launch { repository.insert(note) }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch { repository.delete(note) }
    }
}

class RemindersViewModel(context: Context) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid ?: "anon"

    private val reminderDao = AppDatabase.getInstance(context).reminderDao()
    private val repository = ReminderRepository(reminderDao, uid)

    private val _reminders = MutableStateFlow<List<ReminderEntity>>(emptyList())
    val reminders: StateFlow<List<ReminderEntity>> = _reminders

    init {
        viewModelScope.launch {
            repository.getRemindersFlow().collectLatest { _reminders.value = it }
        }
    }

    fun addReminder(title: String, epoch: Long) {
        val reminder = ReminderEntity(
            id = System.currentTimeMillis().toString(),
            userId = uid,
            title = title,
            timeEpoch = epoch
        )
        viewModelScope.launch { repository.insert(reminder) }
    }

    fun deleteReminder(reminder: ReminderEntity) {
        viewModelScope.launch { repository.delete(reminder) }
    }
}
