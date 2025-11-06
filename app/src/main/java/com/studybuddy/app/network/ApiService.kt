package com.studybuddy.app.network

import com.studybuddy.app.db.NoteDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/user/profile")
    suspend fun getProfile(@Header("Authorization") bearer: String): RemoteProfileResponse

    @POST("api/notes/sync")
    suspend fun syncNotes(@Header("Authorization") bearer: String, @Body payload: List<NoteDto>): SyncResponse
}

data class RemoteProfileResponse(val uid: String, val email: String?, val displayName: String?)
data class SyncResponse(val success: Boolean, val syncedCount: Int)
