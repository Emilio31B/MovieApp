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
import com.example.movieapp.features.movie.domain.listmovie.Movie
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
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
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
                // clear all tables in the database
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
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                movieDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

}