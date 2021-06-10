package com.hcmus.fit.merchant.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.DishOrder;
import com.hcmus.fit.merchant.models.OrderManager;
import com.hcmus.fit.merchant.models.OrderModel;
import com.hcmus.fit.merchant.models.ShipperModel;
import com.hcmus.fit.merchant.networks.MySocket;
import com.hcmus.fit.merchant.utils.AppUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderActivity extends AppCompatActivity {
    private int title;
    private OrderModel orderModel;

    private LinearLayout lnOrder;
    private LinearLayout lnShipper;
    private TextView tvOrderId;
    private TextView tvTotal;
    private TextView tvCustomerName;
    private TextView tvAddress;
    private TextView tvCustomerNote;
    private Button btnCallCustomer;
    private Button btnReceive;
    private Button btnCancel;
    private CircleImageView ivShipper;
    private TextView tvShipperName;
    private TextView tvShipperPhone;
    private Button btnCallShipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tvOrderId = findViewById(R.id.tv_order_id);
        lnOrder = findViewById(R.id.ln_order);
        lnShipper = findViewById(R.id.ln_shipper);
        tvTotal = findViewById(R.id.tv_total);
        tvCustomerName = findViewById(R.id.tv_customer_name);
        tvAddress = findViewById(R.id.tv_customer_address);
        tvCustomerNote = findViewById(R.id.tv_customer_note);
        btnCallCustomer = findViewById(R.id.btn_call_customer);
        btnReceive = findViewById(R.id.btn_receive);
        btnCancel = findViewById(R.id.btn_cancel);
        ivShipper = findViewById(R.id.iv_shipper_avatar);
        tvShipperName = findViewById(R.id.tv_shipper_name);
        tvShipperPhone = findViewById(R.id.tv_shipper_phone);
        btnCallShipper = findViewById(R.id.btn_call_shipper);


        Intent intent = getIntent();
        this.title = intent.getIntExtra("title",R.string.waiting);
        int position = intent.getIntExtra("position", 0);
        this.orderModel = OrderManager.getInstance().getOrderList(title).get(position);

        tvOrderId.setText("#" + orderModel.getOrderId());

        for (int i = 0; i < orderModel.getDishOrderList().size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.widget_dish, null);
            TextView tvDishName = row.findViewById(R.id.tv_dish_name);
            TextView tvQuantity = row.findViewById(R.id.tv_quantity);
            TextView tvPrice = row.findViewById(R.id.tv_dish_price);
            TextView tvTotal = row.findViewById(R.id.tv_total);
            TextView tvOptionLabel = row.findViewById(R.id.tv_option_label);
            TextView tvOptions = row.findViewById(R.id.tv_options);

            DishOrder dishOrder = orderModel.getDishOrderList().get(i);
            tvDishName.setText((i + 1) + ". " + dishOrder.dishModel.getName());
            tvQuantity.setText(String.valueOf(dishOrder.num));
            tvPrice.setText(AppUtil.convertCurrency(dishOrder.dishModel.getPrice()));
            tvTotal.setText(AppUtil.convertCurrency(dishOrder.getTotalPrice()));
            tvOptions.setText(dishOrder.dishModel.getOptions());
            if (dishOrder.dishModel.getOptions().isEmpty()) {
                tvOptionLabel.setVisibility(View.GONE);
                tvOptions.setVisibility(View.GONE);
            }

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

        if (this.title == R.string.waiting) {
            btnReceive.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            lnShipper.setVisibility(View.GONE);

            btnReceive.setOnClickListener(v -> {
                MySocket.confirmOrder(orderModel.getOrderId());
                OrderManager.getInstance().getWaitingList().remove(orderModel);
                OrderManager.getInstance().getWaitShipperList().add(0, orderModel);
                onBackPressed();
            });

            btnCancel.setOnClickListener(v -> {
                MySocket.cancelOrder(orderModel.getOrderId());
                OrderManager.getInstance().getWaitingList().remove(orderModel);
                onBackPressed();
            });
        } else if (this.title == R.string.wait_shipper) {
            btnReceive.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            lnShipper.setVisibility(View.GONE);

        } else if (this.title == R.string.wait_dish) {
            btnReceive.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            lnShipper.setVisibility(View.VISIBLE);

            ShipperModel shipper = this.orderModel.getShipper();
            Picasso.with(this).load(shipper.getAvatar()).into(ivShipper);
            tvShipperName.setText(shipper.getName());
            tvShipperPhone.setText(shipper.getPhone());

            btnCallShipper.setOnClickListener(v -> {
                Uri number = Uri.parse("tel:" + shipper.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            });

        } else {
            btnReceive.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            lnShipper.setVisibility(View.GONE);
        }


    }
}
