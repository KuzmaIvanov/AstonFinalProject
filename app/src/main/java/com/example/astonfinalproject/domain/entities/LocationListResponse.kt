package com.example.astonfinalproject.domain.entities

import com.google.gson.annotations.SerializedName

data class LocationListResponse(
    val info: Info,
    @SerializedName("results")
    val locations: List<Location>
)