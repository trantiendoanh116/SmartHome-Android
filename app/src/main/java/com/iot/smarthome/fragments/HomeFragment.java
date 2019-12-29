package com.iot.smarthome.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.activities.HomeActivity;

import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private final String TAG = HomeFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTextWaring;

    private Socket mSocket;

    public HomeFragment(Socket socket) {
        this.mSocket = socket;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSocket.on(AppConfig.EVENT_RECEIVE_DATA, onNewData);
        initViews();
        setOnListener();
        setOnListenerSocket();
    }

    private void initViews() {
        mToolbar = getView().findViewById(R.id.toolbar);
    }

    private void setOnListener() {
        mTextWaring = getView().findViewById(R.id.text_warning);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }, 350);
            }
        });
    }

    private void setOnListenerSocket() {

        if (mSocket.connected()) {
            mTextWaring.setVisibility(View.GONE);

        } else {
            mTextWaring.setVisibility(View.VISIBLE);

        }
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Connected to socket server");
                            mTextWaring.setVisibility(View.GONE);

                        }
                    });
                }

            }

        });
        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Disconnected to socket server");
                            mTextWaring.setVisibility(View.VISIBLE);
                        }
                    });


                }
            }

        });

    }

    private Emitter.Listener onNewData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject data = (JSONObject) args[0];
                            Log.d(TAG, "" + data.toString());
                            processData(data);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
            }

        }
    };

    private void processData(JSONObject data) {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
