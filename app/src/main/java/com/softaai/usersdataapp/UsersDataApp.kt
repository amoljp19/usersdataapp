package com.softaai.usersdataapp

import android.app.Application
import androidx.activity.viewModels
import com.softaai.usersdataapp.network.UsersDataApiClient
import com.softaai.usersdataapp.persistence.UsersDataRoomDb
import com.softaai.usersdataapp.repository.UsersDataRepository
import com.softaai.usersdataapp.usersdata.viewmodel.UsersDataViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@HiltAndroidApp
class UsersDataApp : Application() {

}