package com.fullstorydev.shoppedemo.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.annotation.Keep;

/*    sample data:
{
        "title": "Bananas",
            "description": "The old reliable.",
            "price": 2.99,ΩΩΩΩ
            "image": "banans.jpg",
            "unit": "lb.",
            "quantityInCart": "2"
    }
 */

@Keep
@Entity(tableName = "items")
public class Item {

    @PrimaryKey@NonNull
    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "unit")
    public String unit="lb";

    @ColumnInfo(name = "quantityInCart",defaultValue = "1")
    public int quantityInCart;

    public Item(String title, String description, Double price, String image, String unit, int quantityInCart) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.image = image;
            this.unit = unit;
            this.quantityInCart=quantityInCart;
    }

    @Ignore
    public Item(String title, String description, Double price, String image, String unit) {
        this(title,description,price,image,unit,1);
    }

    public Item getItem(){return this;}
}