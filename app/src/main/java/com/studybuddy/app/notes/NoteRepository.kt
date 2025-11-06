package com.studybuddy.app.notes

import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao,
    private val userId: String
) {

    // Returns a Flow of notes for this user, ordered by timestamp
    fun getNotesFlow(): Flow<List<NoteEntity>> = noteDao.getNotesFlow(userId)

    suspend fun insert(note: NoteEntity) {
        noteDao.insert(note) // REPLACE strategy ensures updates are reflected
    }

    suspend fun delete(note: NoteEntity) {
        noteDao.delete(note)
    }

    suspend fun update(note: NoteEntity) {
        noteDao.update(note)
    }

    suspend fun getUnsyncedNotes(): List<NoteEntity> = noteDao.getUnsyncedNotes()
}
