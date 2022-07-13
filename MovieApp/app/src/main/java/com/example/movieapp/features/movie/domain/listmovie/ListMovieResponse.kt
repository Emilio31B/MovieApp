package com.example.movieapp.features.movie.domain.listmovie

import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("backdrop_path") val backdrop_path: String?,
    @field:SerializedName("original_title") val original_title: String?,
    @field:SerializedName("overview") val overview: String?,
    @field:SerializedName("poster_path") val poster_path: String?,
    @field:SerializedName("release_date") val release_date: String,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("vote_average") val vote_average: Double?,
    @field:SerializedName("vote_count") val vote_count: Int?
): Serializable

