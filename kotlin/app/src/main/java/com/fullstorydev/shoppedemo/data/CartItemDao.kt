package com.fullstorydev.shoppedemo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): LiveData<List<CartItem>>

    @Query("SELECT quantityInCart FROM cart_items WHERE title = :title LIMIT 1")
    fun getItemQuantityByTitle(title: String): Int

    @Query("SELECT SUM(quantityInCart * price) FROM cart_items")
    fun getSubtotal(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Delete
    suspend fun delete(item: CartItem)

    @Update
    suspend fun updateItem(item: CartItem)

    @Query("UPDATE cart_items SET quantityInCart = quantityInCart + 1 WHERE title = :title")
    suspend fun increaseQuantity(title: String): Int

    @Query("UPDATE cart_items SET quantityInCart = quantityInCart - 1 WHERE title = :title")
    suspend fun decreaseQuantity(title: String): Int
}