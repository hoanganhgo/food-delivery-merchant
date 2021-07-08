package com.hcmus.fit.merchant.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.business.ItemRemoving;
import com.hcmus.fit.merchant.business.ItemShowing;

public class WidgetUtil {

    public static RelativeLayout getOptionType(Context context, String optionTitle,
                                               ItemShowing itemShowing) {
        RelativeLayout rlOption = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlOption.setLayoutParams(params);

        TextView tvOptionTitle = getOptionTitle(context);
        tvOptionTitle.setText(optionTitle);
        tvOptionTitle.setTextSize(15f);
        rlOption.addView(tvOptionTitle);

        ImageButton btnIncrease = getIncreaseButton(context);
        btnIncrease.setOnClickListener(v -> {
            itemShowing.show(context);
        });
        rlOption.addView(btnIncrease);

        return rlOption;
    }

    public static TextView getOptionTitle(Context context) {
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.topMargin = 5;
        textView.setLayoutParams(params);
        textView.setText("Option");
        textView.setTextSize(18f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_medium);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public static ImageButton getIncreaseButton(Context context) {
        ImageButton btnIncrease = new ImageButton(context);
        btnIncrease.setImageResource(R.drawable.ic_increase);
        btnIncrease.setScaleType(ImageView.ScaleType.FIT_CENTER);
        btnIncrease.setBackgroundResource(R.drawable.bg_btn_white);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        btnIncrease.setLayoutParams(params);

        return btnIncrease;
    }

    public static RelativeLayout getItemType(Context context, String itemTitle, String itemPrice, ItemRemoving itemRemoving) {
        RelativeLayout rlOption = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlOption.setLayoutParams(params);

        TextView tvItemTitle = getItemTitle(context);
        tvItemTitle.setText(itemTitle);
        tvItemTitle.setTextSize(15f);
        rlOption.addView(tvItemTitle);

        TextView tvItemPrice = getItemPrice(context);
        tvItemPrice.setText(itemPrice);
        tvItemPrice.setTextSize(15f);
        rlOption.addView(tvItemPrice);

        ImageButton btnDecrease = getDecreaseButton(context);
        btnDecrease.setOnClickListener(v -> {
            itemRemoving.remove();
            ((ViewGroup)rlOption.getParent()).removeView(rlOption);
        });
        rlOption.addView(btnDecrease);

        return rlOption;
    }

    public static TextView getItemTitle(Context context) {
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.topMargin = 5;
        params.leftMargin = 10;
        textView.setLayoutParams(params);
        textView.setText("Item");
        textView.setTextSize(17f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_regular);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public static TextView getItemPrice(Context context) {
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.topMargin = 20;
        params.rightMargin = 100;
        textView.setLayoutParams(params);
        textView.setText("0");
        textView.setTextSize(17f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_regular);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.GRAY);
        return textView;
    }

    public static ImageButton getDecreaseButton(Context context) {
        ImageButton btnIncrease = new ImageButton(context);
        btnIncrease.setImageResource(R.drawable.ic_decrease);
        btnIncrease.setScaleType(ImageView.ScaleType.FIT_CENTER);
        btnIncrease.setBackgroundResource(R.drawable.bg_btn_white);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        btnIncrease.setLayoutParams(params);

        return btnIncrease;
    }

    public static RelativeLayout getOptionTypeView(Context context, String optionTitle) {
        RelativeLayout rlOption = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlOption.setLayoutParams(params);

        TextView tvOptionTitle = getOptionTitle(context);
        tvOptionTitle.setText(optionTitle);
        tvOptionTitle.setTextSize(15f);
        rlOption.addView(tvOptionTitle);

        return rlOption;
    }

    public static RelativeLayout getItemTypeView(Context context, String itemTitle, int itemPrice) {
        RelativeLayout rlOption = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlOption.setLayoutParams(params);

        TextView tvItemTitle = getItemTitle(context);
        tvItemTitle.setText(itemTitle);
        tvItemTitle.setTextSize(15f);
        rlOption.addView(tvItemTitle);

        TextView tvItemPrice = getItemPriceView(context);
        tvItemPrice.setText(AppUtil.convertCurrency(itemPrice));
        tvItemPrice.setTextSize(15f);
        rlOption.addView(tvItemPrice);

        return rlOption;
    }

    public static TextView getItemPriceView(Context context) {
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.topMargin = 20;
        params.rightMargin = 10;
        textView.setLayoutParams(params);
        textView.setText("0");
        textView.setTextSize(17f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_regular);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.GRAY);
        return textView;
    }

}
