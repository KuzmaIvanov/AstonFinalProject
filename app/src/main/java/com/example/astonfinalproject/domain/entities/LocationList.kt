package com.example.astonfinalproject.domain.entities

import com.google.gson.annotations.SerializedName

data class LocationList(
    val info: Info,
    @SerializedName("results")
    val locations: List<Location>
)