package com.hcmus.fit.merchant.constant;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://10.0.2.2:8000";
    private static final String releaseHost = "https://flashfood.online";
    private static final String SERVER = env == 0 ? localHost : releaseHost;

    public static String CREATE_NEW_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods";
    public static String UPDATE_DISH = SERVER + "/restaurants/{restaurantID}/foodcategories/{foodCategoryID}/foods/{foodID}";
}
