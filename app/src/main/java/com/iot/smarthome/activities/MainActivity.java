package com.iot.smarthome.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.iot.smarthome.R;
import com.iot.smarthome.fragments.Floor1Fragment;
import com.iot.smarthome.fragments.Floor2Fragment;
import com.iot.smarthome.fragments.Floor3Fragment;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

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
                            activeFragment(new Floor1Fragment());
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Floor1Fragment fragment = new Floor1Fragment();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
        }
    }

    private void activeFragment(Fragment fragment) {
        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
