package com.iot.smarthome.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.fragments.HomeFragment;
import com.iot.smarthome.service.SocketService;
import com.iot.smarthome.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static NavigationView mNavigationView;
    public static DrawerLayout mDrawerLayout;

    private FirebaseAuth auth;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_home);
            Intent intent = new Intent(this, SocketService.class);
            if (!isMyServiceRunning(SocketService.class)) {
                startService(intent);
                Log.d(TAG, "Start service");
            }
            try {
                mSocket = IO.socket(AppUtils.getAddressServer(this) + AppConfig.SOCKET_NAMESPACE_APP);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Log.d("ERROR :", e.toString());
            }
            mSocket.connect();
            if (getIntent().getBooleanExtra("EXTRA_INIT_DATA", false)) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("init", true);
                    mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //show sreen home
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new HomeFragment(mSocket));
            fragmentTransaction.commit();

        }

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_settings) {
                    Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
