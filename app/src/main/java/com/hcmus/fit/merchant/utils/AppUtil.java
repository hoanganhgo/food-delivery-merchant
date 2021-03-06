package com.hcmus.fit.merchant.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AppUtil {
    public static Calendar parseCalendar(String s) {
        String[] arr = s.split("T");

        String[] dates = arr[0].split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);

        String[] times = arr[1].split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    public static String getDateString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)
                + "/" + calendar.get(Calendar.YEAR);
    }

    public static String getTimeString(Calendar calendar) {
        return calendar.get(Calendar.HOUR) + "h:" + calendar.get(Calendar.MINUTE);
    }

    public static String convertCurrency(int money) {
        StringBuilder s = new StringBuilder("đ");

        while (money / 1000 > 0) {
            int mod = money % 1000;

            if (mod == 0) {
                s.insert(0, "000");
            } else if (mod < 10) {
                s.insert(0, mod);
                s.insert(0,"00");
            } else if (mod < 100) {
                s.insert(0, mod);
                s.insert(0, "0");
            } else {
                s.insert(0, mod);
            }

            money = money / 1000;

            s.insert(0, ",");
        }

        s.insert(0, money);

        return s.toString();
    }

    public static int convertInt(String currency) {
        currency = currency.replaceAll(",","");
        currency = currency.replaceAll("đ","");

        int result = 0;
        try {
            result = Integer.parseInt(currency);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
