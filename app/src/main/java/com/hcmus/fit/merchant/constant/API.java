package com.hcmus.fit.merchant.constant;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://192.168.1.4:8002";
    private static final String releaseHost = "https://api.merchant.flash.bin.edu.vn";
    private static final String localSocket = "http://192.168.1.4:8010";
    private static final String releaseSocket = "https://api.socket.flash.bin.edu.vn";
    private static final String SERVER = env == 0 ? localHost : releaseHost;
    public static final String SERVER_SOCKET = env == 0 ? localSocket : releaseSocket;

    public static final String SEND_PHONE_OTP = SERVER + "/auth/phone/otp/call";
    public static final String AUTH_PHONE_VERIFY = SERVER + "/auth/phone/otp/verify";
    public static final String GET_USER_INFO = SERVER + "/restaurants";
    public static final String GET_USER_DETAIL = SERVER + "/restaurants/{restaurantID}";
    public static final String CREATE_CATEGORY = SERVER + "/restaurants/{restaurantID}/foodcategories";
    public static final String CREATE_NEW_DISH = SERVER +  "/restaurants/{restaurantID}/foodcategories/{categoryID}/foods";
    public static final String DELETE_DISH = SERVER + "/restaurants/{merchantId}/foodcategories/{categoryId}/foods/{foodId}";
    public static final String UPDATE_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods/{foodID}";
    public static final String GET_FOODS = SERVER + "/restaurants/{restaurantID}/foods";
    public static final String LOGIN_EMAIL = SERVER + "/auth/email";
    public static final String WITH_DRAWS = SERVER + "/restaurants/{restaurantID}/withdraws";
    public static final String GET_INCOME = SERVER + "/restaurants/{restaurantID}/statistics?montha={montha}&monthb={monthb}&daya={daya}";

}
