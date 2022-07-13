package com.example.movieapp.features.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.movieapp.core.infra.SafeCallInterface
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.features.movie.data.paging.MoviePagingSource
import com.example.movieapp.features.movie.domain.listmovie.Movie

class MovieRepositoryImpl(
    private val safeCall: SafeCallInterface,
    private val api: NetworkServices
): IMovieRepository {
    override fun getListMovie(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.number_pages,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(api)
            },
            initialKey = 1
        ).liveData
    }
}