package com.softaai.usersdataapp.usersdata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.repository.UsersDataRepository
import com.softaai.usersdataapp.usersdata.viewmodel.base.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersDataViewModel @Inject constructor(private val usersDataRepository: UsersDataRepository) : LiveCoroutinesViewModel() {

    private var usersDataFetchingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var UsersDataListLiveData: LiveData<List<Data>>

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {

        this.UsersDataListLiveData = this.usersDataFetchingLiveData.switchMap {
            launchOnViewModelScope {
                this.usersDataRepository.loadUserDataResponse {
                    this.toastLiveData.postValue(it)
                }
            }
        }
    }

    fun fetchUsersDataList() = this.usersDataFetchingLiveData.postValue(true)

    fun insert(data: Data) = viewModelScope.launch {
        usersDataRepository.insert(data)
    }

    fun loadMoreUserData() {
        UsersDataListLiveData = this.usersDataFetchingLiveData.switchMap {
            launchOnViewModelScope {
                this.usersDataRepository.loadMoreUserDataResponse(2.toString()) {
                    this.toastLiveData.postValue(it)
                }
            }
        }
    }

}