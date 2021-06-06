package com.hcmus.fit.merchant.models;

public class CategoryModel {
    private final String id;
    private final String name;

    public CategoryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
