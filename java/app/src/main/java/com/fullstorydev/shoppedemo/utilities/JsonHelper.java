package com.fullstorydev.shoppedemo.utilities;

import android.util.Log;

import com.fullstorydev.shoppedemo.data.Item;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonHelper {
    public static List<Item> getProductListFromJsonString(String jsonStr) throws JsonParseException {
        try{
            Gson gson = new Gson();
            // for now item and product shares the same model, will separate them in the future refactor
            TypeToken<List<Item>> list = new TypeToken<List<Item>>() {};
            return gson.fromJson(jsonStr, list.getType());
        }catch (JsonParseException e){
            throw e;
        }
    }
}
