package com.hcmus.fit.merchant.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.R;

public class OrderWaitingActivity extends AppCompatActivity {

    private LinearLayout lnOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_waiting);

        lnOrder = findViewById(R.id.ln_order_waiting);

        for (int i = 0; i < 3; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newPlayerRow = inflater.inflate(R.layout.widget_dish, null);
            lnOrder.addView(newPlayerRow);
        }
    }
}
