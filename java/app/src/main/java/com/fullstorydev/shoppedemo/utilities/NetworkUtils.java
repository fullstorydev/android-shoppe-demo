package com.fullstorydev.shoppedemo.utilities;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient mClient = new OkHttpClient();

    public static String getProductListFromURL(String urlStr) throws IOException {
        Request request = new Request.Builder()
                            .url(urlStr)
                            .build();

        try (Response response = mClient.newCall(request).execute()) {
            if(response.isSuccessful()){
                return response.body().string();
            }else{
                throw new IOException("Unexpected response code " + response.code());
            }
        }
    }
}
