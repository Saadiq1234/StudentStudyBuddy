package com.studybuddy.app.resources

import retrofit2.http.GET
import retrofit2.http.Query

data class Doc(val title: String?, val author_name: List<String>?, val key: String?)
data class OpenLibraryResponse(val docs: List<Doc>?)

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun search(@Query("q") query: String): OpenLibraryResponse
}