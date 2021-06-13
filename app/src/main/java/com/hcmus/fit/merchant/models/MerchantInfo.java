package com.hcmus.fit.merchant.models;

import java.util.ArrayList;

public class MerchantInfo {
    private static MerchantInfo instance = null;

    private String id = "";
    private String name = "";
    private String phoneNumber = "";
    private String email = "";
    private String avatar = "";
    private AddressModel address;
    private final ArrayList<CategoryModel> categories = new ArrayList<>();
    private final ArrayList<DishModel> dishList = new ArrayList<>();
    private String token = "";

    private MerchantInfo() {

    }

    public static MerchantInfo getInstance() {
        if (instance == null) {
            instance = new MerchantInfo();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwYjhhY2Y3OGE2NGE0MDZhODQ3ODJkYyIsInJvbGUiOiJtZXJjaGFudCIsImlhdCI6MTYyMjAzOTU2NCwiZXhwIjoxNjUzNzQ3NTgyLjEzM30.Vq0U2GcGLvIfbV0vbdxcaA61Lv4_5VcJn4QrhzlUWtA";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public int getIndexCategory(String categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(categoryId)) {
                return i;
            }
        }

        return 0;
    }

    public int getDishListSize() {
        return dishList.size();
    }

    public DishModel getDishByIndex(int index) {
        return dishList.get(index);
    }

    public void deleteDishByIndex(int index) {
        dishList.remove(index);
    }

    public void addDishModel(DishModel dishModel) {
        dishList.add(dishModel);
    }

    public void addFirstDish(DishModel dishModel) {
        dishList.add(0, dishModel);
    }

    public void clearDishList() {
        dishList.clear();
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public void clear() {
        instance = null;
    }
}
