package com.example.astonfinalproject.domain.entities

import com.google.gson.annotations.SerializedName

data class EpisodeListResponse(
    val info: Info,
    @SerializedName("results")
    val episodes: List<Episode>
)
