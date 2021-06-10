package com.hcmus.fit.merchant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.activities.DishDetailActivity;
import com.hcmus.fit.merchant.adapters.DishAdapter;
import com.hcmus.fit.merchant.models.DishModel;
import com.hcmus.fit.merchant.networks.DishNetwork;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
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

        dishAdapter = new DishAdapter(getContext());

        lvDish = root.findViewById(R.id.lv_dish);
        lvDish.setAdapter(dishAdapter);
        DishNetwork.getFoods(getContext(), dishAdapter);

        lvDish.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), DishDetailActivity.class);
            intent.putExtra("contentView", 1);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        dishAdapter.notifyDataSetChanged();
    }
}