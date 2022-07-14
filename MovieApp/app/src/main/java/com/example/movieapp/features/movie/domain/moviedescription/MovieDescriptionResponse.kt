package com.example.movieapp.features.movie.domain.moviedescription

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("backdrop_path") val backdrop_path: String?,
    @field:SerializedName("original_title") val original_title: String?,
    @field:SerializedName("overview") val overview: String?,
    @field:SerializedName("poster_path") val poster_path: String?,
    @field:SerializedName("release_date") val release_date: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("vote_average") val vote_average: Double?,
    @field:SerializedName("vote_count") val vote_count: Int?
): Serializable