package com.hcmus.fit.merchant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.adapters.OrderPagerAdapter;

public class ManagerFragment extends Fragment {
    private OrderPagerAdapter orderPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);

        orderPagerAdapter = new OrderPagerAdapter(getContext(), getActivity().getSupportFragmentManager());
        viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(orderPagerAdapter);
        viewPager.setCurrentItem(0, false);
        tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return root;
    }
}