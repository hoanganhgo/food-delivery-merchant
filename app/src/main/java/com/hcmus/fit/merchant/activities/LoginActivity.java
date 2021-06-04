package com.hcmus.fit.merchant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.MainActivity;
import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.networks.SignInNetwork;
import com.hcmus.fit.merchant.utils.JWTUtils;
import com.hcmus.fit.merchant.utils.StorageUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private final int SIZE_NUMBER = 10;
    private EditText edtPhoneNumber;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        btnLogin = findViewById(R.id.btn_login);

        String token = StorageUtil.getString(this, StorageUtil.TOKEN_KEY);
        if (token != null) {
            JSONObject jsonBody = JWTUtils.decoded(token);
            try {
                long exp = jsonBody.getLong("exp") * 1000;
                if (exp > System.currentTimeMillis()) {
                    MerchantInfo.getInstance().setToken(token);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        btnLogin.setOnClickListener(v -> {
            if (edtPhoneNumber.length() < SIZE_NUMBER) {
                Toast.makeText(this, R.string.notify_phone_number, Toast.LENGTH_LONG).show();
                return;
            }

            MerchantInfo.getInstance().setPhoneNumber(edtPhoneNumber.getText().toString());
            Log.d("phone_number", edtPhoneNumber.getText().toString());

            SignInNetwork.sendPhoneOTP(this, MerchantInfo.getInstance().getPhoneNumber());
        });

    }
}
