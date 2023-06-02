package com.example.astonfinalproject.data.network

import com.example.astonfinalproject.domain.entities.Character
import com.example.astonfinalproject.domain.entities.Episode
import com.example.astonfinalproject.domain.entities.Location
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface DetailsService {
    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Character

    @GET
    suspend fun getSingleEpisodeByUrl(
        @Url url: String
    ): Episode

    @GET("location/{id}")
    suspend fun getSingleLocation(
        @Path("id") id: Int
    ): Location

    @GET
    suspend fun getSingleCharacterByUrl(
        @Url url: String
    ): Character

    @GET("episode/{id}")
    suspend fun getSingleEpisode(
        @Path("id") id: Int
    ): Episode
}