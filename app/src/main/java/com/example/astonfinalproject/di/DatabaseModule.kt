package com.example.astonfinalproject.di

import android.content.Context
import androidx.room.Room
import com.example.astonfinalproject.data.db.ApplicationDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val DATABASE_NAME = "application-database"

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}