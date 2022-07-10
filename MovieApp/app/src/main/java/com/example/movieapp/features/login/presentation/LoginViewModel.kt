package com.example.movieapp.features.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.login.data.ILoginRepository
import com.example.movieapp.features.login.domain.LoginRequest
import com.example.movieapp.features.login.domain.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: ILoginRepository
) : ViewModel() {

    private val loginMLD: MutableLiveData<LoginResponse?> = MutableLiveData()
    val loginLD: LiveData<LoginResponse?>
        get() = loginMLD

    private val loadingMLD: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLD: LiveData<Boolean>
        get() = loadingMLD

    private val errorMLD: MutableLiveData<String> = MutableLiveData()
    val errorLD: LiveData<String>
        get() = errorMLD


    fun doLogin(request: LoginRequest) = viewModelScope.launch {
        loadingMLD.postValue(true)

        when(val response = loginRepository.doLogin(request)) {
            is NetworkResponse.Success -> {
                loginMLD.postValue(response.data)
                loadingMLD.postValue(false)
            }
            is NetworkResponse.Error -> {
                errorMLD.postValue(response.exception.message ?: "")
                loadingMLD.postValue(false)
            }
        }
    }
}