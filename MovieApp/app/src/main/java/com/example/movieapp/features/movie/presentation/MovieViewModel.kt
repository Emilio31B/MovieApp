package com.example.movieapp.features.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.movie.data.IMovieRepository
import com.example.movieapp.features.movie.domain.listmovie.ListMovieResponse
import com.example.movieapp.features.movie.domain.listmovie.Movie
import com.example.movieapp.features.movie.presentation.listmovie.adapter.ListMovieAdapter
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: IMovieRepository
) : ViewModel() {

    private val emptyListMovieMLD: MutableLiveData<Boolean> = MutableLiveData()
    val emptyListMovieLD: LiveData<Boolean>
        get() = emptyListMovieMLD

    private val loadingMLD: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLD: LiveData<Boolean>
        get() = loadingMLD

    private val errorMLD: MutableLiveData<String> = MutableLiveData()
    val errorLD: LiveData<String>
        get() = errorMLD

    fun getListMovie(): LiveData<PagingData<Movie>> {
        return movieRepository.getListMovie().cachedIn(viewModelScope)
    }

    fun setAdapterStateListener(adapter: ListMovieAdapter) = viewModelScope.launch {
        adapter.addLoadStateListener { loadState ->

            emptyListMovieMLD.postValue(
                loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            )

            if(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading ) {
                loadingMLD.postValue(true)
            } else {
                loadingMLD.postValue(false)
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    errorMLD.postValue(it.error.message ?: "")
                }
            }
        }
    }
}