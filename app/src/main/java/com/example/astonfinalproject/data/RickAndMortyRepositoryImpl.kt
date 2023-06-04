package com.example.astonfinalproject.data

import android.util.Log
import com.example.astonfinalproject.data.network.RickAndMortyService
import com.example.astonfinalproject.domain.RickAndMortyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : RickAndMortyRepository {
    override fun sayHello() {
        Log.i("HELLO", "HELLO FROM REPOSITORY!")
    }

}