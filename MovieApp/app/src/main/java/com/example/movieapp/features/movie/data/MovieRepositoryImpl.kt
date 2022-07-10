package com.example.movieapp.features.movie.data

import com.example.movieapp.BuildConfig
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.core.infra.SafeCallInterface
import com.example.movieapp.core.infra.succeeded
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import retrofit2.Response

class MovieRepositoryImpl(
    private val safeCall: SafeCallInterface,
    private val api: NetworkServices
): IMovieRepository {
    override suspend fun getListMovie(page: Int) = safeCall.makeCall {
        api.getListMovie(
            page = page,
            apiKey = BuildConfig.API_KEY
        )
    }
}