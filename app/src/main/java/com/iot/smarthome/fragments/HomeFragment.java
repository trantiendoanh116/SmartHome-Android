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

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = HomeFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTextWaring;
    private TextView mTxtDenTranKh1, mTxtDenChumKh1, mTxtDentranhKh1, mTxtQuatTran, mTxtDenTrangTriKh1, mTxtDenTranKh2, mTxtDenChumKh2, mTxtDentranhKh2, mTxtDenSan,
            mTxtDenCong, mTxtDenWC, mTxtBinhNL, mTxtDenCuaNgach, mTxtDenbep1, mTxtDenBep2, mTxtKhiLoc, mTxtATtong, mTxtATbep, mTxtTemp, mTxtHumi, mTxtCo, mTxtDongDienTong,
            mTxtCSTieuThu;
    private Button btnDenTranKh1, btnDenChumKh1, btnDenTranhKh1, btnOffQuatTran, btnOnQuatTran, btnDenTrangTriKh1, btnDenTranKh2, btnDenChumKh2, btnDenTranhKh2, btnDenSan,
            btnDenCong, btnDenWC, btnBinhNL, btnDenCuaNgach, btnDenBep1, btnDenBep2, btnKhiLoc, btnATtong, btnATbep;

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
        btnKhiLoc = getView().findViewById(R.id.f1d16_btn_on);

        mTxtATbep = getView().findViewById(R.id.cd01_value);
        btnATbep = getView().findViewById(R.id.cd01_btn);

        mTxtATtong = getView().findViewById(R.id.cd02_value);
        btnATtong = getView().findViewById(R.id.cd02_btn);


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
        btnKhiLoc.setOnClickListener(this);
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
        try {
            JSONObject jsonObject = new JSONObject();
            int id = v.getId();
            switch (id) {
                case R.id.f1d01_btn:
                    Log.d(TAG, "Button DenTranKH1 clicked");
                    jsonObject.put(AppConfig.den_tran_kh1, "change");

                    btnDenTranKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenTranKh1.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenTranKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d02_btn:
                    Log.d(TAG, "Button DenChumKH1 clicked");
                    jsonObject.put(AppConfig.den_chum_kh1, "change");

                    btnDenChumKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenChumKh1.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenChumKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d03_btn:
                    Log.d(TAG, "Button DenTranhKH1 clicked");
                    jsonObject.put(AppConfig.den_tranh_kh1, "change");

                    btnDenTranhKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenTranhKh1.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenTranhKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d04_btn_off:
                    Log.d(TAG, "Button OFF QuatTran clicked");
                    jsonObject.put(AppConfig.quat_tran, "off");

                    btnOffQuatTran.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnOffQuatTran.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnOffQuatTran.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d04_btn_on:
                    Log.d(TAG, "Button ON QuatTran clicked");
                    jsonObject.put(AppConfig.quat_tran, "on");

                    btnOnQuatTran.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnOnQuatTran.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnOnQuatTran.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d05_btn:
                    Log.d(TAG, "Button DenTrangtriKH1 clicked");
                    jsonObject.put(AppConfig.den_trangtri_kh1, "change");

                    btnDenTrangTriKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenTrangTriKh1.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenTrangTriKh1.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d06_btn:
                    Log.d(TAG, "Button DenTranKh2 clicked");
                    jsonObject.put(AppConfig.den_tran_kh2, "change");

                    btnDenTranKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenTranKh2.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenTranKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d07_btn:
                    Log.d(TAG, "Button DenChumKH2 clicked");
                    jsonObject.put(AppConfig.den_chum_kh2, "change");

                    btnDenChumKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenChumKh2.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenChumKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d08_btn:
                    Log.d(TAG, "Button DenTranhKH2 clicked");
                    jsonObject.put(AppConfig.den_tranh_kh2, "change");

                    btnDenTranhKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenTranhKh2.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenTranhKh2.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d09_btn:
                    Log.d(TAG, "Button DenSan clicked");
                    jsonObject.put(AppConfig.den_san, "change");

                    btnDenSan.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenSan.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenSan.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d10_btn:
                    Log.d(TAG, "Button DenCong clicked");
                    jsonObject.put(AppConfig.den_cong, "change");

                    btnDenCong.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenCong.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenCong.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d11_btn:
                    Log.d(TAG, "Button DenWC clicked");
                    jsonObject.put(AppConfig.den_wc, "change");

                    btnDenWC.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenWC.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenWC.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d12_btn:
                    Log.d(TAG, "Button BinhNL clicked");
                    jsonObject.put(AppConfig.binh_nl, "change");

                    btnBinhNL.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnBinhNL.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnBinhNL.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d13_btn:
                    Log.d(TAG, "Button DenCuaNgach clicked");
                    jsonObject.put(AppConfig.den_cua_ngach, "change");

                    btnDenCuaNgach.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenCuaNgach.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenCuaNgach.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d14_btn:
                    Log.d(TAG, "Button DenBep1 clicked");
                    jsonObject.put(AppConfig.den_bep_1, "change");

                    btnDenBep1.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenBep1.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenBep1.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d15_btn:
                    Log.d(TAG, "Button DenBep2 clicked");
                    jsonObject.put(AppConfig.den_bep_2, "change");

                    btnDenBep2.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnDenBep2.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnDenBep2.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d16_btn_on:
                    Log.d(TAG, "Button KhiLoc clicked");
                    jsonObject.put(AppConfig.khi_loc, "change");

                    btnKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnKhiLoc.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.cd01_btn:
                    Log.d(TAG, "Button ATbep clicked");
                    jsonObject.put(AppConfig.at_bep, "change");

                    btnATbep.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnATbep.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnATbep.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.cd02_btn:
                    Log.d(TAG, "Button ATTong clicked");
                    jsonObject.put(AppConfig.at_tong, "change");

                    btnATtong.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnATtong.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnATtong.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                default:
                    break;
            }
            Log.d(TAG, "Send to server: " + jsonObject.toString());
            mSocket.emit(AppConfig.EVENT_CONTROL, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void processData(JSONObject jsonObject) {
        try {
            //Den Tran KH1
            if (jsonObject.has(AppConfig.den_tran_kh1)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_tran_kh1);
                    if (value == 0) {
                        mTxtDenTranKh1.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenTranKh1.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenTranKh1.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenTranKh1.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Chum KH1
            if (jsonObject.has(AppConfig.den_chum_kh1)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_chum_kh1);
                    if (value == 0) {
                        mTxtDenChumKh1.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenChumKh1.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenChumKh1.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenChumKh1.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Tranh KH1
            if (jsonObject.has(AppConfig.den_tranh_kh1)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_tranh_kh1);
                    if (value == 0) {
                        mTxtDentranhKh1.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDentranhKh1.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDentranhKh1.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDentranhKh1.setText(getString(R.string.all_txt_error));
                }
            }
            //Quat Tran
            if (jsonObject.has(AppConfig.quat_tran)) {
                try {
                    int value = jsonObject.getInt(AppConfig.quat_tran);
                    if (value == 0) {
                        mTxtQuatTran.setText("0");
                    } else if (value == 1) {
                        mTxtQuatTran.setText("1");
                    } else if (value == 2) {
                        mTxtQuatTran.setText("2");
                    } else if (value == 3) {
                        mTxtQuatTran.setText("3");
                    } else {
                        mTxtQuatTran.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtQuatTran.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Tranh tri KH1
            if (jsonObject.has(AppConfig.den_trangtri_kh1)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_trangtri_kh1);
                    if (value == 0) {
                        mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Tran KH2
            if (jsonObject.has(AppConfig.den_tran_kh2)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_tran_kh2);
                    if (value == 0) {
                        mTxtDenTranKh2.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenTranKh2.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenTranKh2.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenTranKh2.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Chum KH2
            if (jsonObject.has(AppConfig.den_chum_kh2)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_chum_kh2);
                    if (value == 0) {
                        mTxtDenChumKh2.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenChumKh2.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenChumKh2.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenChumKh2.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Tranh KH2
            if (jsonObject.has(AppConfig.den_tranh_kh2)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_tranh_kh2);
                    if (value == 0) {
                        mTxtDentranhKh2.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDentranhKh2.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDentranhKh2.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDentranhKh2.setText(getString(R.string.all_txt_error));
                }
            }
            //Den San
            if (jsonObject.has(AppConfig.den_san)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_san);
                    if (value == 0) {
                        mTxtDenSan.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenSan.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenSan.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenSan.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Cong
            if (jsonObject.has(AppConfig.den_cong)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_cong);
                    if (value == 0) {
                        mTxtDenCong.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenCong.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenCong.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenCong.setText(getString(R.string.all_txt_error));
                }
            }
            //Den WC
            if (jsonObject.has(AppConfig.den_wc)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_wc);
                    if (value == 0) {
                        mTxtDenWC.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenWC.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenWC.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenWC.setText(getString(R.string.all_txt_error));
                }
            }
            //Binh NL
            if (jsonObject.has(AppConfig.binh_nl)) {
                try {
                    int value = jsonObject.getInt(AppConfig.binh_nl);
                    if (value == 0) {
                        mTxtBinhNL.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtBinhNL.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtBinhNL.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtBinhNL.setText(getString(R.string.all_txt_error));
                }
            }
            //Den Cua Ngach
            if (jsonObject.has(AppConfig.den_cua_ngach)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_cua_ngach);
                    if (value == 0) {
                        mTxtDenCuaNgach.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenCuaNgach.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenCuaNgach.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenCuaNgach.setText(getString(R.string.all_txt_error));
                }
            }
            //Den 1 bep
            if (jsonObject.has(AppConfig.den_bep_1)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_bep_1);
                    if (value == 0) {
                        mTxtDenbep1.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenbep1.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenbep1.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenbep1.setText(getString(R.string.all_txt_error));
                }
            }
            //Den 2 bep
            if (jsonObject.has(AppConfig.den_bep_2)) {
                try {
                    int value = jsonObject.getInt(AppConfig.den_bep_2);
                    if (value == 0) {
                        mTxtDenBep2.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtDenBep2.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtDenBep2.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDenBep2.setText(getString(R.string.all_txt_error));
                }
            }
            //Khi Loc
            if (jsonObject.has(AppConfig.khi_loc)) {
                try {
                    int value = jsonObject.getInt(AppConfig.khi_loc);
                    if (value == 1) {
                        mTxtKhiLoc.setText("1");
                    } else if (value == 2) {
                        mTxtKhiLoc.setText("2");
                    } else {
                        mTxtKhiLoc.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtKhiLoc.setText(getString(R.string.all_txt_error));
                }
            }
            //AT Bep
            if (jsonObject.has(AppConfig.at_bep)) {
                try {
                    int value = jsonObject.getInt(AppConfig.at_bep);
                    if (value == 0) {
                        mTxtATbep.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtATbep.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtATbep.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtATbep.setText(getString(R.string.all_txt_error));
                }
            }
            //AT Tong
            if (jsonObject.has(AppConfig.at_tong)) {
                try {
                    int value = jsonObject.getInt(AppConfig.at_tong);
                    if (value == 0) {
                        mTxtATtong.setText(getString(R.string.all_txt_off));
                    } else if (value == 1) {
                        mTxtATtong.setText(getString(R.string.all_txt_on));
                    } else {
                        mTxtATtong.setText(getString(R.string.all_txt_error));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtATtong.setText(getString(R.string.all_txt_error));
                }
            }
            //Nhiet do va do am
            if (jsonObject.has(AppConfig.temp_humi)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.temp_humi);
                    double temp = jsonObject1.getDouble(AppConfig.KEY_TEMP);
                    double humi = jsonObject1.getDouble(AppConfig.KEY_HUMI);
                    mTxtTemp.setText(String.format("%s °C", temp));
                    mTxtHumi.setText(String.format("%s %%", humi));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtTemp.setText(getString(R.string.all_txt_error));
                    mTxtHumi.setText(getString(R.string.all_txt_error));
                }
            }
            //Khi CO bep
            if (jsonObject.has(AppConfig.co)) {
                try {
                    double value = jsonObject.getInt(AppConfig.co);
                    mTxtCo.setText((int) value);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtCo.setText(getString(R.string.all_txt_error));
                }
            }
            //Cong suat vaf luong dien tieu thu
            if (jsonObject.has(AppConfig.cong_suat_va_dien_tieu_thu)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.cong_suat_va_dien_tieu_thu);
                    double ddTong = jsonObject1.getDouble(AppConfig.KEY_A);
                    double w = jsonObject1.getDouble(AppConfig.KEY_W);
                    double kw = jsonObject1.getDouble(AppConfig.KEY_KW);
                    double mw = jsonObject1.getDouble(AppConfig.KEY_MW);
                    String txtCSTieuThu = w + "W - " + kw + " KW - " + mw + " MW";
                    mTxtDongDienTong.setText(String.format("%s A", ddTong));
                    mTxtCSTieuThu.setText(txtCSTieuThu);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    mTxtDongDienTong.setText(getString(R.string.all_txt_error));
                    mTxtCSTieuThu.setText(getString(R.string.all_txt_error));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
