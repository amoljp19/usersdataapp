package com.softaai.usersdataapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.usersdataapp.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDataApiResponseDao {

    @Query("SELECT * FROM Data ORDER BY firstName ASC")
    fun getAlphabetizedUsersData(): Flow<List<Data>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: List<Data>)

    @Query("DELETE FROM Data")
    suspend fun deleteAll()
}
