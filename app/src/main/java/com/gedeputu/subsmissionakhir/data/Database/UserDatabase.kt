package com.gedeputu.subsmissionakhir.data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFavorite::class],
    version = 1, exportSchema = false
    )

abstract class UserDatabase : RoomDatabase() {

    companion object {
        private var Instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase?{
            if (Instance==null) {
                synchronized(UserDatabase::class){
                    Instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database").build()
                }
            }
            return Instance
        }
    }

    abstract fun userFavoriteDao(): UserFavoriteDao
}