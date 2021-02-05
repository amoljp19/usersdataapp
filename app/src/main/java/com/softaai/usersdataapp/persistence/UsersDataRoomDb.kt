package com.softaai.usersdataapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.softaai.usersdataapp.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Data::class), version = 1, exportSchema = false)
abstract class UsersDataRoomDb : RoomDatabase() {

    abstract fun usersDataDao(): UsersDataApiResponseDao

    private class UsersDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val usersDao = database.usersDataDao()

                    usersDao.deleteAll()


                    val data = Data("", "", "Amol", 1,"Pawar")
                    usersDao.insert(data)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: UsersDataRoomDb? = null

        fun getUsersDatabase(context: Context, scope: CoroutineScope): UsersDataRoomDb {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataRoomDb::class.java,
                    "users_database"
                ).addCallback(UsersDatabaseCallback(scope))
                        .build()
                INSTANCE = instance

                instance
            }
        }
    }
}