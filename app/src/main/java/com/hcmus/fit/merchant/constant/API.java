package com.hcmus.fit.merchant.constant;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://10.0.2.2:8002";
    //private static final String localHost = "http://192.168.1.4:8002";
    private static final String releaseHost = "https://flashfood.online";
    private static final String localSocket = "http://10.0.2.2:8010";
    private static final String releaseSocket = "";
    private static final String SERVER = env == 0 ? localHost : releaseHost;
    public static final String SERVER_SOCKET = env == 0 ? localSocket : releaseSocket;

    public static final String SEND_PHONE_OTP = SERVER + "/auth/phone/otp/call";
    public static final String AUTH_PHONE_VERIFY = SERVER + "/auth/phone/otp/verify";
    public static final String GET_USER_INFO = SERVER + "/restaurants";
    public static final String GET_USER_DETAIL = SERVER + "/restaurants/{restaurantID}";
    public static final String CREATE_CATEGORY = SERVER + "/restaurants/{restaurantID}/foodcategories";
    public static final String CREATE_NEW_DISH = SERVER +  "/restaurants/{restaurantID}/foodcategories/{categoryID}/foods";
    public static final String UPDATE_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods/{foodID}";
}
