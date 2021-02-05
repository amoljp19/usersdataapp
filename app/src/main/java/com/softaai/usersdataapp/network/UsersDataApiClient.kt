package com.softaai.usersdataapp.network

import com.softaai.usersdataapp.model.UsersDataApiResponse
import javax.inject.Inject

class UsersDataApiClient @Inject constructor(private val usersDataApiService: UsersDataApiService) {

    fun fetchUsersData(onResult: (response: ApiResponse<UsersDataApiResponse>) -> Unit) {
        this.usersDataApiService.fetchUsersData().transform(onResult)
    }
}