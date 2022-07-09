package com.example.movieapp.features.movie.data

import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import retrofit2.Response

interface IMovieRepository {
    suspend fun getListMovie(page: Int): NetworkResponse<ListMovieResponse>
}