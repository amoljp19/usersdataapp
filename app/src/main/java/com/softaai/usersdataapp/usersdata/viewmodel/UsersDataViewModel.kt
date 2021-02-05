package com.softaai.usersdataapp.usersdata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.network.UsersDataApiClient
import com.softaai.usersdataapp.repository.UsersDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersDataViewModel @Inject constructor(private val usersDataApiClient: UsersDataApiClient, private val usersDataRepository: UsersDataRepository) : ViewModel() {

    val allUsers: LiveData<List<Data>> = usersDataRepository.allUsers.asLiveData()


    fun insert(data: Data) = viewModelScope.launch {
        usersDataRepository.insert(data)
    }
}