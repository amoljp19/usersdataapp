package com.softaai.usersdataapp.network

import com.softaai.usersdataapp.model.UsersDataApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersDataApiService {

    @GET("/api/users")
    fun fetchUsersData(
        @Query("page") pageNo: String = "1"
    ): Call<UsersDataApiResponse>
}