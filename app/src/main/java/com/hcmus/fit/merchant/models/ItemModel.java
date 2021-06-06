package com.hcmus.fit.merchant.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel {
    private int id;
    private int maxQuantity;
    private int price;
    private String name;

    public ItemModel() {
    }

    public ItemModel(int id, int maxQuantity, int price, String name) {
        this.id = id;
        this.maxQuantity = maxQuantity;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemWithJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.maxQuantity = json.getInt("MaxQuantity");
        this.price = json.getInt("OriginalPrice");
        this.name = json.getString("Name");
    }

    public JSONObject createJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("maxquantity", this.maxQuantity);
        return json;
    }
}
