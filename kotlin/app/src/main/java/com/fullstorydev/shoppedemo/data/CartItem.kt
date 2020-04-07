package com.fullstorydev.shoppedemo.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "cart_item_id")  val id: Int,
    @Embedded val product: Product,
    val quantityInCart: Int = 0
)
