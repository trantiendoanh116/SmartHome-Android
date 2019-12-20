package com.iot.smarthome.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class Floor1Fragment extends Fragment {
    private final String TAG = "Floor1Fragment";
    private TextView textLightStatus, textFanStatus, textAptStatus, textTemp, textHumi, textCo2;
    private TextView mTextWaring;
    private Button btnFanOn, btnFanOff, btnChangLight, btnChangeApt;
    private Socket mSocket;

    public Floor1Fragment(Socket socket) {
        this.mSocket = socket;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSocket.on(AppConfig.EVENT_RECEIVE_DATA, onNewData);
        //staus
        initViews();
        setOnListener();
        setOnListenerSocket();

    }


    private void initViews() {
        mTextWaring = getView().findViewById(R.id.text_warning);
        //
        btnChangLight = getView().findViewById(R.id.btn_change_light);
        textLightStatus = getView().findViewById(R.id.text_light_status);

        btnChangeApt = getView().findViewById(R.id.btn_change_fan);
        textAptStatus = getView().findViewById(R.id.text_apt_status);

        textFanStatus = getView().findViewById(R.id.text_fan_status);
        btnFanOff = getView().findViewById(R.id.button_off);
        btnFanOn = getView().findViewById(R.id.button_on);

        textCo2 = getView().findViewById(R.id.text_co2);
        textHumi = getView().findViewById(R.id.text_humidity);
        textTemp = getView().findViewById(R.id.text_temperature);
        //
    }


    private void setOnListener() {
        btnChangLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Change light");
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("light", true);
                    mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnChangeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Change Apt");
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("apt", true);
                    mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        btnFanOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Fan off");
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fan_off", true);
                    mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnFanOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Fan on");
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("fan_on", true);
                    mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                            Toast.makeText(getContext(), "Connected to socket server", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getContext(), "Disconnected to socket server", Toast.LENGTH_LONG).show();
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
            if (data.has("light")) {
                int value = data.getInt("light");
                if (value == 1) {
                    textLightStatus.setText("Bật");
                } else {
                    textLightStatus.setText("Tắt");
                }
            }
            if (data.has("fan")) {
                int value = data.getInt("fan");
                textFanStatus.setText("Tốc độ:" + value);
            }
            if (data.has("apt")) {
                int value = data.getInt("apt");
                if (value == 1) {
                    textAptStatus.setText("Bật");
                } else {
                    textAptStatus.setText("Tắt");
                }
            }
            if (data.has("temp")) {
                double value = data.getInt("temp");
                textTemp.setText(value + "°C");
            }
            if (data.has("humi")) {
                double value = data.getInt("humi");
                textHumi.setText(value + "%");
            }
            if (data.has("co")) {
                double value = data.getInt("co");
                textCo2.setText(value + "%");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
