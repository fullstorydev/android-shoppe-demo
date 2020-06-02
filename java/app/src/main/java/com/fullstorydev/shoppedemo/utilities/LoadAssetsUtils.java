package com.fullstorydev.shoppedemo.utilities;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class LoadAssetsUtils {

    public static String getProductListFromFile(Application application, String fileName) throws IOException {
        InputStream is = application.getAssets().open(fileName);
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
