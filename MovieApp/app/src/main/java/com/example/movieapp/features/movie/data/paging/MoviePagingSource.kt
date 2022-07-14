package com.example.movieapp.features.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.BuildConfig
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.features.movie.domain.moviedescription.Movie

class MoviePagingSource(private val api: NetworkServices): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response = api.getListMovie(position, BuildConfig.API_KEY)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if(position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}