package com.example.movieapp.features.login.data

import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.core.infra.UnknownStatusCodeException
import com.example.movieapp.core.utils.validateCredentials
import com.example.movieapp.features.login.domain.LoginRequest
import com.example.movieapp.features.login.domain.LoginResponse

class LoginRepositoryImpl(): ILoginRepository {
    override suspend fun doLogin(requestLogin: LoginRequest): NetworkResponse<LoginResponse> {
        val response = validateCredentials(requestLogin)

        return if(response.status) NetworkResponse.Success(response)
        else NetworkResponse.Error(UnknownStatusCodeException(response.message))
    }
}