package com.example.movieapp.features.movie.di

import com.example.movieapp.features.movie.data.IMovieRepository
import com.example.movieapp.features.movie.data.MovieRepositoryImpl
import com.example.movieapp.features.movie.data.db.MovieDatabase
import com.example.movieapp.features.movie.presentation.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MovieModule {

    val module = module {

        single<IMovieRepository> { MovieRepositoryImpl(get(), get(), MovieDatabase.getInstance(androidContext())) }

        viewModel { MovieViewModel(get()) }
    }
}