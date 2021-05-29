package com.hcmus.fit.merchant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.activities.DishDetailActivity;
import com.hcmus.fit.merchant.adapters.DishAdapter;
import com.hcmus.fit.merchant.models.DishModel;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private List<DishModel> dishModelList = new ArrayList<>();

    private ListView lvDish;
    private Button btnAddDish;

    private DishAdapter dishAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        btnAddDish = root.findViewById(R.id.btn_add_dish);

        btnAddDish.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DishDetailActivity.class);
            startActivity(intent);
        });

        dishAdapter = new DishAdapter(getContext(), dishModelList);

        lvDish = root.findViewById(R.id.lv_dish);
        lvDish.setAdapter(dishAdapter);
        genDishData();

        return root;
    }

    private void genDishData() {
        DishModel dish = new DishModel("76uyiuwyrwr","","","food",1200);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishModelList.add(dish);
        dishAdapter.notifyDataSetChanged();
    }
}