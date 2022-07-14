package com.example.movieapp.features.movie.domain.listmovie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListMovieResponse(
    @SerializedName("dates") val dates: Dates,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
): Serializable

data class Dates(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
): Serializable
