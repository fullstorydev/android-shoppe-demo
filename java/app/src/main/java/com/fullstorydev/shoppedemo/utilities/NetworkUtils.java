package com.fullstorydev.shoppedemo.utilities;

import com.fullstory.FS;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient mClient = new OkHttpClient();

    public static String getProductListFromURL(String urlStr) throws IOException {
        Request request = new Request.Builder()
                            .url(urlStr)
                            .header("X-FullStory-URL",FS.getCurrentSessionURL(true) == null? "FS session not ready!": FS.getCurrentSessionURL(true))
                            .build();

        try (Response response = mClient.newCall(request).execute()) {
            if(response.isSuccessful()){
                return response.body().string();
            }else{
                FS.log(FS.LogLevel.ERROR, "getProductListFromURL returned unexpected response code " + response.code());
                throw new IOException("Unexpected response code " + response.code());
            }
        }
    }

    public static String postToCheckout(String urlStr) throws IOException {
        RequestBody reqbody = RequestBody.create(new byte[0]);

        Request request = new Request.Builder()
                .url(urlStr)
                .method("POST", reqbody)
                .header("X-FullStory-URL",FS.getCurrentSessionURL(true) == null? "FS session not ready!": FS.getCurrentSessionURL(true))
                .build();

        try (Response response = mClient.newCall(request).execute()) {
            if(response.isSuccessful()){
                return response.body().string();
            }else{
                FS.log(FS.LogLevel.ERROR, "postToCheckout returned unexpected response code " + response.code());
                throw new IOException("Unexpected response code " + response.code());
            }
        }
    }
}
