package com.hcmus.fit.merchant.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderModel {
    private String orderId;
    private int subTotal;
    private Calendar calendar;

    private String customerName;
    private String fullAddress;
    private String customerNote;
    private String customerPhone;

    private ArrayList<DishOrder> dishOrderList = new ArrayList<>();

    private ShipperModel shipper;

    private double distance;
    private long completeAt;

    public OrderModel() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ArrayList<DishOrder> getDishOrderList() {
        return dishOrderList;
    }

    public void setDishOrderList(ArrayList<DishOrder> dishOrderList) {
        this.dishOrderList = dishOrderList;
    }

    public ShipperModel getShipper() {
        return shipper;
    }

    public void setShipper(ShipperModel shipper) {
        this.shipper = shipper;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(long completeAt) {
        this.completeAt = completeAt;
    }

    public void createDishOrderList(JSONArray foodArray) throws JSONException {
        for (int i = 0; i < foodArray.length(); i++) {
            JSONObject foodJson = foodArray.getJSONObject(i);

            DishModel dishModel = new DishModel();
            dishModel.setId(foodJson.getString("id"));
            dishModel.setPrice(foodJson.getInt("Price"));
            dishModel.setName(foodJson.getString("Name"));
            dishModel.setOptions(foodJson.getString("Options"));

            DishOrder dishOrder = new DishOrder(dishModel, foodJson.getInt("Quantity"));

            this.dishOrderList.add(dishOrder);
        }
    }

    public int getDishNum() {
        int num = 0;

        for (DishOrder dishOrder : dishOrderList) {
            num += dishOrder.num;
        }

        return num;
    }
}
