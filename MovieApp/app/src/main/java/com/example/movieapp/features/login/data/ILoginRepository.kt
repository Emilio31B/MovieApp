package com.example.movieapp.features.login.data

import com.example.movieapp.core.infra.NetworkResponse
import com.example.movieapp.features.login.domain.LoginRequest
import com.example.movieapp.features.login.domain.LoginResponse

interface ILoginRepository {
    suspend fun doLogin(requestLogin: LoginRequest): NetworkResponse<LoginResponse>
}