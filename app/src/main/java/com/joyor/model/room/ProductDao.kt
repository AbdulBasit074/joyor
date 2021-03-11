package com.joyor.model.room

import androidx.room.*
import com.joyor.model.Product


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(product: Product)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCartNotReplace(product: Product)



    @Query("SELECT * FROM Product WHERE id=:productId AND color_code=:colorCode LIMIT 1")
    fun containsProduct(productId: Int, colorCode: String): Product?


    @Update
    fun updateProduct(product: Product)


    @Query("DELETE FROM Product WHERE id=:productId AND color_code=:colorCode")
    fun removeProduct(productId: Int, colorCode: String)


    @Query("UPDATE product set quantity=:quantityUpdate WHERE id=:productId AND color_code=:color")
    fun updateColorQuantity(quantityUpdate:Int,color:String,productId:Int)

    @Query("UPDATE product set color_code=:colorCodeNew,color_name=:colorNameNew  WHERE id=:productId AND color_code=:colorOld")
    fun updateColor(colorOld:String,colorCodeNew:String,colorNameNew:String,productId:Int)


    @Query("SELECT * FROM Product")
    fun getAllCartProduct(): List<Product>


}