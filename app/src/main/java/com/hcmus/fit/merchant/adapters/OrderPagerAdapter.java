package com.hcmus.fit.merchant.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.fragments.OrderFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<OrderFragment> orderFragmentList = new ArrayList<>();

    private final Context mContext;

    public OrderPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        Log.d("order", "New OrderPagerAdapter");

        OrderFragment orderFragment1 = new OrderFragment(R.string.waiting);
        orderFragmentList.add(orderFragment1);

        OrderFragment orderFragment2 = new OrderFragment(R.string.wait_shipper);
        orderFragmentList.add(orderFragment2);

        OrderFragment orderFragment3 = new OrderFragment(R.string.wait_dish);
        orderFragmentList.add(orderFragment3);

        OrderFragment orderFragment4 = new OrderFragment(R.string.shipping);
        orderFragmentList.add(orderFragment4);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return this.orderFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(orderFragmentList.get(position).getTitle());
    }

    @Override
    public int getCount() {
        return this.orderFragmentList.size();
    }


}