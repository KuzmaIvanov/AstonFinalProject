package com.example.astonfinalproject

import android.app.Application
import com.example.astonfinalproject.di.DaggerApplicationComponent

class MyApplication: Application() {
    val appComponent = DaggerApplicationComponent.create()
}