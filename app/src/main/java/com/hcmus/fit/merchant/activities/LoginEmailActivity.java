package com.hcmus.fit.merchant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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


public class LoginEmailActivity extends AppCompatActivity {
    private Button btnSignInPhone;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        btnSignInPhone = findViewById(R.id.sign_in_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
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

        btnSignInPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                return;
            }

            SignInNetwork.loginWithEmail(this, edtEmail.getText().toString(),
                    edtPassword.getText().toString());
        });

    }
}
