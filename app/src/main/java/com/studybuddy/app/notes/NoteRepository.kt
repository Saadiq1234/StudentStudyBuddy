package com.studybuddy.app.notes

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NoteRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val noteDao = db.noteDao()
    private val firestore = FirebaseFirestore.getInstance()

    fun getNotesFlow(userId: String) = noteDao.getNotesForUser(userId)

    suspend fun insertLocal(note: NoteEntity) {
        noteDao.insert(note)
    }

    suspend fun syncUnsynced() {
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
                // handle or log; leave unsynced for next attempt
            }
        }
    }

    suspend fun uploadNote(note: NoteEntity) {
        // upload single note and mark synced
        val data = mapOf(
            "userId" to note.userId,
            "title" to note.title,
            "content" to note.content,
            "timestamp" to note.timestamp
        )
        firestore.collection("notes").document(note.id).set(data).await()
        noteDao.update(note.copy(synced = true))
    }
}
