package com.fullstorydev.shoppedemo.data;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fullstorydev.shoppedemo.utilities.FSUtils;

/*    sample data:
{
    "orderId": "sccll0jxh",
    "completed": false
}
 */

@Keep
@Entity(tableName = "orders")
public class Order {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "completed")
    public boolean completed;

    public Order() {
        this.id = FSUtils.makeId(9);
        this.completed = false;
    }

    public Order getOrder(){ return this; }
}