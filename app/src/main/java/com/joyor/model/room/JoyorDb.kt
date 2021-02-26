package com.joyor.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joyor.model.User


@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class JoyorDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var databaseName: String = "JoyorDb"
        private var instance: JoyorDb? = null
        fun newInstance(context: Context): JoyorDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context, JoyorDb::class.java, databaseName)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}