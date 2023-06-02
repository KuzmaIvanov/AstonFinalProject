package com.example.astonfinalproject.di

import com.example.astonfinalproject.data.network.CharactersService
import com.example.astonfinalproject.data.network.DetailsService
import com.example.astonfinalproject.data.network.EpisodesService
import com.example.astonfinalproject.data.network.LocationsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCharactersService(
        retrofit: Retrofit
    ): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocationsService(
        retrofit: Retrofit
    ): LocationsService {
        return retrofit.create(LocationsService::class.java)
    }

    @Singleton
    @Provides
    fun provideEpisodesService(
        retrofit: Retrofit
    ): EpisodesService {
        return retrofit.create(EpisodesService::class.java)
    }

    @Singleton
    @Provides
    fun provideDetailsService(
        retrofit: Retrofit
    ): DetailsService {
        return retrofit.create(DetailsService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
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