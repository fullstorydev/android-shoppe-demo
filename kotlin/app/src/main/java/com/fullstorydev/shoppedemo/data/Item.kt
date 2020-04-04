package com.fullstorydev.shoppedemo.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "item_id")  val id: Int,
    @Embedded val product: Product,
    val quantityInCart: Int = 0
)