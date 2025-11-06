package com.studybuddy.app.db

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class NoteDto(
    val id: String = "",
    val title: String = "",
    val body: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

class FirestoreRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    private val notesRef get() = db.collection("notes")

    suspend fun createOrUpdateNote(note: NoteDto): Result<Void?> {
        return try {
            notesRef.document(note.id).set(note).await()
            Result.success(null)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun getNote(noteId: String): Result<NoteDto> {
        return try {
            val doc = notesRef.document(noteId).get().await()
            val note = doc.toObject(NoteDto::class.java) ?: throw Exception("Not found")
            Result.success(note)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun getAllNotes(): Result<List<NoteDto>> {
        return try {
            val snapshot = notesRef.get().await()
            val list = snapshot.documents.mapNotNull { it.toObject(NoteDto::class.java) }
            Result.success(list)
        } catch (e: Exception) { Result.failure(e) }
    }
}
