package com.hcmus.fit.merchant.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.hcmus.fit.merchant.MainActivity;
import com.hcmus.fit.merchant.R;

import static com.hcmus.fit.merchant.constant.Constant.CHANNEL_DESCRIPTION;
import static com.hcmus.fit.merchant.constant.Constant.CHANNEL_ID;
import static com.hcmus.fit.merchant.constant.Constant.CHANNEL_NAME;

public class NotifyUtil {
    private static int id = 0;
    private static NotificationCompat.Builder mBuilder;
    private static MainActivity mainActivity;

    public static void init(MainActivity appCompatActivity) {
        mainActivity = appCompatActivity;
        createNotificationChannel();

        mBuilder = new NotificationCompat.Builder(mainActivity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private static void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mainActivity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void call(String title, String content) {
        mBuilder.setContentTitle(title)
                .setContentText(content);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mainActivity);
        notificationManager.notify(id++, mBuilder.build());
    }

    public static void callNewOrder(String content) {
        if (!activityActive()) {
            return;
        }

        mBuilder.setContentTitle(mainActivity.getResources().getString(R.string.new_order))
                .setContentText(content);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mainActivity);
        notificationManager.notify(id++, mBuilder.build());
    }

    private static boolean activityActive() {
        return mainActivity != null && !mainActivity.isDestroyed();
    }

    public static void activeIconBell(boolean active) {
        if (activityActive()) {
            mainActivity.runOnUiThread(() -> mainActivity.updateIcon(active));
        }
    }
}
