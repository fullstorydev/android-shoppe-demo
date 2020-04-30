package com.fullstorydev.shoppedemo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
abstract class ItemDao {
    @Query("SELECT * FROM items")
    abstract LiveData<List<Item>> getAll();

    @Query("SELECT quantityInCart FROM items WHERE title = :title LIMIT 1")
    protected abstract int getItemQuantityByTitle(String title);

    @Query("SELECT IFNULL(SUM(quantityInCart * price),0.00) FROM items")
    abstract LiveData<Double> getSubtotal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Item item);

    @Delete
    abstract void delete(Item item);

    @Update
    abstract void updateItem(Item item);

    @Query("UPDATE items SET quantityInCart = quantityInCart + 1 WHERE title=:title")
    abstract int increaseQuantity(String title);

    @Query("UPDATE items SET quantityInCart = quantityInCart - 1 WHERE title=:title")
    abstract int decreaseQuantity(String title);

    @Transaction
    void increaseQuantityOrInsert(Item item) {
        if(increaseQuantity(item.title) == 0){
            item.quantityInCart =1;
            insert(item);
        }
    }
    @Transaction
    void decreaseQuantityOrDelete(Item item) {
        int itemsInCart = getItemQuantityByTitle(item.title);
        if(itemsInCart > 1 ){
            decreaseQuantity(item.title);
        }else{
            delete(item);
        }
    }
}