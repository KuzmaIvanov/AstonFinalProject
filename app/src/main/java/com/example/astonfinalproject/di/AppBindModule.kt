package com.example.astonfinalproject.di

import com.example.astonfinalproject.data.repositories.CharactersRepositoryImpl
import com.example.astonfinalproject.data.repositories.DetailsRepositoryImpl
import com.example.astonfinalproject.data.repositories.EpisodesRepositoryImpl
import com.example.astonfinalproject.data.repositories.LocationsRepositoryImpl
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import com.example.astonfinalproject.domain.repositories.DetailsRepository
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppBindModule {

    @Singleton
    @Binds
    fun bindCharactersRepository(
        charactersRepositoryImpl: CharactersRepositoryImpl
    ): CharactersRepository

    @Singleton
    @Binds
    fun bindLocationsRepository(
        locationsRepositoryImpl: LocationsRepositoryImpl
    ): LocationsRepository

    @Singleton
    @Binds
    fun bindEpisodesRepository(
        episodesRepositoryImpl: EpisodesRepositoryImpl
    ): EpisodesRepository

    @Singleton
    @Binds
    fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository
}