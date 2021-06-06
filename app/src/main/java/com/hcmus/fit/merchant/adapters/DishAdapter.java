package com.hcmus.fit.merchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.DishModel;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.utils.AppUtil;
import com.squareup.picasso.Picasso;


public class DishAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    public DishAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MerchantInfo.getInstance().getDishList().size();
    }

    @Override
    public Object getItem(int position) {
        return MerchantInfo.getInstance().getDishList().get(position);
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
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_dish);
            holder.tvName = convertView.findViewById(R.id.tv_dish_name);
            holder.tvCategory = convertView.findViewById(R.id.tv_category);
            holder.tvStatus = convertView.findViewById(R.id.tv_status);
            holder.tvPrice = convertView.findViewById(R.id.tv_dish_price);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        DishModel dishModel = MerchantInfo.getInstance().getDishList().get(position);

        if (dishModel.getAvatarUri() != null) {
            Picasso.with(convertView.getContext()).load(dishModel.getAvatarUri()).into(holder.ivAvatar);
        }

        holder.tvName.setText(dishModel.getName());
        holder.tvCategory.setText(dishModel.getCategory());
        holder.tvPrice.setText(AppUtil.convertCurrency(dishModel.getPrice()));

        return convertView;
    }

    static class MyViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvCategory;
        TextView tvStatus;
        TextView tvPrice;
    }
}
