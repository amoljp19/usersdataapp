package com.softaai.usersdataapp.di.module

import android.content.Context
import com.softaai.usersdataapp.network.RequestInterceptor
import com.softaai.usersdataapp.network.UsersDataApiClient
import com.softaai.usersdataapp.network.UsersDataApiService
import com.softaai.usersdataapp.persistence.UsersDataApiResponseDao
import com.softaai.usersdataapp.persistence.UsersDataRoomDb
import com.softaai.usersdataapp.repository.UsersDataRepository
import com.softaai.usersdataapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().addInterceptor(RequestInterceptor()).build();


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UsersDataApiService =
        retrofit.create(UsersDataApiService::class.java)

    @Provides
    @Singleton
    fun provideApiClient(usersDataApiService: UsersDataApiService): UsersDataApiClient =
        UsersDataApiClient(usersDataApiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): UsersDataRoomDb =
        UsersDataRoomDb.getUsersDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDataDao(db: UsersDataRoomDb): UsersDataApiResponseDao = db.usersDataDao()

    @Singleton
    @Provides
    fun provideUserDataRepository(
        usersDataApiClient: UsersDataApiClient,
        usersDataApiResponseDao: UsersDataApiResponseDao
    ) =
        UsersDataRepository(usersDataApiClient, usersDataApiResponseDao)
}