package com.studybuddy.app.reminders

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RemindersViewModel(private val context: Context) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val reminderDao = AppDatabase.getInstance(context).reminderDao()
    private val _reminders = MutableStateFlow<List<ReminderEntity>>(emptyList())
    val reminders = _reminders

    init {
        val uid = auth.currentUser?.uid ?: ""
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                reminderDao.getRemindersForUser(uid).collect { list ->
                    _reminders.value = list
                }
            }
        }
    }

    fun addReminder(title: String, epoch: Long) {
        val uid = auth.currentUser?.uid ?: "anon"
        val reminder = ReminderEntity(userId = uid, title = title, timeEpoch = epoch)
        viewModelScope.launch { reminderDao.insert(reminder)
            ReminderScheduler.scheduleReminder(context, reminder)
        }
    }
    fun delete(reminder: ReminderEntity) {
        viewModelScope.launch {
            reminderDao.delete(reminder)
        }
    }
}
