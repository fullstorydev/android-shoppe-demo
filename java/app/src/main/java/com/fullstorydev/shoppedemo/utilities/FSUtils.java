package com.fullstorydev.shoppedemo.utilities;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.data.Order;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSUtils{
    public static void checkoutSuccess(Double subtotal, Order order) {
        String orderId = "unknown";
        if( order != null) orderId = order.id;

        Map<String, Object> properties = new HashMap<>();
        properties.put("order_id_str", orderId);
        properties.put("revenue_real", subtotal);
        properties.put("shipping", 5.99);
        properties.put("tax_real", 2.75);
        FS.event(EVENT_NAMES.ORDER_COMPLETED, properties);
    }

    public static void checkoutFailure(String msg, List<Item> items, Order order, Double subtotal) {
        String orderId = "unknown";
        if( order != null) orderId = order.id;

        Map<String, Object> properties = new HashMap<>();
        properties.put("code_str", "AC10XY2");
        properties.put("message_str", msg);
        properties.put("order.items_int", items.size());
        properties.put("order.order_id", orderId);
        properties.put("order.total_real", subtotal);

        FS.event(EVENT_NAMES.CHECKOUT_ERROR, properties);

        // log to logcat so we can generate error clicks
        Log.e("FruitShoppe", msg);
    }

    public static void productUpdated(String event, Item item){
        Map<String, Object> properties = new HashMap<>();
        properties.put("description_str", item.description);
        properties.put("displayName_str", item.title);
        properties.put("id_str", item.title);
        properties.put("imgName_str", item.image);
        properties.put("price_raw_real", item.price);
        properties.put("price_real", item.price);
        properties.put("product_id_str", FSUtils.makeId(9));
        properties.put("unit_str", item.unit);

        FS.event(event, properties);
    }

    public static String makeId(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static final class EVENT_NAMES{
        public static final String PRODUCT_ADDED = "Product Added";
        public static final String PRODUCT_REMOVED = "Product Removed";
        public static final String ORDER_COMPLETED = "Order Completed";
        public static final String CHECKOUT_ERROR = "Checkout Error";
    }
}
