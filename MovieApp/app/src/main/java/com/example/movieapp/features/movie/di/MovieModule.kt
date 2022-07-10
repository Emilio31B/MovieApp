package com.example.movieapp.features.movie.di

import com.example.movieapp.features.movie.data.IMovieRepository
import com.example.movieapp.features.movie.data.MovieRepositoryImpl
import com.example.movieapp.features.movie.presentation.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MovieModule {

    val module = module {

        single<IMovieRepository> { MovieRepositoryImpl(get(), get()) }

        viewModel { MovieViewModel(get()) }
    }
}