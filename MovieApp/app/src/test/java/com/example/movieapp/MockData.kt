package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.core.network.NetworkServices
import com.example.movieapp.features.movie.data.IMovieRepository
import com.example.movieapp.features.movie.domain.listmovie.Dates
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import com.example.movieapp.features.movie.domain.moviedescription.Movie
import retrofit2.Response

val movieMockData: Movie = Movie(
    id= 616037,
    backdrop_path = "example.jpg",
    original_title = "Thor: Love and Thunder",
    overview = "movieExampleDescription",
    poster_path = "example.jpg",
    release_date = "14/07/2022",
    title = "Thor: Love and Thunder",
    vote_average = 4.5,
    vote_count = 10
)

val datesMockData: Dates = Dates(
    maximum = "20/07/2022",
    minimum = "14/07/2022"
)

val listMovieMockData: List<Movie> = listOf(
    Movie(
        id= 616037,
        backdrop_path = "example1.jpg",
        original_title = "Thor: Love and Thunder",
        overview = "movieExampleDescription1",
        poster_path = "example3.jpg",
        release_date = "14/07/2022",
        title = "Thor: Love and Thunder",
        vote_average = 4.5,
        vote_count = 10
    ),
    Movie(
        id= 616038,
        backdrop_path = "example2.jpg",
        original_title = "Spider-man",
        overview = "movieExampleDescription2",
        poster_path = "example3.jpg",
        release_date = "15/07/2022",
        title = "Spider-man",
        vote_average = 4.5,
        vote_count = 11
    ),
    Movie(
        id= 616039,
        backdrop_path = "example3.jpg",
        original_title = "IronMan",
        overview = "movieExampleDescription3",
        poster_path = "example3.jpg",
        release_date = "16/07/2022",
        title = "IronMan",
        vote_average = 4.5,
        vote_count = 12
    )
)

val listMovieResponseMockData:ListMovieResponse = ListMovieResponse(
    dates = datesMockData,
    page = 1,
    results = listMovieMockData,
    total_pages = 16,
    total_results = 3
)

class TestMovieApi: NetworkServices{
    override suspend fun getListMovie(page: Int, apiKey: String): Response<ListMovieResponse> {
        return Response.success(listMovieResponseMockData)
    }

    override suspend fun getMovieDescription(id: Int, apiKey: String): Response<Movie> {
        return Response.success(movieMockData)
    }

}