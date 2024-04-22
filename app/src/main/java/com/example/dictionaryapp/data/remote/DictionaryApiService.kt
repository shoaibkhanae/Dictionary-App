package com.example.dictionaryapp.data.remote

import com.example.dictionaryapp.data.model.Definition
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("entries/en/{word}")
    suspend fun getDefinition(
        @Path("word") word: String
    ): Response<Definition>
}