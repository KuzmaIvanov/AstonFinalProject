package com.example.astonfinalproject.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.astonfinalproject.presentation.viewmodels.characters.CharacterDetailsViewModel
import com.example.astonfinalproject.presentation.viewmodels.characters.CharactersSearchViewModel
import com.example.astonfinalproject.presentation.viewmodels.characters.CharactersViewModel
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesDetailsViewModel
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesSearchViewModel
import com.example.astonfinalproject.presentation.viewmodels.episodes.EpisodesViewModel
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationDetailsViewModel
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationsSearchViewModel
import com.example.astonfinalproject.presentation.viewmodels.locations.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun splashCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    abstract fun splashLocationsViewModel(viewModel: LocationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    abstract fun splashEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun splashCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    abstract fun splashLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesDetailsViewModel::class)
    abstract fun splashEpisodeDetailsViewModel(viewModel: EpisodesDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersSearchViewModel::class)
    abstract fun splashCharactersSearchViewModel(viewModel: CharactersSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsSearchViewModel::class)
    abstract fun splashLocationsSearchViewModel(viewModel: LocationsSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesSearchViewModel::class)
    abstract fun splashEpisodesSearchViewModel(viewModel: EpisodesSearchViewModel): ViewModel
}