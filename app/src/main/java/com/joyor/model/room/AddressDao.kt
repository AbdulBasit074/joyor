package com.joyor.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joyor.model.Address


@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address)

    @Query("SELECT * FROM Address LIMIT 1")
    fun getUserAddress(): Address?

    @Query("DELETE FROM Address")
    fun deleteAddress()


}