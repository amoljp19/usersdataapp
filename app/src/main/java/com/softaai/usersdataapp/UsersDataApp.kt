package com.softaai.usersdataapp

import android.app.Application
import com.softaai.usersdataapp.persistence.UsersDataRoomDb
import com.softaai.usersdataapp.repository.UsersDataRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class UsersDataApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { UsersDataRoomDb.getUsersDatabase(this, applicationScope) }
    val repository by lazy { UsersDataRepository(database.usersDataDao()) }
}