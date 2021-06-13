package com.hcmus.fit.merchant.networks;

import android.util.Log;

import com.hcmus.fit.merchant.R;
import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.constant.EventConstant;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.models.NotifyManager;
import com.hcmus.fit.merchant.models.NotifyModel;
import com.hcmus.fit.merchant.models.OrderManager;
import com.hcmus.fit.merchant.models.OrderModel;
import com.hcmus.fit.merchant.models.ShipperModel;
import com.hcmus.fit.merchant.utils.AppUtil;
import com.hcmus.fit.merchant.utils.NotifyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Calendar;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MySocket {
    private static Socket instance = null;

    public static Socket getInstance() {
        if (instance == null) {
            try {
                instance = IO.socket(API.SERVER_SOCKET);
//                instance = IO.socket("https://87e83c19d91f.ngrok.io");
                Log.d("socket","connect...");
                instance.connect();
                Log.d("socket","connected...");
                instance.on(EventConstant.CONNECT, onAuthenticate);
                instance.on(EventConstant.RESPONSE_CHANGE_STATUS_ROOM, listenStatusRoom);
                instance.on(EventConstant.RESPONSE_MERCHANT_CONFIRM_ORDER, receiveNewOrder);
                instance.on(EventConstant.RESPONSE_UPDATE_SHIPPER, listenUpdateShipper);
                instance.on(EventConstant.RESPONSE_NOTIFICATION, listenNotification);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static final Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        try {
            json.put("token", MerchantInfo.getInstance().getToken());
            instance.emit(EventConstant.AUTHENTICATE, json);
            Log.d("socket","authenticate...");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener receiveNewOrder = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String id = data.getString("_id");
            int subtotal = data.getInt("Subtotal");
            String createdAt = data.getString("CreatedAt");
            Calendar calendar = AppUtil.parseCalendar(createdAt);
            JSONArray foodArray = data.getJSONArray("Foods");

            double distance = data.getDouble("Distance");

            JSONObject customerJson = data.getJSONObject("User");
            String customerName = customerJson.getString("FullName");
            String customerAddress = data.getString("Address");
            String customerPhone = customerJson.getString("Phone");

            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId(id);
            orderModel.setSubTotal(subtotal);
            orderModel.setCustomerName(customerName);
            orderModel.setFullAddress(customerAddress);
            orderModel.setCustomerNote("Empty");
            orderModel.setCustomerPhone(customerPhone);
            orderModel.setDistance(distance);
            orderModel.setCalendar(calendar);
            orderModel.createDishOrderList(foodArray);

            OrderManager.getInstance().getWaitingList().add(0, orderModel);
            OrderManager.getInstance().notifyDataChanged(R.string.waiting);

            //code temp notify
            NotifyUtil.call("New Order", id +", " + customerName + ", " + customerAddress);
            NotifyUtil.activeIconBell(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public static void confirmOrder(String orderId) {
        JSONObject json = new JSONObject();
        try {
            json.put("orderID", orderId);
            MySocket.getInstance().emit(EventConstant.REQUEST_MERCHANT_CONFIRM_ORDER, json);
            Log.d("socket","confirm order >>>");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void cancelOrder(String orderId) {
        JSONObject json = new JSONObject();
        try {
            json.put("orderID", orderId);
            MySocket.getInstance().emit(EventConstant.REQUEST_MERCHANT_CANCEL_ORDER, json);
            Log.d("socket","cancel order >>>");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final Emitter.Listener listenStatusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            int status = data.getInt("status");
            String orderId = data.getString("orderID");

            if (status == 3) {
                OrderManager.getInstance().shipperReceivedOrder(orderId);
            } else if (status == 4) {
                OrderManager.getInstance().shipperGetOrder(orderId);
            } else if (status == 5) {
                OrderManager.getInstance().shipperCompleteOrder(orderId);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener listenUpdateShipper = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-shipper", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            JSONObject shipperJson = data.getJSONObject("infoShipper");
            String orderId = data.getString("orderID");
            String id = shipperJson.getString("_id");
            String fullName = shipperJson.getString("FullName");
            String avatar = shipperJson.getString("Avatar");
            String phone = shipperJson.getString("Phone");
            ShipperModel shipperModel = new ShipperModel(id, avatar, fullName, phone);
            OrderManager.getInstance().addShipper(orderId, shipperModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener listenNotification = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-notification", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            String id = data.getString("_id");
            String title = data.getString("Title");
            String content = data.getString("Subtitle");
            String avatar = data.getString("Thumbnail");

            NotifyModel notifyModel = new NotifyModel(id, title, content, avatar);
            NotifyManager.getInstance().addNotifyModel(notifyModel);

            NotifyUtil.call(title, content);
            NotifyUtil.activeIconBell(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };


}
