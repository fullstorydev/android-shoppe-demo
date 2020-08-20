package com.fullstorydev.shoppedemo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
abstract class OrderDao {
    @Query("SELECT * FROM orders")
    abstract LiveData<List<Order>> getAll();

    @Query("SELECT * FROM orders WHERE completed = 0 LIMIT 1")
    protected abstract LiveData<Order> getCurrentOrder();

    @Query("UPDATE orders SET completed = :completed WHERE id = :orderId")
    abstract void updateOrderStatus(String orderId, boolean completed);

    @Query("SELECT * FROM orders WHERE completed = :completed")
    protected abstract LiveData<List<Order>> getOrdersWithStatus(boolean completed);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Order order);
}