package com.example.movieapp.features.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import com.example.movieapp.features.movie.domain.listmovie.Movie
import retrofit2.Response

interface IMovieRepository {
    fun getListMovie(): LiveData<PagingData<Movie>>
}