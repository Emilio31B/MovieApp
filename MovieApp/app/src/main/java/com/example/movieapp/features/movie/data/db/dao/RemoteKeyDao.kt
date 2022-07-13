package com.example.movieapp.features.movie.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.features.movie.data.db.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysRepoId(movieId: Int): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}