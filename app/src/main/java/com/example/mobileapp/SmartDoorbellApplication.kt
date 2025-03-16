package com.example.mobileapp

import android.app.Application
import com.example.mobileapp.data.AppContainer
import com.example.mobileapp.data.AppDataContainer

class SmartDoorbellApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}