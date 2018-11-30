package com.banana.bananaweather.until;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by djd000 on 2018/11/20.
 */

public class HttpUtil {
    public static void sendOkHttpRequeset(String address,okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
