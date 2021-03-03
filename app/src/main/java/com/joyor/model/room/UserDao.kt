package com.joyor.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joyor.model.User


@Dao
interface UserDao {


    @Insert
    fun login(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    fun getLoggedUser(): User?

    @Query("DELETE FROM User")
    fun logOut()

}