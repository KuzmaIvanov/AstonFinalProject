package com.example.astonfinalproject.di

import android.content.Context
import com.example.astonfinalproject.presentation.screens.characters.CharacterDetailsFragment
import com.example.astonfinalproject.presentation.screens.characters.CharactersFragment
import com.example.astonfinalproject.presentation.screens.characters.CharactersSearchFragment
import com.example.astonfinalproject.presentation.screens.episodes.EpisodesFragment
import com.example.astonfinalproject.presentation.screens.episodes.EpisodesDetailsFragment
import com.example.astonfinalproject.presentation.screens.episodes.EpisodesSearchFragment
import com.example.astonfinalproject.presentation.screens.locations.LocationDetailsFragment
import com.example.astonfinalproject.presentation.screens.locations.LocationsFragment
import com.example.astonfinalproject.presentation.screens.locations.LocationsSearchFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: LocationsFragment)
    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: CharacterDetailsFragment)
    fun inject(fragment: CharactersSearchFragment)
    fun inject(fragment: LocationDetailsFragment)
    fun inject(fragment: LocationsSearchFragment)
    fun inject(fragment: EpisodesSearchFragment)
    fun inject(fragment: EpisodesDetailsFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}

@Module(
    includes = [
        NetworkModule::class,
        AppBindModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
class AppModule