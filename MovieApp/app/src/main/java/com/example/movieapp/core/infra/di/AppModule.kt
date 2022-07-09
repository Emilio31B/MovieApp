package com.example.movieapp.core.infra.di

import com.example.movieapp.core.infra.SafeCall
import com.example.movieapp.core.infra.SafeCallInterface
import org.koin.dsl.module

object AppModule {

    val appModule = module {
        single<SafeCallInterface> { SafeCall }
    }
}