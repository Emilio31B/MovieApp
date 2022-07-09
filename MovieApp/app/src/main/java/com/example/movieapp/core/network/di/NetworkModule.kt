package com.example.movieapp.core.network.di

import com.example.movieapp.core.network.NetworkConfig
import com.example.movieapp.core.network.NetworkServices
import org.koin.dsl.module

object NetworkModule {
    val module = module {
        single { NetworkConfig.getInstance(NetworkServices::class.java) }
    }
}