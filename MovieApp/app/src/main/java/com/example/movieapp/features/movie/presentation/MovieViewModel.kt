package com.example.movieapp.features.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.movie.data.IMovieRepository
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import com.example.movieapp.features.movie.domain.listmovie.Movie
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: IMovieRepository
) : ViewModel() {

    private val listMovieMLD: MutableLiveData<ListMovieResponse?> = MutableLiveData()
    val listMovieLD: LiveData<ListMovieResponse?>
        get() = listMovieMLD

    private val loadingMLD: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLD: LiveData<Boolean>
        get() = loadingMLD

    private val errorMLD: MutableLiveData<String> = MutableLiveData()
    val errorLD: LiveData<String>
        get() = errorMLD

    /*fun getListMovie(page: Int) = viewModelScope.launch {
        loadingMLD.postValue(true)
        val response = movieRepository.getListMovie(
            page = page
        )

        when(response) {
            is NetworkResponse.Success -> {
                listMovieMLD.postValue(response.data)
                loadingMLD.postValue(false)
            }
            is NetworkResponse.Error -> {
                errorMLD.postValue(response.exception.message ?: "")
                loadingMLD.postValue(false)
            }
        }
    }*/

    fun getListMovie(): LiveData<PagingData<Movie>> {
        return movieRepository.getListMovie().cachedIn(viewModelScope)
    }
}