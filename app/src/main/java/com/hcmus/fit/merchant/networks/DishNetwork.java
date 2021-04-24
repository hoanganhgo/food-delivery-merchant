package com.hcmus.fit.merchant.networks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.models.DishModel;
import com.hcmus.fit.merchant.utils.QueryUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DishNetwork {
    public static void createNewDish(Context context, DishModel dishModel, String restaurantID, String foodCategoryID) {
        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", restaurantID);
        params.put("foodCategoryID", foodCategoryID);
        String query = QueryUtil.createQuery(API.CREATE_NEW_DISH, params);
        Log.d("dish", query);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, query,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("dish", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        queue.add(req);
    }

    public static void updateDish(Context context, DishModel dishModel, String restaurantID,
                                  String foodCategoryID, String foodId) {
        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", restaurantID);
        params.put("foodCategoryID", foodCategoryID);
        params.put("foodID", foodId);
        String query = QueryUtil.createQuery(API.UPDATE_DISH, params);
        Log.d("dish", query);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.PUT, query,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("dish", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        queue.add(req);
    }
}
