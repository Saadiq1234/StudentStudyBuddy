package com.studybuddy.app.storage

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class StorageRepository(private val storage: FirebaseStorage = FirebaseStorage.getInstance()) {

    private val rootRef = storage.reference

    suspend fun uploadFile(path: String, localFile: File): Result<String> {
        return try {
            val ref = rootRef.child(path)
            val task = ref.putFile(android.net.Uri.fromFile(localFile)).await()
            val url = ref.downloadUrl.await().toString()
            Result.success(url)
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun downloadFile(path: String, destination: File): Result<File> {
        return try {
            val ref = rootRef.child(path)
            ref.getFile(destination).await()
            Result.success(destination)
        } catch (e: Exception) { Result.failure(e) }
    }
}
