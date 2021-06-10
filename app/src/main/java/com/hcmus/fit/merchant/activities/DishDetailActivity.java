package com.hcmus.fit.merchant.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.adapters.CategoryAdapter;
import com.hcmus.fit.merchant.business.ItemRemoving;
import com.hcmus.fit.merchant.business.ItemShowing;
import com.hcmus.fit.merchant.models.CategoryModel;
import com.hcmus.fit.merchant.models.DishModel;
import com.hcmus.fit.merchant.models.ItemModel;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.models.OptionModel;
import com.hcmus.fit.merchant.networks.DishNetwork;
import com.hcmus.fit.merchant.utils.WidgetUtil;
import com.squareup.picasso.Picasso;

public class DishDetailActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private int contentView = 0;
    private ImageButton btnAvatar;
    private EditText editDishName;
    private EditText editPrice;
    private RelativeLayout rlStatus;
    private TextView tvStatus;
    private Switch btnStatus;
    private Spinner snDishCategory;
    private EditText editDescription;
    private Button btnDeleteDish;
    private Button btnUpdateDish;
    private Button btnAddDish;
    private Button btnAddOption;
    private LinearLayout layoutOption;
    private ImageButton btnAddCategory;

    private CategoryAdapter cateAdapter;

    private DishModel dishModel = new DishModel();

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
        btnAddOption = findViewById(R.id.btn_add_option);
        layoutOption = findViewById(R.id.layout_option);
        rlStatus = findViewById(R.id.rl_status);
        btnAddCategory = findViewById(R.id.btn_add_category);

        Intent intent = getIntent();
        int contentView = intent.getIntExtra("contentView", 0);
        int position = intent.getIntExtra("position", 0);



        btnAvatar.setOnClickListener(v -> {
            this.dispatchTakePictureIntent();
        });

        cateAdapter = new CategoryAdapter(this);
        snDishCategory.setAdapter(cateAdapter);
        DishNetwork.getCategories(this, cateAdapter);

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
            rlStatus.setVisibility(View.GONE);
            btnAddDish.setVisibility(View.VISIBLE);
        } else if (contentView == 1) {
            btnAddDish.setVisibility(View.GONE);
            btnDeleteDish.setVisibility(View.VISIBLE);
            btnUpdateDish.setVisibility(View.VISIBLE);
            rlStatus.setVisibility(View.VISIBLE);
            setContentEditView(position);
        }

        btnAddCategory.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = LayoutInflater.from(this);
            View categoryView = inflater.inflate(R.layout.alert_add_category, null);
            EditText edtCategory = categoryView.findViewById(R.id.edt_category_name);
            Button btnAddCategory = categoryView.findViewById(R.id.btn_add_category);

            btnAddCategory.setOnClickListener(v1 -> {
                DishNetwork.createCategory(this, edtCategory.getText().toString(),
                        cateAdapter, snDishCategory);

                alertDialog.cancel();
            });

            alertDialog.setView(categoryView);
            alertDialog.show();
        });

        btnAddOption.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = LayoutInflater.from(this);
            View optionView = inflater.inflate(R.layout.alert_add_option, null);
            EditText editType = optionView.findViewById(R.id.edt_option_type);
            RadioButton rbChoose1 = optionView.findViewById(R.id.rb_choose_1);
            RadioButton rbChooseMore = optionView.findViewById(R.id.rb_choose_more);
            Button btnAddOption = optionView.findViewById(R.id.btn_add_option);
            alertDialog.setView(optionView);

            OptionModel optionModel = new OptionModel();

            LinearLayout optionParent = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            optionParent.setLayoutParams(params);
            optionParent.setOrientation(LinearLayout.VERTICAL);

            ItemShowing itemShowing = (context) -> {
                AlertDialog alertDialogItem = new AlertDialog.Builder(context).create();
                LayoutInflater inflaterItem = LayoutInflater.from(context);
                View itemView = inflaterItem.inflate(R.layout.alert_add_item, null);
                TextView tvOptionName = itemView.findViewById(R.id.tv_option_name);
                EditText editName = itemView.findViewById(R.id.edt_item_name);
                EditText editPrice = itemView.findViewById(R.id.edt_item_price);
                Button btnAddItem = itemView.findViewById(R.id.btn_add_item);
                tvOptionName.setText(optionModel.getName());
                ItemModel itemModel = new ItemModel();

                ItemRemoving itemRemoving = () -> {
                    optionModel.remove(itemModel);
                };

                btnAddItem.setOnClickListener(v1 -> {
                    RelativeLayout rlItem = WidgetUtil.getItemType(getApplicationContext(),
                            editName.getText().toString(), editPrice.getText().toString(), itemRemoving);
                    optionParent.addView(rlItem);

                    itemModel.setMaxQuantity(100);
                    itemModel.setName(editName.getText().toString());
                    itemModel.setPrice(Integer.parseInt(editPrice.getText().toString()));
                    optionModel.addItem(itemModel);

                    alertDialogItem.cancel();
                });

                alertDialogItem.setView(itemView);
                alertDialogItem.show();
            };

            btnAddOption.setOnClickListener(v1 -> {
                RelativeLayout rlOption = WidgetUtil.getOptionType(this,
                        editType.getText().toString(), itemShowing);
                optionParent.addView(rlOption);

                optionModel.setName(editType.getText().toString());
                optionModel.setSelectMore(!rbChoose1.isChecked());

                dishModel.addOption(optionModel);

                alertDialog.cancel();
            });

            layoutOption.addView(optionParent);
            alertDialog.show();
        });

        btnAddDish.setOnClickListener(v -> {
            if (verifyAddDish()) {
                dishModel.setName(editDishName.getText().toString());
                CategoryModel categoryModel = (CategoryModel) snDishCategory.getSelectedItem();
                dishModel.setCategoryId(categoryModel.getId());
                dishModel.setCategory(categoryModel.getName());
                dishModel.setPrice(Integer.parseInt(editPrice.getText().toString()));
                dishModel.checkMaxSelect();
                DishNetwork.postDish(this, dishModel);
            }
        });
    }

    private void setContentEditView(int position) {
        DishModel dishModel = MerchantInfo.getInstance().getDishList().get(position);
        Picasso.with(this).load(dishModel.getAvatarUri()).into(btnAvatar);
        editDishName.setText(dishModel.getName());
        editPrice.setText(String.valueOf(dishModel.getPrice()));
        int index = MerchantInfo.getInstance().getIndexCategory(dishModel.getCategoryId());
        snDishCategory.setSelection(index);
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
            dishModel.setAvatar(cropImage);
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

    private boolean verifyAddDish() {
        if (dishModel.getAvatar() == null) {
            Toast.makeText(this, getResources().getString(R.string.please_add_avatar), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editDishName.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_dish_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_price), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
