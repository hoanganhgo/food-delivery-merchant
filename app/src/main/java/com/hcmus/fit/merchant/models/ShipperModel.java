package com.hcmus.fit.merchant.models;

public class ShipperModel {
    private String id;
    private String avatar;
    private String name;
    private String phone;

    public ShipperModel(String id, String avatar, String name, String phone) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
