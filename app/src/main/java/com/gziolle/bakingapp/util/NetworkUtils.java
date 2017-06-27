package com.gziolle.bakingapp.util;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 21/06/2017.
 * gziolle@gmail.com
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gziolle.bakingapp.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    // https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static ArrayList<Recipe> fetchRecipes() {
        Response response;
        ArrayList<Recipe> recipes = null;
        OkHttpClient client = new OkHttpClient();
        try {
            HttpUrl.Builder builder = HttpUrl.parse(RECIPES_URL).newBuilder();
            Request request = new Request.Builder()
                    .url(builder.toString())
                    .build();
            response = client.newCall(request).execute();

            Gson gson = new GsonBuilder().create();
            recipes = gson.fromJson(response.body().string(), new TypeToken<ArrayList<Recipe>>() {
            }.getType());
        } catch (IOException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
        return recipes;
    }
}
