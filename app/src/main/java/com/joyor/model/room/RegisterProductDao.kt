package com.joyor.model.room

import androidx.room.*
import com.joyor.model.Product
import com.joyor.model.RegisterProduct


@Dao
interface RegisterProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRegisterProduct(productRegisterList: List<RegisterProduct>)

    @Delete
    fun removeRegisterProduct(productRegister: RegisterProduct)

    @Query("SELECT * From RegisterProduct")
    fun getAllRegisterProduct(): List<RegisterProduct>

    @Query("DELETE from RegisterProduct")
    fun removeAll()

    @Query("DELETE from RegisterProduct WHERE id=:idProduct")
    fun removeProduct(idProduct: String)


}