package com.iot.smarthome.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.fragments.Floor1Fragment;
import com.iot.smarthome.fragments.Floor2Fragment;
import com.iot.smarthome.fragments.Floor3Fragment;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private FirebaseAuth auth;
    private Socket mSocket;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Handler handler = new Handler();
            switch (item.getItemId()) {
                case R.id.nav_floor_1:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activeFragment(new Floor1Fragment(mSocket));
                        }
                    }, 200);

                    return true;
                case R.id.nav_floor_2:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activeFragment(new Floor2Fragment());
                        }
                    }, 200);

                    return true;
                case R.id.nav_floor_3:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activeFragment(new Floor3Fragment());
                        }
                    }, 200);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        try {
            mSocket = IO.socket(AppConfig.URL_SERVER + AppConfig.NAMESPACE_GET_DATA);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("ERROR :", e.toString());
        }
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "Connected to socket server");
            }

        });
        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d(TAG, "Disconnected to socket server");
            }

        });

        mSocket.connect();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Floor1Fragment fragment = new Floor1Fragment(mSocket);
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void activeFragment(Fragment fragment) {
        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
