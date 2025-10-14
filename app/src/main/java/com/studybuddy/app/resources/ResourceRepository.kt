package com.studybuddy.app.resources

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val service: OpenLibraryApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApi::class.java)
    }
}

