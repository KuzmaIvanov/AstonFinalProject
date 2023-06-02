package com.example.astonfinalproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astonfinalproject.domain.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}