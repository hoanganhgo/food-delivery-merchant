package com.hcmus.fit.merchant.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.networks.ProfileNetwork;
import com.hcmus.fit.merchant.networks.SignInNetwork;
import com.hcmus.fit.merchant.utils.AppUtil;
import com.squareup.picasso.Picasso;

public class SettingFragment extends Fragment {
    private ImageView ivAvatar;
    private TextView tvMerchantName;
    private TextView tvAddress;
    private TextView tvWallet;
    private Button btnOpening;
    private TextView tvOpening;
    private Button btnClosing;
    private TextView tvClosing;
    private Button btnSubmitMoney;
    private TextView tvProcessing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        ivAvatar = root.findViewById(R.id.iv_avatar);
        tvMerchantName = root.findViewById(R.id.tv_merchant_name);
        tvAddress = root.findViewById(R.id.tv_address);
        tvWallet = root.findViewById(R.id.tv_wallet);
        btnOpening = root.findViewById(R.id.btn_opening);
        tvOpening = root.findViewById(R.id.tv_opening);
        btnClosing = root.findViewById(R.id.btn_closing);
        tvClosing = root.findViewById(R.id.tv_closing);
        btnSubmitMoney = root.findViewById(R.id.btn_submit_money);
        tvProcessing = root.findViewById(R.id.tv_processing);

        MerchantInfo merchant = MerchantInfo.getInstance();

        Picasso.with(getContext()).load(merchant.getAvatar()).into(ivAvatar);
        tvMerchantName.setText(merchant.getName());
        tvAddress.setText(merchant.getAddress().getFullAddress());
        tvWallet.setText(AppUtil.convertCurrency(merchant.getWallet()));
        tvOpening.setText(merchant.getOpening());
        tvClosing.setText(merchant.getClosing());

        btnOpening.setOnClickListener(v -> {
            showPopup(R.string.open_time);
        });

        btnClosing.setOnClickListener(v -> {
            showPopup(R.string.close_time);
        });

        btnSubmitMoney.setOnClickListener(v -> {
            if (MerchantInfo.getInstance().processWithDraw) {
                return;
            }

            showPopup(R.string.with_draws);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ProfileNetwork.getWithDraw(this);
        SignInNetwork.getUserWallet(getContext());
        tvWallet.setText(AppUtil.convertCurrency(MerchantInfo.getInstance().getWallet()));
    }

    private void showPopup(int title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getResources().getString(title));
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getResources().getString(R.string.confirm),
                (dialog, which) -> {
                    if (input.getText().toString().isEmpty()) {
                        return;
                    }

                    switch (title) {
                        case R.string.open_time:
                            if (input.getText().toString().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                                ProfileNetwork.updateSetting(getContext(), input.getText().toString(), null);
                                MerchantInfo.getInstance().setOpening(input.getText().toString());
                                tvOpening.setText(MerchantInfo.getInstance().getOpening());
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.notify_value_invalid), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;

                        case R.string.close_time:
                            if (input.getText().toString().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                                ProfileNetwork.updateSetting(getContext(), null, input.getText().toString());
                                MerchantInfo.getInstance().setClosing(input.getText().toString());
                                tvClosing.setText(MerchantInfo.getInstance().getClosing());
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.notify_value_invalid), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;

                        case R.string.with_draws:
                            int money = Integer.parseInt(input.getText().toString());
                            if (MerchantInfo.getInstance().getWallet() < money) {
                                Toast.makeText(getContext(), getResources().getString(R.string.notify_not_enough_money), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ProfileNetwork.postWithDraw(getContext(), money);
                            MerchantInfo.getInstance().processWithDraw = true;
                            updateProcessingWithDraw();
                            break;

                    }

                    dialog.cancel();
                });

        alertDialog.setNegativeButton(getResources().getString(R.string.cancel),
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    public void updateProcessingWithDraw() {
        if (MerchantInfo.getInstance().processWithDraw) {
            tvProcessing.setText(getResources().getString(R.string.processing));
        } else {
            tvProcessing.setText("");
        }
    }
}