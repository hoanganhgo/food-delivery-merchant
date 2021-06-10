package com.hcmus.fit.merchant.models;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.hcmus.fit.merchant.R;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderManager {
    private static OrderManager instance = null;

    private final ArrayList<OrderModel> waitingList = new ArrayList<>();
    private final ArrayList<OrderModel> waitShipperList = new ArrayList<>();
    private final ArrayList<OrderModel> waitDishList = new ArrayList<>();
    private final ArrayList<OrderModel> shippingList = new ArrayList<>();

    private Activity activity;
    private BaseAdapter waitingAdapter;
    private BaseAdapter waitShipperAdapter;
    private BaseAdapter waitDishAdapter;
    private BaseAdapter shippingAdapter;

    private OrderManager() {

    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }

        return instance;
    }

    public ArrayList<OrderModel> getOrderList(int title) {
        switch (title) {
            case R.string.waiting:
                return waitingList;

            case R.string.wait_shipper:
                return waitShipperList;

            case R.string.wait_dish:
                return waitDishList;

            case R.string.shipping:
                return shippingList;
        }

        return new ArrayList<>();
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

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public void notifyDataChanged(int title) {
        if (this.activity != null && !this.activity.isDestroyed()) {
            this.activity.runOnUiThread(() -> {
                switch (title) {
                    case R.string.waiting:
                        if (this.waitingAdapter != null) {
                            this.waitingAdapter.notifyDataSetChanged();;
                        }
                        break;

                    case R.string.wait_shipper:
                        if (this.waitShipperAdapter != null) {
                            this.waitShipperAdapter.notifyDataSetChanged();;
                        }
                        break;

                    case R.string.wait_dish:
                        if (this.waitDishAdapter != null) {
                            this.waitDishAdapter.notifyDataSetChanged();;
                        }
                        break;

                    case R.string.shipping:
                        if (this.shippingAdapter != null) {
                            this.shippingAdapter.notifyDataSetChanged();;
                        }
                        break;
                }
            });
        }
    }

    public void shipperReceivedOrder(String orderId) {
        Iterator<OrderModel> it = this.waitShipperList.iterator();

        while (it.hasNext()) {
            OrderModel orderModel = it.next();

            if (orderModel.getOrderId().equals(orderId)) {
                it.remove();
                this.waitDishList.add(0, orderModel);
                notifyDataChanged(R.string.wait_shipper);
                notifyDataChanged(R.string.wait_dish);
            }
        }
    }

    public void shipperGetOrder(String orderId) {
        Iterator<OrderModel> it = this.waitDishList.iterator();

        while (it.hasNext()) {
            OrderModel orderModel = it.next();

            if (orderModel.getOrderId().equals(orderId)) {
                it.remove();
                this.shippingList.add(0, orderModel);
                notifyDataChanged(R.string.wait_dish);
                notifyDataChanged(R.string.shipping);
            }
        }
    }

    public void shipperCompleteOrder(String orderId) {
        Iterator<OrderModel> it = this.shippingList.iterator();

        while (it.hasNext()) {
            OrderModel orderModel = it.next();

            if (orderModel.getOrderId().equals(orderId)) {
                it.remove();
                notifyDataChanged(R.string.shipping);
                //add history
            }
        }
    }

    public void addShipper(String orderId, ShipperModel shipper) {
        try {
            for (OrderModel order : this.waitDishList) {
                if (order.getOrderId().equals(orderId)) {
                    order.setShipper(shipper);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
