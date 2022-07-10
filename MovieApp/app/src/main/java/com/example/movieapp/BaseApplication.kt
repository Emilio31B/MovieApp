package com.example.movieapp

import android.app.Application
import com.example.movieapp.core.infra.di.AppModule
import com.example.movieapp.core.network.di.NetworkModule
import com.example.movieapp.features.movie.di.MovieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin(this@BaseApplication)
    }

    private fun setUpKoin(context: Application) {
        // Start Koin
        startKoin {
            androidContext(context)
            modules(
                AppModule.appModule,
                NetworkModule.module,
                MovieModule.module
            )
        }
    }
}