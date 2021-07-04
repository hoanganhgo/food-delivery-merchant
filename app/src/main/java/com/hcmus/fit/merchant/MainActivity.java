package com.hcmus.fit.merchant;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hcmus.fit.merchant.activities.NotificationActivity;
import com.hcmus.fit.merchant.constant.Constant;
import com.hcmus.fit.merchant.models.MerchantInfo;
import com.hcmus.fit.merchant.networks.MySocket;
import com.hcmus.fit.merchant.networks.SignInNetwork;
import com.hcmus.fit.merchant.utils.NotifyUtil;

import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private long pressBackTime = 0;

    private AppBarConfiguration mAppBarConfiguration;
    private MenuItem btnNotification;
    public TextView tvMerchantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_order, R.id.nav_menu, R.id.nav_report, R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        tvMerchantName = navigationView.getHeaderView(0).findViewById(R.id.tv_merchant_name);

        Log.d("token", MerchantInfo.getInstance().getToken());
        SignInNetwork.getMerchantInfo(this);
        MySocket.getInstance();

        NotifyUtil.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        btnNotification = menu.findItem(R.id.item_notification);;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_notification:
                NotifyUtil.activeIconBell(false);
                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateIcon(boolean hasNotify) {
        int resId = R.drawable.ic_bell;
        if (hasNotify) {
            resId = R.drawable.ic_notification;
        }

        Drawable icon = ResourcesCompat.getDrawable(getResources(), resId, null);
        btnNotification.setIcon(icon);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressBackTime > Constant.TIME_EXIT) {
            Toast.makeText(this, getResources().getString(R.string.notify_exit), Toast.LENGTH_SHORT).show();
            pressBackTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }

}