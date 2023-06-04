package com.example.astonfinalproject.di

import com.example.astonfinalproject.MainActivity
import com.example.astonfinalproject.data.RickAndMortyRepositoryImpl
import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.domain.RickAndMortyRepository
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
    fun inject(activity: MainActivity)
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
    fun bindRickAndMortyRepository(
        rickAndMortyRepositoryImpl: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository
}