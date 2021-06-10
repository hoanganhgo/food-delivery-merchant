package com.hcmus.fit.merchant.networks;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.merchant.adapters.CategoryAdapter;
import com.hcmus.fit.merchant.adapters.DishAdapter;
import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.models.CategoryModel;
import com.hcmus.fit.merchant.models.DishModel;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.utils.QueryUtil;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DishNetwork {
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

    public static void postDish(Context context, DishModel dishModel) {
        RequestQueue queue = Volley.newRequestQueue(context);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        dishModel.getAvatar().compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] bitMapData = stream.toByteArray();
        ContentType contentType = ContentType.create("image/png");
        String fileName = "merchant_dish.png";

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("image", bitMapData, contentType, fileName);

        JSONObject dishJson = null;
        try {
            dishJson = dishModel.createJson();
            builder.addTextBody("data", dishJson.toString());
            Log.d("data json", dishJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        HttpEntity httpEntity = builder.build();

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        params.put("categoryID", dishModel.getCategoryId());
        String query = QueryUtil.createQuery(API.CREATE_NEW_DISH, params);

        StringRequest req = new StringRequest(Request.Method.POST, query,
                response -> {
                    Log.d("postDish", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String id = data.getString("_id");
                            String name = data.getString("Name");
                            String categoryId = data.getString("FoodCategory");
                            int price = data.getInt("OriginalPrice");
                            String avatarUri = data.getString("Avatar");
                            JSONArray jsonArray = data.getJSONArray("Options");

                            DishModel dish = new DishModel();
                            dish.setId(id);
                            dish.setName(name);
                            dish.setCategoryId(categoryId);
                            dish.setPrice(price);
                            dish.setAvatarUri(avatarUri);
                            dish.createListOption(jsonArray);
                            MerchantInfo.getInstance().getDishList().add(0, dish);
                            ((AppCompatActivity)context).onBackPressed();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("postDish", error.getMessage()))
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    httpEntity.writeTo(bos);
                } catch (IOException e) {
                    VolleyLog.e("IOException writing to ByteArrayOutputStream");
                }
                return bos.toByteArray();
            }

            @Override
            public String getBodyContentType() {
                return httpEntity.getContentType().getValue();
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

    public static void getCategories(Context context, CategoryAdapter cateAdapter) {
        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.CREATE_CATEGORY, params);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("category", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            JSONArray data = json.getJSONArray("data");
                            ArrayList<CategoryModel> categories = MerchantInfo.getInstance().getCategories();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject categoryJson = data.getJSONObject(i);
                                String id = categoryJson.getString("id");
                                String name = categoryJson.getString("Name");
                                CategoryModel categoryModel = new CategoryModel(id, name);
                                categories.add(categoryModel);
                            }

                            cateAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("category", error.getMessage()))
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

    public static void createCategory(Context context, String category, CategoryAdapter cateAdapter, Spinner cateSpin) {
        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.CREATE_CATEGORY, params);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, query,
                response -> {
                    Log.d("category", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String id = data.getString("_id");
                            CategoryModel categoryModel = new CategoryModel(id, category);
                            MerchantInfo.getInstance().getCategories().add(categoryModel);
                            cateSpin.setSelection(MerchantInfo.getInstance().getCategories().size());
                            cateAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("category", error.getMessage()))
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    json.put("name", category);
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
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + MerchantInfo.getInstance().getToken());
                return params;
            }
        };

        queue.add(req);
    }

    public static void getFoods(Context context, DishAdapter dishAdapter) {
        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", MerchantInfo.getInstance().getId());
        String query = QueryUtil.createQuery(API.GET_FOODS, params);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("foods", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            JSONArray data = json.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject categoryJson = data.getJSONObject(i);
                                String categoryName = categoryJson.getString("Name");
                                String categoryId = categoryJson.getString("id");

                                JSONArray foodArr = categoryJson.getJSONArray("Foods");

                                for (int j = 0; j < foodArr.length(); j++) {
                                    JSONObject foodJson = foodArr.getJSONObject(j);
                                    String foodName = foodJson.getString("Name");
                                    String foodAvatar = foodJson.getString("Avatar");
                                    int price = foodJson.getInt("OriginalPrice");
                                    JSONArray optionArr = foodJson.getJSONArray("Options");

                                    DishModel dishModel = new DishModel();
                                    dishModel.setCategoryId(categoryId);
                                    dishModel.setCategory(categoryName);
                                    dishModel.setName(foodName);
                                    dishModel.setAvatarUri(foodAvatar);
                                    dishModel.setPrice(price);
                                    dishModel.createListOption(optionArr);
                                    MerchantInfo.getInstance().getDishList().add(dishModel);
                                }
                            }

                            dishAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("foods", error.getMessage()))
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
