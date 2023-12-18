package com.example.stockapp

import android.app.Application
import com.example.stockapp.di.LocalAppContainer

class StockApp: Application() {

    lateinit var container: LocalAppContainer

    override fun onCreate() {
        super.onCreate()
        container = LocalAppContainer(this)
    }
}