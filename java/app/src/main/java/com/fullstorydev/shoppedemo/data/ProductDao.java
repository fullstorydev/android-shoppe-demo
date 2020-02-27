package com.fullstorydev.shoppedemo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Delete
    void delete(Product product);

    @Update
    void updateQuantityInCart(Product product);

}