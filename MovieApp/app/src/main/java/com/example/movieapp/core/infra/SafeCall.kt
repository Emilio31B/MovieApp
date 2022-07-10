package com.example.movieapp.core.infra

import android.util.Log
import com.example.movieapp.core.infra.NetworkResponse.Success
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

interface SafeCallInterface {
    suspend fun <T> makeCall(
        block: suspend () -> Response<T>
    ): NetworkResponse<T>
}

object SafeCall: SafeCallInterface {
    override suspend fun <T> makeCall(block: suspend () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = block()
            if (response.isSuccessful) {
                response.body()?.let(::Success) ?: NetworkResponse.Error(NoDataException())
            } else {
                NetworkResponse.Error(UnknownStatusCodeException())
            }
        } catch (exception: HttpException) {
            NetworkResponse.Error(exception)
        } catch (exception: UnknownHostException) {
            NetworkResponse.Error(exception)
        } catch (exception: ConnectException) {
            NetworkResponse.Error(exception)
        } catch (exception: IOException) {
            NetworkResponse.Error(exception)
        } catch (exception: SSLHandshakeException) {
            NetworkResponse.Error(exception)
        }
    }

}