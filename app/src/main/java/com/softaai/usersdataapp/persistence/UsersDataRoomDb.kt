package com.softaai.usersdataapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.softaai.usersdataapp.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Data::class), version = 1, exportSchema = true)
abstract class UsersDataRoomDb : RoomDatabase() {

    abstract fun usersDataDao(): UsersDataApiResponseDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDataRoomDb? = null

        fun getUsersDatabase(context: Context): UsersDataRoomDb {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataRoomDb::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}