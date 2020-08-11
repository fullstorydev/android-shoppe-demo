package com.fullstorydev.shoppedemo.utilities;

import android.util.Log;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.data.Item;
import com.fullstorydev.shoppedemo.data.Order;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FSUtils{
    public static void checkoutSuccess(Double subtotal, Order order) {
        String orderId = "unknown";
        if( order != null) orderId = order.id;

        Map<String, Object> properties = new HashMap<>();
        properties.put("order_id_str", orderId);
        properties.put("revenue_real", subtotal);
        properties.put("shipping", 5.99);
        properties.put("tax_real", 2.75);
        FS.event(Constants.EVENT_NAMES.ORDER_COMPLETED, properties);
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

        FS.event(Constants.EVENT_NAMES.CHECKOUT_ERROR, properties);
    }

    public static void productAdded(Item item){
        Map<String, Object> properties = new HashMap<>();
        properties.put("description_str", item.description);
        properties.put("displayName_str", item.title);
        properties.put("id_str", item.title);
        properties.put("imgName_str", item.image);
        properties.put("price_raw_real", item.price);
        properties.put("price_real", item.price);
        properties.put("product_id_str", FSUtils.makeId(9));
        properties.put("unit_str", item.unit);

        FS.event(Constants.EVENT_NAMES.PRODUCT_ADDED, properties);
    }

    public static void productRemoved(Item item){
        Map<String, Object> properties = new HashMap<>();
        properties.put("description_str", item.description);
        properties.put("displayName_str", item.title);
        properties.put("id_str", item.title);
        properties.put("imgName_str", item.image);
        properties.put("price_raw_real", item.price);
        properties.put("price_real", item.price);
        properties.put("product_id_str", FSUtils.makeId(9));
        properties.put("unit_str", item.unit);

        FS.event(Constants.EVENT_NAMES.PRODUCT_REMOVED, properties);
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
}
