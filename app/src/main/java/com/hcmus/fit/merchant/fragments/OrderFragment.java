package com.hcmus.fit.merchant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.activities.OrderActivity;
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
        OrderManager.getInstance().setActivity(getActivity());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_main, container, false);
        orderAdapter = new OrderAdapter(getContext(), orderList);
        lvOrder = root.findViewById(R.id.lv_order);
        lvOrder.setAdapter(orderAdapter);

        lvOrder.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            intent.putExtra("title", this.title);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        Log.d("view", "OrderFragment create view " + getResources().getString(title));
        orderAdapter.notifyDataSetChanged();
        setupAdapter();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.notifyDataSetChanged();
    }

    public int getTitle() {
        return title;
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