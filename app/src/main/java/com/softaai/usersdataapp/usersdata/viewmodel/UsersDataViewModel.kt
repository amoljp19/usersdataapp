package com.softaai.usersdataapp.usersdata.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.repository.UsersDataRepository
import com.softaai.usersdataapp.usersdata.viewmodel.base.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersDataViewModel @Inject constructor(private val usersDataRepository: UsersDataRepository) : LiveCoroutinesViewModel() {

    private var usersDataFetchingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val UsersDataListLiveData: LiveData<List<Data>>

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

    fun insert(data : Data) = viewModelScope.launch {
        usersDataRepository.insert(data)
    }

}