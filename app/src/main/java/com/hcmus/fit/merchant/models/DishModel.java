package com.hcmus.fit.merchant.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hcmus.fit.merchant.utils.WidgetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishModel {
    private String id;
    private String name;
    private String avatarUri = null;
    private Bitmap avatar = null;
    private String categoryId;
    private String category;
    private String options;
    private int optionPrice;
    private int price;
    private final List<OptionModel> optionList = new ArrayList<>();

    public DishModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addOption(OptionModel optionModel) {
        this.optionList.add(optionModel);
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(int optionPrice) {
        this.optionPrice = optionPrice;
    }

    public int getPriceTotal() {
        int total = price;

        for (OptionModel optionModel : this.optionList) {
            for (ItemModel itemModel : optionModel.getItemList()) {
                total += itemModel.getPrice();
            }
        }

        return total;
    }

    public void checkMaxSelect() {
        for (OptionModel optionModel : optionList) {
            optionModel.checkMaxSelect();
        }
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public JSONObject createJson() throws JSONException {
        JSONObject dishJson = new JSONObject();
        dishJson.put("name", this.name);
        dishJson.put("price", this.price);

        JSONArray optionArray = new JSONArray();

        for (OptionModel optionModel : this.optionList) {
            JSONObject jsonOption = optionModel.createJson();
            optionArray.put(jsonOption);
        }

        dishJson.put("options", optionArray);

        return dishJson;
    }

    public void createListOption(JSONArray optionArr) throws JSONException {
        for (int i = 0; i < optionArr.length(); i++) {
            JSONObject optionJson = optionArr.getJSONObject(i);
            OptionModel optionModel = new OptionModel();
            optionModel.setOptionWithJson(optionJson);
            this.optionList.add(optionModel);
        }
    }

    public void createViewOption(Context context, LinearLayout optionParent) {
        for (OptionModel option : optionList) {
            RelativeLayout rlOption = WidgetUtil.getOptionTypeView(context, option.getName());
            optionParent.addView(rlOption);

            for (ItemModel item : option.getItemList()) {
                RelativeLayout rlItem = WidgetUtil.getItemTypeView(context, item.getName(), item.getPrice());
                optionParent.addView(rlItem);
            }
        }
    }
}
