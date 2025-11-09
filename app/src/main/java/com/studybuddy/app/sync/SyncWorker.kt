package com.studybuddy.app.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.studybuddy.app.data.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val noteDao = db.noteDao()
        val firestore = FirebaseFirestore.getInstance()

        // ✅ Get current user's ID
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return Result.failure()

        // ✅ Fetch only unsynced notes for this user
        val unsynced = noteDao.getUnsyncedNotes(uid)

        for (note in unsynced) {
            try {
                val data = mapOf(
                    "userId" to note.userId,
                    "title" to note.title,
                    "content" to note.content,
                    "timestamp" to note.timestamp
                )
                firestore.collection("notes").document(note.id).set(data).await()

                // ✅ Mark as synced
                noteDao.update(note.copy(synced = true))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return Result.success()
    }
}
