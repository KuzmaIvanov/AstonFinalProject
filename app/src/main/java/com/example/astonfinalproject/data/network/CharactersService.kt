package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.domain.entities.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {
    @GET("character")
    suspend fun getCharactersByPage(
        @Query("page") page: Int
    ): CharacterListResponse

    @GET("character")
    suspend fun filterCharacters(
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("gender") gender: String?
    ): CharacterListResponse
}