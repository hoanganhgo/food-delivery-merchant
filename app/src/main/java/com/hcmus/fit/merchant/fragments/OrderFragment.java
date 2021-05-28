package com.hcmus.fit.merchant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.activities.OrderWaitDishActivity;
import com.hcmus.fit.merchant.activities.OrderWaitShipperActivity;
import com.hcmus.fit.merchant.activities.OrderWaitingActivity;
import com.hcmus.fit.merchant.adapters.OrderAdapter;
import com.hcmus.fit.merchant.models.OrderManager;
import com.hcmus.fit.merchant.models.OrderModel;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderFragment extends Fragment {
    private final int title;
    private List<OrderModel> orderList;
    private ListView lvOrder;
    private OrderAdapter orderAdapter;

    public OrderFragment(int title) {
        this.title = title;

        switch (title) {
            case R.string.waiting:
                this.orderList = OrderManager.getInstance().getWaitingList();
                break;

            case R.string.wait_shipper:
                this.orderList = OrderManager.getInstance().getWaitShipperList();
                break;

            case R.string.wait_dish:
                this.orderList = OrderManager.getInstance().getWaitDishList();
                break;

            case R.string.shipping:
                this.orderList = OrderManager.getInstance().getShippingList();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_main, container, false);
        orderAdapter = new OrderAdapter(getContext(), orderList);
        lvOrder = root.findViewById(R.id.lv_order);
        lvOrder.setAdapter(orderAdapter);

        Class<?> orderDetail = getClassOrderDetail();
        lvOrder.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), orderDetail);
            startActivity(intent);
        });

        orderAdapter.notifyDataSetChanged();
        setupAdapter();

        return root;
    }

    public int getTitle() {
        return title;
    }

    private Class<?> getClassOrderDetail() {
        switch (title) {
            case R.string.waiting:
                return OrderWaitingActivity.class;

            case R.string.wait_shipper:
                return OrderWaitShipperActivity.class;

            case R.string.wait_dish:
                return OrderWaitDishActivity.class;

            case R.string.shipping:
                return OrderWaitShipperActivity.class;
        }

        return OrderWaitingActivity.class;
    }

    private void setupAdapter() {
        switch (title) {
            case R.string.waiting:
                OrderManager.getInstance().setWaitingAdapter(orderAdapter);
                break;

            case R.string.wait_shipper:
                OrderManager.getInstance().setWaitShipperAdapter(orderAdapter);
                break;

            case R.string.wait_dish:
                OrderManager.getInstance().setWaitDishAdapter(orderAdapter);
                break;

            case R.string.shipping:
                OrderManager.getInstance().setShippingAdapter(orderAdapter);
                break;
        }
    }
}