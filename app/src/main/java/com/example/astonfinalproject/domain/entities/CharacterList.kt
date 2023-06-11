package com.example.astonfinalproject.domain.entities

import com.google.gson.annotations.SerializedName

data class CharacterList(
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)