package com.iot.smarthome.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = HomeFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTextWaring;
    private TextView mTxtDenTranKh1, mTxtDenChumKh1, mTxtDentranhKh1, mTxtQuatTran, mTxtDenTrangTriKh1, mTxtDenTranKh2, mTxtDenChumKh2, mTxtDentranhKh2, mTxtDenSan,
            mTxtDenCong, mTxtDenWC, mTxtBinhNL, mTxtDenCuaNgach, mTxtDenbep1, mTxtDenBep2, mTxtKhiLoc, mTxtATtong, mTxtATbep, mTxtTemp, mTxtHumi, mTxtCo, mTxtDongDienTong,
            mTxtCSTieuThu;
    private Button btnDenTranKh1, btnDenChumKh1, btnDenTranhKh1, btnOffQuatTran, btnOnQuatTran, btnDenTrangTriKh1, btnDenTranKh2, btnDenChumKh2, btnDenTranhKh2, btnDenSan,
            btnDenCong, btnDenWC, btnBinhNL, btnDenCuaNgach, btnDenBep1, btnDenBep2, bntKhiLoc, btnATtong, btnATbep;

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
        mTextWaring = getView().findViewById(R.id.text_warning);

        mTxtDenTranKh1 = getView().findViewById(R.id.f1d01_value);
        btnDenTranKh1 = getView().findViewById(R.id.f1d01_btn);

        mTxtDenChumKh1 = getView().findViewById(R.id.f1d02_value);
        btnDenChumKh1 = getView().findViewById(R.id.f1d02_btn);

        mTxtDentranhKh1 = getView().findViewById(R.id.f1d03_value);
        btnDenTranhKh1 = getView().findViewById(R.id.f1d03_btn);

        mTxtQuatTran = getView().findViewById(R.id.f1d04_value);
        btnOffQuatTran = getView().findViewById(R.id.f1d04_btn_off);
        btnOnQuatTran = getView().findViewById(R.id.f1d04_btn_on);

        mTxtDenTrangTriKh1 = getView().findViewById(R.id.f1d05_value);
        btnDenTrangTriKh1 = getView().findViewById(R.id.f1d05_btn);

        mTxtDenTranKh2 = getView().findViewById(R.id.f1d06_value);
        btnDenTranKh2 = getView().findViewById(R.id.f1d06_btn);

        mTxtDenChumKh2 = getView().findViewById(R.id.f1d07_value);
        btnDenChumKh2 = getView().findViewById(R.id.f1d07_btn);

        mTxtDentranhKh2 = getView().findViewById(R.id.f1d08_value);
        btnDenTranhKh2 = getView().findViewById(R.id.f1d08_btn);

        mTxtDenSan = getView().findViewById(R.id.f1d09_value);
        btnDenSan = getView().findViewById(R.id.f1d09_btn);

        mTxtDenCong = getView().findViewById(R.id.f1d10_value);
        btnDenCong = getView().findViewById(R.id.f1d10_btn);

        mTxtDenWC = getView().findViewById(R.id.f1d11_value);
        btnDenWC = getView().findViewById(R.id.f1d11_btn);

        mTxtBinhNL = getView().findViewById(R.id.f1d12_value);
        btnBinhNL = getView().findViewById(R.id.f1d12_btn);

        mTxtDenCuaNgach = getView().findViewById(R.id.f1d13_value);
        btnDenCuaNgach = getView().findViewById(R.id.f1d13_btn);

        mTxtDenbep1 = getView().findViewById(R.id.f1d14_value);
        btnDenBep1 = getView().findViewById(R.id.f1d14_btn);

        mTxtDenBep2 = getView().findViewById(R.id.f1d15_value);
        btnDenBep2 = getView().findViewById(R.id.f1d15_btn);

        mTxtKhiLoc = getView().findViewById(R.id.f1d16_value);
        bntKhiLoc = getView().findViewById(R.id.f1d16_btn_on);

        mTxtATtong = getView().findViewById(R.id.cd01_value);
        btnATtong = getView().findViewById(R.id.cd01_btn);

        mTxtATbep = getView().findViewById(R.id.cd02_value);
        btnATbep = getView().findViewById(R.id.cd02_btn);

        mTxtTemp = getView().findViewById(R.id.cs01_temp_value);
        mTxtHumi = getView().findViewById(R.id.cs01_humi_value);
        mTxtCo = getView().findViewById(R.id.cs02_value);
        mTxtDongDienTong = getView().findViewById(R.id.cs03_dd_value);
        mTxtCSTieuThu = getView().findViewById(R.id.cs03_cs_value);


    }

    private void setOnListener() {

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
        btnDenTranKh1.setOnClickListener(this);
        btnDenChumKh1.setOnClickListener(this);
        btnDenTranhKh1.setOnClickListener(this);
        btnOffQuatTran.setOnClickListener(this);
        btnOnQuatTran.setOnClickListener(this);
        btnDenTrangTriKh1.setOnClickListener(this);
        btnDenTranKh2.setOnClickListener(this);
        btnDenChumKh2.setOnClickListener(this);
        btnDenTranhKh2.setOnClickListener(this);
        btnDenSan.setOnClickListener(this);
        btnDenCong.setOnClickListener(this);
        btnDenWC.setOnClickListener(this);
        btnBinhNL.setOnClickListener(this);
        btnDenCuaNgach.setOnClickListener(this);
        btnDenBep1.setOnClickListener(this);
        btnDenBep2.setOnClickListener(this);
        bntKhiLoc.setOnClickListener(this);
        btnATtong.setOnClickListener(this);
        btnATbep.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.f1d01_btn:
                Log.d(TAG, "Button DenTranKH1 clicked");
                break;
            case R.id.f1d02_btn:
                Log.d(TAG, "Button DenChumKH1 clicked");
                break;
            case R.id.f1d03_btn:
                Log.d(TAG, "Button DenTranhKH1 clicked");
                break;
            case R.id.f1d04_btn_off:
                Log.d(TAG, "Button OFF QuatTran clicked");
                break;
            case R.id.f1d04_btn_on:
                Log.d(TAG, "Button ON QuatTran clicked");
                break;
            case R.id.f1d05_btn:
                Log.d(TAG, "Button DenTrangtriKH1 clicked");
                break;
            case R.id.f1d06_btn:
                Log.d(TAG, "Button DenTranKh2 clicked");
                break;
            case R.id.f1d07_btn:
                Log.d(TAG, "Button DenChumKH2 clicked");
                break;
            case R.id.f1d08_btn:
                Log.d(TAG, "Button DenTranhKH2 clicked");
                break;
            case R.id.f1d09_btn:
                Log.d(TAG, "Button DenSan clicked");
                break;
            case R.id.f1d10_btn:
                Log.d(TAG, "Button DenCong clicked");
                break;
            case R.id.f1d11_btn:
                Log.d(TAG, "Button DenWC clicked");
                break;
            case R.id.f1d12_btn:
                Log.d(TAG, "Button BinhNL clicked");
                break;
            case R.id.f1d13_btn:
                Log.d(TAG, "Button DenCuaNgach clicked");
                break;
            case R.id.f1d14_btn:
                Log.d(TAG, "Button DenBep1 clicked");
                break;
            case R.id.f1d15_btn:
                Log.d(TAG, "Button DenBep2 clicked");
                break;
            case R.id.f1d16_btn_on:
                Log.d(TAG, "Button KhiLoc clicked");
                break;
            case R.id.cd01_btn:
                Log.d(TAG, "Button ATbep clicked");
                break;
            case R.id.cd02_btn:
                Log.d(TAG, "Button ATbep clicked");
                break;
            default:
                break;
        }
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
