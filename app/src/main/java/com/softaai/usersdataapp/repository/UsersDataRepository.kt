package com.softaai.usersdataapp.repository

import androidx.annotation.WorkerThread
import com.softaai.usersdataapp.model.Data
import com.softaai.usersdataapp.persistence.UsersDataApiResponseDao
import kotlinx.coroutines.flow.Flow

class UsersDataRepository(private val usersDataDao: UsersDataApiResponseDao) {


    val allUsers: Flow<List<Data>> = usersDataDao.getAlphabetizedUsersData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(data: Data) {
        usersDataDao.insert(data)
    }
}