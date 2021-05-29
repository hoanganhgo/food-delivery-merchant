package com.hcmus.fit.merchant.models;

public class DishModel {
    private String id;
    private String name;
    private String avatar;
    private String category;
    private int price;

    public DishModel() {
    }

    public DishModel(String id, String name, String avatar, String category, int price) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
