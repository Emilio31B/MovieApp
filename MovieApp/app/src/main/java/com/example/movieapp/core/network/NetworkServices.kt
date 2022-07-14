package com.example.movieapp.core.network

import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import retrofit2.Response
import retrofit2.http.*

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

    @GET("/3/movie/{id}?")
    suspend fun getMovieDescription(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>
}