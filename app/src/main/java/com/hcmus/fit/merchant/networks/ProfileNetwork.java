package com.hcmus.fit.merchant.networks;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.ui.SettingFragment;
import com.hcmus.fit.merchant.utils.QueryUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileNetwork {

    public static void getWithDraw(SettingFragment fragment) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.WITH_DRAWS, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("get with draw", response);

                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray data = json.getJSONArray("data");

                        boolean payment = false;

                        if (data.length() > 0) {
                            JSONObject reqJson = data.getJSONObject(0);
                            int status = reqJson.getInt("Status");
                            if (status != -1) {
                                payment = true;
                            }
                        } else {
                            payment = true;
                        }

                        if (!payment) {
                            MerchantInfo.getInstance().processWithDraw = true;
                            fragment.updateProcessingWithDraw();
                        } else {
                            MerchantInfo.getInstance().processWithDraw = false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                try {
                    Log.d("get with draw", error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                })
        {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

    public static void postWithDraw(Context context,int money) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.WITH_DRAWS, params);

        StringRequest req = new StringRequest(Request.Method.POST, query,
                response -> {
                    Log.d("post with draw", response);
                },
                error -> {
                try {
                    Log.d("post with draw", error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                })
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    json.put("amount", money);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

    public static void updateSetting(Context context, String openingAt, String closingAt) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.GET_USER_DETAIL, params);

        StringRequest req = new StringRequest(Request.Method.PUT, query,
                response -> {
                    Log.d("update setting", response);
                },
                error -> {
                try {
                    Log.d("update setting", error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                })
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    if (openingAt != null) {
                        json.put("openingAt", openingAt);
                    }

                    if (closingAt != null) {
                        json.put("closingAt", closingAt);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

}
