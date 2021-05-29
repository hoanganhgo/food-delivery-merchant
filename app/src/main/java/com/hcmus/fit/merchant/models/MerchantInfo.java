package com.hcmus.fit.merchant.models;

public class MerchantInfo {
    private static MerchantInfo instance = null;

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String email = "";
    private String avatar = "";
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwYTc3MWUyYjcyNDVmMjgxODJjZjgyZiIsImV4cCI6MTY1MzgxNTUyMy4wNjMsInJvbGUiOiJtZXJjaGFudCIsImlhdCI6MTYyMjA0MTc2N30.ZDyLONmSl3VYEdyoVvZByA_4jqcS8JTwVzI6vgA_VpI";
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void clear() {
        instance = null;
    }
}
