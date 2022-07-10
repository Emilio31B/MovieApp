package com.example.movieapp.core.utils

import com.example.movieapp.features.login.domain.LoginRequest
import com.example.movieapp.features.login.domain.LoginResponse

fun validateCredentials(request: LoginRequest): LoginResponse {
    val status: Boolean
    val message: String
    if( request.username.isEmpty() || request.password.isEmpty() ) {
        status = false
        message = "There are empty fields"
    } else if(
        request.username == Constants.username &&
        request.password == Constants.password ) {
        status = true
        message = "Successful login"
    } else {
        status = false
        message = "Username or password is incorrect. Try again"
    }

    return LoginResponse(
        status = status,
        message = message
    )
}