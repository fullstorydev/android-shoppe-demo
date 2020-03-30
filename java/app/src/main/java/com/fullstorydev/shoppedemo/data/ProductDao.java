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
abstract class ProductDao {
    @Query("SELECT * FROM product")
    abstract LiveData<List<Product>> getAll();

    @Query("SELECT quantityInCart FROM product WHERE title = :productTitles LIMIT 1")
    protected abstract int getProductQuantityByTitle(String productTitles);

    @Query("SELECT sum(quantityInCart*price) FROM product")
    abstract LiveData<Double> getSubtotal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(Product products);

    @Delete
    abstract void delete(Product product);

    @Update
    abstract void updateProduct(Product product);

    @Query("UPDATE product SET quantityInCart = quantityInCart + 1 WHERE title=:title")
    abstract int increaseQuantity(String title);

    @Query("UPDATE product SET quantityInCart = quantityInCart - 1 WHERE title=:title")
    abstract int decreaseQuantity(String title);

    @Transaction
    void increaseQuantityOrInsert(Product product) {
        if(increaseQuantity(product.title) == 0){
            product.quantityInCart =1;
            insert(product);
        }
    }
    @Transaction
    void decreaseQuantityOrDelete(Product product) {
        int productInCart = getProductQuantityByTitle(product.title);
        if(productInCart > 1 ){
            decreaseQuantity(product.title);
        }else{
            delete(product);
        }
    }
}