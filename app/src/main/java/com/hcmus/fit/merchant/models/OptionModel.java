package com.hcmus.fit.merchant.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OptionModel {
    private int id;
    private boolean selectMore = false;
    private int maxSelect = 0;
    private String name;
    private List<ItemModel> itemList = new ArrayList<>();

    public OptionModel() {
    }

    public OptionModel(int id, int maxSelect, String name, List<ItemModel> itemList) {
        this.id = id;
        this.maxSelect = maxSelect;
        this.name = name;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public int getMaxSelect() {
        return maxSelect;
    }

    public String getName() {
        return name;
    }

    public List<ItemModel> getItemList() {
        return itemList;
    }

    public void setSelectMore(boolean selectMore) {
        this.selectMore = selectMore;
    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(ItemModel itemModel) {
        this.itemList.add(itemModel);
    }

    public void remove(ItemModel itemModel) {
        this.itemList.remove(itemModel);
    }

    public void checkMaxSelect() {
        if (selectMore) {
            maxSelect = itemList.size();
        } else {
            maxSelect = 1;
        }
    }

    public void setOptionWithJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.maxSelect = json.getInt("MaxSelect");
        this.name = json.getString("Name");

        this.itemList = new ArrayList<>();
        JSONArray itemArrJson = json.getJSONArray("Items");

        for (int i = 0; i < itemArrJson.length(); i++) {
            ItemModel itemModel = new ItemModel();
            JSONObject itemJson = itemArrJson.getJSONObject(i);
            itemModel.setItemWithJson(itemJson);
            this.itemList.add(itemModel);
        }
    }

    public JSONObject createJson() throws JSONException {
        JSONObject jsonOption = new JSONObject();
        jsonOption.put("name", this.name);
        jsonOption.put("maxselect", this.maxSelect);

        JSONArray jsonArray = new JSONArray();

        for (ItemModel itemModel : this.itemList) {
            JSONObject itemJson = itemModel.createJson();
            jsonArray.put(itemJson);
        }

        jsonOption.put("items", jsonArray);

        return jsonOption;
    }
}
