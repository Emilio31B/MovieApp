package com.example.movieapp.features.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.core.infra.SafeCallInterface
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.core.utils.Constants
import com.example.movieapp.features.movie.data.db.MovieDatabase
import com.example.movieapp.features.movie.data.db.mediator.MovieMediator
import com.example.movieapp.features.movie.data.paging.MoviePagingSource
import com.example.movieapp.features.movie.domain.moviedescription.Movie

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
            //Set mediator when room integration is done
            /*remoteMediator = MovieMediator(
                api,
                database
            ),*/
            pagingSourceFactory = {
                MoviePagingSource(api)
                //Get data from database when room integration is done
                //database.movieDao().getMovies()
            },
            initialKey = 1
        ).liveData
    }

    override suspend fun getMovieDescription(movieId: Int) = safeCall.makeCall {
        api.getMovieDescription(
            movieId,
            BuildConfig.API_KEY
        )
    }
}