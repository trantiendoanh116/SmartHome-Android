package com.iot.smarthome.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.iot.smarthome.network.HttpConnectionClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
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
            try {
                mSocket = IO.socket(AppConfig.URL_SERVER + AppConfig.NAMESPACE_GET_DATA);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Log.d("ERROR :", e.toString());
            }
            mSocket.connect();
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
                } else if (id == R.id.action_refresh) {
                    final ProgressDialog spinner = new ProgressDialog(HomeActivity.this);
                    spinner.setMessage("Đang kiểm tra...");
                    spinner.show();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final JSONObject objectStatus = new JSONObject();
                            try {

                                final HttpURLConnection response = HttpConnectionClient.get(AppConfig.URL_SERVER + "/ping", new HashMap<String, String>());
                                if (Objects.requireNonNull(response).getResponseCode() == 200) {
                                    objectStatus.put("status", 200);

                                }
                            } catch (Exception e) {
                                Log.e("HomeActivity", e.getMessage());
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        spinner.dismiss();

                                        if (objectStatus.has("status") && objectStatus.getInt("status") == 200) {
                                            showDialog("Đã kết nối với server");
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("init", true);
                                                mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            mDrawerLayout.closeDrawers();
                                        } else {
                                            showDialog("Chưa kết nối server");
                                        }
                                    } catch (Exception e) {
                                        Log.e("HomeActivity", e.getMessage());
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });
                    t.start();
                }
                return false;
            }
        });


    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
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
