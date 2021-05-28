package com.hcmus.fit.merchant.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.models.OrderModel;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final List<OrderModel> orderModelList;

    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.orderModelList = orderModelList;
    }

    @Override
    public int getCount() {
        return orderModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_order, null);
            holder = new MyViewHolder();
            holder.tvOrderId = convertView.findViewById(R.id.tv_order_id);
            holder.tvOrderDate = convertView.findViewById(R.id.tv_order_date);
            holder.tvCustomerName = convertView.findViewById(R.id.tv_customer_name);
            holder.tvOrderPrice = convertView.findViewById(R.id.tv_order_price);
            holder.tvOrderTime = convertView.findViewById(R.id.tv_order_time);
            holder.tvDishNum = convertView.findViewById(R.id.tv_num_dish);
            holder.tvDistance = convertView.findViewById(R.id.tv_distance);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

//        OrderModel orderModel = orderModelList.get(position);

        return convertView;
    }

    static class MyViewHolder {
        TextView tvOrderId;
        TextView tvOrderDate;
        TextView tvCustomerName;
        TextView tvOrderPrice;
        TextView tvOrderTime;
        TextView tvDishNum;
        TextView tvDistance;
    }
}
