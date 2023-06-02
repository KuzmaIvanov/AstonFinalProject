package com.example.astonfinalproject.domain.entities

import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterOrigin,
    val location: CharacterLocation,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("episode")
    val episodesUrlList: List<String>,
    @SerializedName("url")
    val characterUrl: String,
    val created: String
)

data class CharacterOrigin(
    val name: String,
    val url: String
)

data class CharacterLocation(
    val name: String,
    val url: String
)