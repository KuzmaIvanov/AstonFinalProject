package com.example.astonfinalproject

import android.app.Application
import com.example.astonfinalproject.di.ApplicationComponent
import com.example.astonfinalproject.di.DaggerApplicationComponent

class MyApplication: Application() {
    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}