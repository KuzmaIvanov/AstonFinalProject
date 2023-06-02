package com.example.astonfinalproject.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("characters")
data class CharacterEntity(
    @PrimaryKey val uid: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @ColumnInfo("character_origin_name") val characterOriginName: String,
    @ColumnInfo("character_origin_url") val characterOriginUrl: String,
    @ColumnInfo("character_location_name") val characterLocationName: String,
    @ColumnInfo("character_location_url") val characterLocationsUrl: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("episodes_url_list_json") val episodesUrlListJson: String,
    @ColumnInfo("url") val characterUrl: String,
    val created: String,
    @ColumnInfo("page_number") val pageNumber: Int
)