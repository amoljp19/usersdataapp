package com.softaai.usersdataapp.network

import retrofit2.Call
import retrofit2.Callback

fun <T> Call<T>.transform(onResult: (response: ApiResponse<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            onResult(ApiResponse.of { response })
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            onResult(ApiResponse.error(throwable))
        }
    })
}

fun <T> ApiResponse.Failure.Error<T>.message() = "$code: ${responseBody?.string()}"

fun <T> ApiResponse.Failure.Exception<T>.message() = "$message"