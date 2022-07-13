package com.example.movieapp.features.movie.data.db.dao

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.features.movie.domain.listmovie.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(repos: List<Movie>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()
}