package com.softaai.usersdataapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.network.ApiResponse
import com.softaai.usersdataapp.network.UsersDataApiClient
import com.softaai.usersdataapp.network.message
import com.softaai.usersdataapp.persistence.UsersDataApiResponseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDataRepository @Inject constructor(private val usersDataApiClient: UsersDataApiClient, private val usersDataDao: UsersDataApiResponseDao) {


    val allUsersData: Flow<List<Data>> = usersDataDao.getAlphabetizedUsersData()
    var allUsers : List<Data>? = null

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(data: Data) {
        usersDataDao.insertUsersDataList(listOf(data))
    }

    suspend fun loadUserDataResponse(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Data>>()

        allUsers = usersDataDao.getAlphabetizedUsersData().asLiveData().value

        if (allUsers.isNullOrEmpty() == true) {
            usersDataApiClient.fetchUsersData() { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.let {
                            allUsers = it!!.data
                            liveData.postValue(it.data)
                            GlobalScope.launch{
                                usersDataDao.insertUsersDataList(it.data)
                            }
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        }
        liveData.apply { postValue(allUsers) }
    }

    suspend fun loadMoreUserDataResponse(pageNo:String, error: (String) -> Unit) = withContext(Dispatchers.IO) {

        val liveData = MutableLiveData<List<Data>>()
        usersDataApiClient.fetchMoreUsersData(pageNo){ response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data.let {
                            allUsers = it!!.data
                            liveData.postValue(it.data)
                            GlobalScope.launch{
                                usersDataDao.insertUsersDataList(it.data)
                            }
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        liveData.apply { postValue(allUsers) }
    }
}