package com.example.movieapp.features.movie.data.db.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.BuildConfig
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.features.movie.data.db.MovieDatabase
import com.example.movieapp.features.movie.data.db.RemoteKey
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieMediator(
    private val api: NetworkServices,
    private val movieDatabase: MovieDatabase
): RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.STARTER_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = api.getListMovie(page, BuildConfig.API_KEY)

            val movies = apiResponse.body()!!.results.filter { true }
            val endOfPaginationReached = movies?.isEmpty()
            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.remoteKeysDao().clearRemoteKeys()
                    movieDatabase.movieDao().clearMovies()
                }
                val prevKey = if (page == Constants.STARTER_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKey(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                movieDatabase.remoteKeysDao().insertAll(keys)
                movieDatabase.movieDao().insertMovies(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                movieDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

}