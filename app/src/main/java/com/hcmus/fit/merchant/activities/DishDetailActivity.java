package com.hcmus.fit.merchant.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.MerchantModel;

public class DishDetailActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private int contentView = 0;
    private ImageButton btnAvatar;
    private EditText editDishName;
    private EditText editPrice;
    private TextView tvStatus;
    private Switch btnStatus;
    private Spinner snDishCategory;
    private EditText editDescription;
    private Button btnDeleteDish;
    private Button btnUpdateDish;
    private Button btnAddDish;

    private MerchantModel merchant = new MerchantModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dish_detail);

        btnAvatar = findViewById(R.id.btn_avatar_dish);
        editDishName = findViewById(R.id.edit_dish_name);
        editPrice = findViewById(R.id.edit_price);
        tvStatus = findViewById(R.id.tv_status);
        btnStatus = findViewById(R.id.btn_status);
        snDishCategory = findViewById(R.id.sn_category);
        editDescription = findViewById(R.id.edit_description);
        btnDeleteDish = findViewById(R.id.btn_delete_dish);
        btnUpdateDish = findViewById(R.id.btn_update_dish);
        btnAddDish = findViewById(R.id.btn_add_dish);

        btnAvatar.setOnClickListener(v -> {
            this.dispatchTakePictureIntent();
        });

        btnStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvStatus.setText(R.string.available);
                tvStatus.setTextColor(getResources().getColor(R.color.green_light));
            } else {
                tvStatus.setText(R.string.unavailable);
                tvStatus.setTextColor(getResources().getColor(R.color.red));
            }
        });

        if (contentView == 0) {
            btnDeleteDish.setVisibility(View.GONE);
            btnUpdateDish.setVisibility(View.GONE);
            btnAddDish.setVisibility(View.VISIBLE);
        } else if (contentView == 1) {
            btnAddDish.setVisibility(View.GONE);
            btnDeleteDish.setVisibility(View.VISIBLE);
            btnUpdateDish.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                merchant.getCategories());
        snDishCategory.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            int width = imageBitmap.getWidth();
            int height = imageBitmap.getHeight();

            int narrowSize = Math.min(width, height);
            int differ = (int)Math.abs((imageBitmap.getHeight() - imageBitmap.getWidth())/2.0f);
            width  = (width  == narrowSize) ? 0 : differ;
            height = (width == 0) ? differ : 0;
            Bitmap cropImage = Bitmap.createBitmap(imageBitmap, width, height, narrowSize, narrowSize);
            btnAvatar.setImageBitmap(cropImage);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
