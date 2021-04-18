package com.hcmus.fit.merchant.models;

import java.util.ArrayList;

public class MerchantModel {
    private ArrayList<String> categories;

    public MerchantModel() {
        categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Drink");
        categories.add("Fast Food");
        categories.add("Chicken");
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
