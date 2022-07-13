package com.example.movieapp.features.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.movieapp.core.infra.SafeCallInterface
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.features.movie.data.db.MovieDatabase
import com.example.movieapp.features.movie.data.db.mediator.MovieMediator
import com.example.movieapp.features.movie.data.paging.MoviePagingSource
import com.example.movieapp.features.movie.domain.listmovie.Movie

class MovieRepositoryImpl(
    private val safeCall: SafeCallInterface,
    private val api: NetworkServices,
    private val database: MovieDatabase
): IMovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getListMovie(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.number_pages,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            remoteMediator = MovieMediator(
                api,
                database
            ),
            pagingSourceFactory = {
                database.movieDao().getMovies()
            },
            initialKey = 1
        ).liveData
    }
}