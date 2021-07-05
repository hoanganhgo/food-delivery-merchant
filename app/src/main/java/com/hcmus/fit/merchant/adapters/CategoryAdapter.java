package com.hcmus.fit.merchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.CategoryModel;
import com.hcmus.fit.merchant.models.MerchantInfo;


public class CategoryAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    public CategoryAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MerchantInfo.getInstance().getCategories().size();
    }

    @Override
    public Object getItem(int position) {
        return MerchantInfo.getInstance().getCategories().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_category, null);
            holder = new MyViewHolder();
            holder.tvCategory = convertView.findViewById(R.id.tv_category);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        CategoryModel categoryModel = MerchantInfo.getInstance().getCategories().get(position);
        holder.tvCategory.setText(categoryModel.getName());

        return convertView;
    }

    static class MyViewHolder {
        TextView tvCategory;
    }
}
