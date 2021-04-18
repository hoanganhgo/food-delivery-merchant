package com.hcmus.fit.merchant.models;

public class DishModel {
    private int id;
    private String name;
    private String avatar;
    private String category;
    private int status;
    private int price;

    public DishModel(int id, String name, String avatar, String category, int status, int price) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.category = category;
        this.status = status;
        this.price = price;
    }
}
