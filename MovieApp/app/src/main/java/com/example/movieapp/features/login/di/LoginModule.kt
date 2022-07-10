package com.example.movieapp.features.login.di

import com.example.movieapp.features.login.data.ILoginRepository
import com.example.movieapp.features.login.data.LoginRepositoryImpl
import com.example.movieapp.features.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object LoginModule {

    val module = module {

        single<ILoginRepository> { LoginRepositoryImpl() }

        viewModel { LoginViewModel(get()) }
    }
}