package com.studybuddy.app.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.studybuddy.app.notes.AppDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val noteDao = db.noteDao()
        val firestore = FirebaseFirestore.getInstance()
        val unsynced = noteDao.getUnsyncedNotes()
        for (note in unsynced) {
            try {
                val data = mapOf(
                    "userId" to note.userId,
                    "title" to note.title,
                    "content" to note.content,
                    "timestamp" to note.timestamp
                )
                firestore.collection("notes").document(note.id).set(data).await()
                noteDao.update(note.copy(synced = true))
            } catch (e: Exception) {
            // log and continue
            }
        }
        return Result.success()
    }
}
