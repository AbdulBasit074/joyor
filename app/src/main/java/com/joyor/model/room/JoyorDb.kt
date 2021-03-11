package com.joyor.model.room

import android.content.Context
import androidx.room.*
import com.joyor.model.Address
import com.joyor.model.Product
import com.joyor.model.User


@Database(entities = [User::class, Product::class,Address::class], version = 7, exportSchema = false)
@TypeConverters(Converter::class)
abstract class JoyorDb : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun addressDao(): AddressDao


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