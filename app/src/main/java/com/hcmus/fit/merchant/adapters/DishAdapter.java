package com.hcmus.fit.merchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.DishModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final List<DishModel> dishModelList;

    public DishAdapter(Context context, List<DishModel> dishModelList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dishModelList = dishModelList;
    }

    @Override
    public int getCount() {
        return dishModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dish, null);
            holder = new MyViewHolder();
            holder.btnDish = convertView.findViewById(R.id.btn_dish);
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_dish);
            holder.tvName = convertView.findViewById(R.id.tv_dish_name);
            holder.tvCategory = convertView.findViewById(R.id.tv_category);
            holder.tvStatus = convertView.findViewById(R.id.tv_status);
            holder.tvPrice = convertView.findViewById(R.id.tv_dish_price);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        String imageUri = "https://images.foody.vn/delivery/collection/s320x200/image-900af801-201118134119.jpeg";
        Picasso.with(convertView.getContext()).load(imageUri).into(holder.ivAvatar);

        return convertView;
    }

    static class MyViewHolder {
        Button btnDish;
        ImageView ivAvatar;
        TextView tvName;
        TextView tvCategory;
        TextView tvStatus;
        TextView tvPrice;


    }
}
