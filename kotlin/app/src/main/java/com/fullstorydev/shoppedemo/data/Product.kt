package com.fullstorydev.shoppedemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val productId: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val unit: String = "lb"
)