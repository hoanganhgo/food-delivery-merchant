package com.hcmus.fit.merchant.networks;

import android.util.Log;

import com.hcmus.fit.merchant.constant.API;
import com.hcmus.fit.merchant.models.MerchantInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MySocket {
    private static Socket instance = null;

    public static Socket getInstance() {
        Log.d("socket","getInstance...");
        if (instance == null) {
            try {
//                instance = IO.socket(API.SERVER_SOCKET);
                instance = IO.socket("https://87e83c19d91f.ngrok.io");
                instance.connect();
                Log.d("socket","connect...");
                instance.on("connect", onAuthenticate);
                instance.on("RESPONSE_CHANGE_STATUS_ROOM", statusRoom);
                instance.on("RESPONSE_SHIPPER_CHANGE_COOR", statusRoom);
                instance.on("RESPONSE_MERCHANT_CONFIRM_ORDER", statusRoom);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        try {
            json.put("token", MerchantInfo.getInstance().getToken());
            instance.emit("authenticate", json);
            Log.d("socket","authenticate...");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static Emitter.Listener statusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", json.toString());
    };

}
