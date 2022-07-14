package com.example.movieapp.features.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.features.movie.data.db.dao.MovieDao
import com.example.movieapp.features.movie.data.db.dao.RemoteKeyDao
import com.example.movieapp.features.movie.domain.moviedescription.Movie

@Database(
    entities = [Movie::class, RemoteKey::class],
    version = 1,
    exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "Movie.db"
            ).build()
    }
}