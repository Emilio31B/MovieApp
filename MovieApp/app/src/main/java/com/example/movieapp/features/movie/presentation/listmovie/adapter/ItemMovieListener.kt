package com.example.movieapp.features.movie.presentation.listmovie.adapter

import com.example.movieapp.features.movie.domain.moviedescription.Movie


interface ItemMovieListener {
    fun onItemMovieClickListener(movie: Movie)
}