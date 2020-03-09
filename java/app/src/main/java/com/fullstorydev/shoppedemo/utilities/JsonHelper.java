package com.fullstorydev.shoppedemo.utilities;

import com.fullstorydev.shoppedemo.data.Product;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonHelper {
    public static List<Product> getProductListFromJsonString(String jsonStr) throws JsonParseException {
        try{
            Gson gson = new Gson();
            TypeToken<List<Product>> list = new TypeToken<List<Product>>() {};
            List<Product> productList = gson.fromJson(jsonStr, list.getType());
            return productList;
        }catch (JsonParseException e){
            throw e;
        }
    }
}
