package com.iot.smarthome.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.adapters.MyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private Socket mSocket;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            mToolbar = findViewById(R.id.toolbar);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                    return false;
                }
            });
            try {
                mSocket = IO.socket(AppConfig.URL_SERVER + AppConfig.NAMESPACE_GET_DATA);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Log.d("ERROR :", e.toString());
            }
            mSocket.connect();
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            viewPager = (ViewPager) findViewById(R.id.viewPager);

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.main_txt_floor_1)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.main_txt_floor_2)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.main_txt_floor_3)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), mSocket);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("init", true);
                mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                Log.d(TAG, "Load first data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
