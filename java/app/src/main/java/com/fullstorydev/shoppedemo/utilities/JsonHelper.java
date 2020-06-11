package com.fullstorydev.shoppedemo.utilities;

import com.fullstorydev.shoppedemo.data.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonHelper {
    public static List<Item> getProductListFromJsonString(String jsonStr) {
        Gson gson = new Gson();
        // for now item and product shares the same model, will separate them in the future refactor
        TypeToken<List<Item>> list = new TypeToken<List<Item>>() {};
        return gson.fromJson(jsonStr, list.getType());
    }
}
