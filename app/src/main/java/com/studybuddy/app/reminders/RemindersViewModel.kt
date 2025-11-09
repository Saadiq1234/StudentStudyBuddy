package com.studybuddy.app.reminders

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studybuddy.app.data.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RemindersViewModel(private val context: Context) : ViewModel() {

    private val reminderDao = AppDatabase.getInstance(context).reminderDao()
    private val repository = ReminderRepository(reminderDao)

    private val _reminders = MutableStateFlow<List<ReminderEntity>>(emptyList())
    val reminders: StateFlow<List<ReminderEntity>> = _reminders

    private var userId: String? = null

    fun setUserId(uid: String) {
        userId = uid
        loadRemindersForUser(uid)
    }

    fun loadRemindersForUser(uid: String) {
        viewModelScope.launch {
            repository.getRemindersForUserFlow(uid).collectLatest {
                _reminders.value = it
            }
        }
    }

    fun addReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            repository.insert(reminder)
            userId?.let { loadRemindersForUser(it) } // refresh after adding
        }
    }

    fun deleteReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            repository.delete(reminder)
            userId?.let { loadRemindersForUser(it) } // refresh after deleting
        }
    }
}
