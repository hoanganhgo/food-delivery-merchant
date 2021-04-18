package com.hcmus.fit.merchant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.adapters.DishAdapter;
import com.hcmus.fit.merchant.models.DishModel;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private List<DishModel> dishModelList = new ArrayList<>();

    private ListView lvDish;

    private DishAdapter dishAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        dishAdapter = new DishAdapter(getContext(), dishModelList);

        lvDish = root.findViewById(R.id.lv_dish);
        lvDish.setAdapter(dishAdapter);
        genDishData();

        return root;
    }

    private void genDishData() {
        DishModel dish = new DishModel(1,"","","food",1,1200);
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