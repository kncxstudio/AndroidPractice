package com.iboxshare.qqmusiclight.util;

import com.iboxshare.qqmusiclight.info.Info;

import java.io.IOError;
import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by KNCX on 2016/5/26.
 */
public class PostTool {
    static OkHttpClient okHttpClient  = new OkHttpClient();
    public static String getTopSongs(String categoryId) throws IOException{
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("showapi_appid", Info.musicAppId)
                .addFormDataPart("showapi_sign",Info.musicAppSign)
                .addFormDataPart("topid",categoryId)
                .build();
        Request request = new Request.Builder()
                .url(Info.topSongsUrl)
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();

    }
}
