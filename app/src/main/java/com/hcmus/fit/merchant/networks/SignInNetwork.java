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
import com.hcmus.fit.merchant.MainActivity;
import com.hcmus.fit.merchant.activities.OTPLoginActivity;
import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.models.AddressModel;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.utils.JWTUtils;
import com.hcmus.fit.merchant.utils.QueryUtil;
import com.hcmus.fit.merchant.utils.StorageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInNetwork {

    public static void sendPhoneOTP(Context context, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SEND_PHONE_OTP,
                response -> {
                    Log.d("sendPhoneOTP", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            Intent intent = new Intent(context, OTPLoginActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("OTP", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                return params;
            }
        };

        queue.add(req);
    }

    public static void authPhoneVerify(Context context, String phoneNumber, String OTP) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.AUTH_PHONE_VERIFY,
                response -> {
                    Log.d("Verify", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");
                            MerchantInfo.getInstance().setToken(token);

                            //save token
                            StorageUtil.putString(context, StorageUtil.TOKEN_KEY, token);

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("Verify", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                params.put("otp", OTP);
                return params;
            }
        };

        queue.add(req);
    }

    public static void getMerchantInfo(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest req = new StringRequest(Request.Method.GET, API.GET_USER_INFO,
                response -> {
                    Log.d("merchantInfo", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONArray dataArr = json.getJSONArray("data");
                            JSONObject data = dataArr.getJSONObject(0);
                            String id = data.getString("id");
                            String name = data.getString("Name");
                            String avatar = data.getString("Avatar");

                            MerchantInfo.getInstance().setId(id);
                            MerchantInfo.getInstance().setName(name);
                            MerchantInfo.getInstance().setAvatar(avatar);

                            getMerchantDetail(context, id);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("merchantInfo", error.getMessage()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return params;
            }
        };

        queue.add(req);
    }

    public static void getMerchantDetail(Context context, String merchantId) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", merchantId);
        String query = QueryUtil.createQuery(API.GET_USER_DETAIL, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("merchantInfo", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String phoneNumber = data.getString("Phone");
                            String fullAddress = data.getString("FullAddress");
                            JSONObject locationJson = data.getJSONObject("Geolocation");
                            double longitude = locationJson.getDouble("longitude");
                            double latitude = locationJson.getDouble("latitude");

                            MerchantInfo.getInstance().setPhoneNumber(phoneNumber);
                            AddressModel addressModel = new AddressModel(fullAddress, latitude, longitude);
                            MerchantInfo.getInstance().setAddress(addressModel);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("merchantInfo", error.getMessage()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return params;
            }
        };

        queue.add(req);
    }


}
