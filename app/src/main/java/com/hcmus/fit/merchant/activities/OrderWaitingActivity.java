package com.hcmus.fit.merchant.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.constant.EventConstant;
import com.hcmus.fit.merchant.models.DishOrder;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.models.OrderManager;
import com.hcmus.fit.merchant.models.OrderModel;
import com.hcmus.fit.merchant.networks.MySocket;
import com.hcmus.fit.merchant.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderWaitingActivity extends AppCompatActivity {

    private LinearLayout lnOrder;
    private TextView tvOrderId;
    private TextView tvTotal;
    private TextView tvCustomerName;
    private TextView tvAddress;
    private TextView tvCustomerNote;
    private Button btnCallCustomer;
    private Button btnReceive;
    private Button btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_waiting);

        tvOrderId = findViewById(R.id.tv_order_id);
        lnOrder = findViewById(R.id.ln_order_waiting);
        tvTotal = findViewById(R.id.tv_total);
        tvCustomerName = findViewById(R.id.tv_customer_name);
        tvAddress = findViewById(R.id.tv_customer_address);
        tvCustomerNote = findViewById(R.id.tv_customer_note);
        btnCallCustomer = findViewById(R.id.btn_call_customer);
        btnReceive = findViewById(R.id.btn_receive);
        btnCancel = findViewById(R.id.btn_cancel);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        OrderModel orderModel = OrderManager.getInstance().getWaitingList().get(position);

        tvOrderId.setText("#" + orderModel.getOrderId());

        for (int i = 0; i < orderModel.getDishOrderList().size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.widget_dish, null);
            TextView tvDishName = row.findViewById(R.id.tv_dish_name);
            TextView tvQuantity = row.findViewById(R.id.tv_quantity);
            TextView tvPrice = row.findViewById(R.id.tv_dish_price);
            TextView tvTotal = row.findViewById(R.id.tv_total);

            DishOrder dishOrder = orderModel.getDishOrderList().get(i);
            tvDishName.setText((i + 1) + ". " + dishOrder.dishModel.getName());
            tvQuantity.setText(String.valueOf(dishOrder.num));
            tvPrice.setText(AppUtil.convertCurrency(dishOrder.dishModel.getPrice()));
            tvTotal.setText(AppUtil.convertCurrency(dishOrder.getTotalPrice()));

            lnOrder.addView(row);
        }

        tvTotal.setText(AppUtil.convertCurrency(orderModel.getSubTotal()));
        tvCustomerName.setText(orderModel.getCustomerName());
        tvAddress.setText(orderModel.getFullAddress());
        tvCustomerNote.setText(orderModel.getCustomerNote());

        btnCallCustomer.setOnClickListener(v -> {
            Uri number = Uri.parse("tel:" + orderModel.getCustomerPhone());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        });

        btnReceive.setOnClickListener(v -> {
            MySocket.confirmOrder(orderModel.getOrderId());
            OrderManager.getInstance().getWaitingList().remove(orderModel);
            OrderManager.getInstance().getWaitShipperList().add(orderModel);
            onBackPressed();
        });

        btnCancel.setOnClickListener(v -> {
            MySocket.cancelOrder(orderModel.getOrderId());
            OrderManager.getInstance().getWaitingList().remove(orderModel);
            onBackPressed();
        });
    }
}
