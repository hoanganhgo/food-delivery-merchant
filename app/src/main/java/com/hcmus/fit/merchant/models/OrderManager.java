package com.hcmus.fit.merchant.models;

import android.widget.BaseAdapter;

import java.util.ArrayList;

public class OrderManager {
    private static OrderManager instance = null;

    private final ArrayList<OrderModel> waitingList = new ArrayList<>();
    private final ArrayList<OrderModel> waitShipperList = new ArrayList<>();
    private final ArrayList<OrderModel> waitDishList = new ArrayList<>();
    private final ArrayList<OrderModel> shippingList = new ArrayList<>();

    private BaseAdapter waitingAdapter;
    private BaseAdapter waitShipperAdapter;
    private BaseAdapter waitDishAdapter;
    private BaseAdapter shippingAdapter;

    private OrderManager() {
        OrderModel orderModel = new OrderModel();
        waitingList.add(orderModel);
        waitingList.add(orderModel);
        waitingList.add(orderModel);
        waitingList.add(orderModel);

        waitShipperList.add(orderModel);
        waitShipperList.add(orderModel);

        waitDishList.add(orderModel);
        waitDishList.add(orderModel);
        waitDishList.add(orderModel);

        shippingList.add(orderModel);
        shippingList.add(orderModel);
        shippingList.add(orderModel);
        shippingList.add(orderModel);
        shippingList.add(orderModel);
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }

        return instance;
    }

    public ArrayList<OrderModel> getWaitingList() {
        return waitingList;
    }

    public ArrayList<OrderModel> getWaitShipperList() {
        return waitShipperList;
    }

    public ArrayList<OrderModel> getWaitDishList() {
        return waitDishList;
    }

    public ArrayList<OrderModel> getShippingList() {
        return shippingList;
    }

    public void setWaitingAdapter(BaseAdapter waitingAdapter) {
        this.waitingAdapter = waitingAdapter;
    }

    public void setWaitShipperAdapter(BaseAdapter waitShipperAdapter) {
        this.waitShipperAdapter = waitShipperAdapter;
    }

    public void setWaitDishAdapter(BaseAdapter waitDishAdapter) {
        this.waitDishAdapter = waitDishAdapter;
    }

    public void setShippingAdapter(BaseAdapter shippingAdapter) {
        this.shippingAdapter = shippingAdapter;
    }
}
