package com.example.astonfinalproject.di

import com.example.astonfinalproject.data.repositories.CharactersRepositoryImpl
import com.example.astonfinalproject.data.repositories.LocationsRepositoryImpl
import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.data.repositories.EpisodesRepositoryImpl
import com.example.astonfinalproject.domain.repositories.CharactersRepository
import com.example.astonfinalproject.domain.repositories.EpisodesRepository
import com.example.astonfinalproject.domain.repositories.LocationsRepository
import com.example.astonfinalproject.presentation.screens.CharactersFragment
import com.example.astonfinalproject.presentation.screens.EpisodesFragment
import com.example.astonfinalproject.presentation.screens.FilterCharactersFragment
import com.example.astonfinalproject.presentation.screens.FilterEpisodesFragment
import com.example.astonfinalproject.presentation.screens.FilterLocationsFragment
import com.example.astonfinalproject.presentation.screens.LocationsFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com/api/"

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: LocationsFragment)
    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: FilterCharactersFragment)
    fun inject(fragment: FilterLocationsFragment)
    fun inject(fragment: FilterEpisodesFragment)
}

@Module(includes = [NetworkModule::class, AppBindModule::class])
class AppModule

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRickAndMortyService(
        gsonConverterFactory: GsonConverterFactory
    ): RickAndMortyService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RickAndMortyService::class.java)
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(
        gson: Gson
    ): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}

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
}