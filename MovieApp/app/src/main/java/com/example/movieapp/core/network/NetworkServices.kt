package com.example.movieapp.core.network

import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkServices {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("/3/movie/upcoming?")
    suspend fun getListMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<ListMovieResponse>
}