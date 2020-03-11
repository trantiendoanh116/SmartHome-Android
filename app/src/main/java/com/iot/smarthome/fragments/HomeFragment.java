package com.iot.smarthome.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = HomeFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTextWaring;
    private PrefManager prefManager;
    private TextView mTxtDenTranKh1, mTxtDenChumKh1, mTxtDentranhKh1, mTxtQuatTran, mTxtDenTrangTriKh1, mTxtDenTranKh2, mTxtDenChumKh2, mTxtDentranhKh2, mTxtDenSan,
            mTxtDenCong, mTxtDenWC, mTxtBinhNL, mTxtDenCuaNgach, mTxtDenbep1, mTxtDenBep2, mTxtKhiLoc, mTxtATtong, mTxtATbep, mTxtTemp, mTxtHumi, mTxtCo, mTxtVol, mTxtAmp,
            mTxtCSTieuThu;
    private Button btnDenTranKh1, btnDenChumKh1, btnDenTranhKh1, btnOffQuatTran, btnOnQuatTran, btnDenTrangTriKh1, btnDenTranKh2, btnDenChumKh2, btnDenTranhKh2, btnDenSan,
            btnDenCong, btnDenWC, btnBinhNL, btnDenCuaNgach, btnDenBep1, btnDenBep2, btnOnKhiLoc, btnOffKhiLoc, btnATtong, btnATbep;

    private Socket mSocket;

    public HomeFragment(Socket socket) {
        this.mSocket = socket;
    }

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefManager = new PrefManager(getContext());
        mSocket.on(AppConfig.EVENT_RECEIVE_DATA, onReceived);
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
        btnOnKhiLoc = getView().findViewById(R.id.f1d16_btn_on);
        btnOffKhiLoc = getView().findViewById(R.id.f1d16_btn_off);

        mTxtATbep = getView().findViewById(R.id.cd01_value);
        btnATbep = getView().findViewById(R.id.cd01_btn);

        mTxtATtong = getView().findViewById(R.id.cd02_value);
        btnATtong = getView().findViewById(R.id.cd02_btn);


        mTxtTemp = getView().findViewById(R.id.cs01_temp_value);
        mTxtHumi = getView().findViewById(R.id.cs01_humi_value);
        mTxtCo = getView().findViewById(R.id.cs02_value);
        mTxtVol = getView().findViewById(R.id.cs03_vol);
        mTxtAmp = getView().findViewById(R.id.cs03_amp);
        mTxtCSTieuThu = getView().findViewById(R.id.cs03_cs_value);

        setupUIValueDevice();
    }

    public void setupUIValueDevice() {
        setupViewDenTranKH1();
        setupViewDenChumKH1();
        setupViewDenTranhKH1();
        setupViewQuatTran();
        setupViewDenTrangTriKH1();
        setupViewDenTranKH2();
        setupViewDenChumKH2();
        setupViewDenTranhKH2();
        setupViewDenSan();
        setupViewDenCong();
        setupViewDenWC();
        setupViewBinhNL();
        setupViewDenCuaNgach();
        setupViewDenBep1();
        setupViewDenBep2();
        setupViewKhiLoc();
        setupViewATBep();
        setupViewATTong();
        setupTempHumi();
        setupKhoiCo();
        setupViewDongDienTong();
        setupViewCongSuatTieuThu();
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

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_refresh) {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setupUIValueDevice();
                            progressDialog.dismiss();
                        }
                    }, 2000);

                }
                return false;
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
        btnOnKhiLoc.setOnClickListener(this);
        btnOffKhiLoc.setOnClickListener(this);
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
                    Log.d(TAG, "Button ON KhiLoc clicked");
                    jsonObject.put(AppConfig.khi_loc, "on");

                    btnOnKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnOnKhiLoc.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnOnKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.f1d16_btn_off:
                    Log.d(TAG, "Button OFF KhiLoc clicked");
                    jsonObject.put(AppConfig.khi_loc, "off");

                    btnOffKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnOffKhiLoc.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnOffKhiLoc.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
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

    private Emitter.Listener onReceived = new Emitter.Listener() {
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
                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, jsonObject.getInt(AppConfig.den_tran_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, -1);
                }
                setupViewDenTranKH1();
            }
            //Den Chum KH1
            if (jsonObject.has(AppConfig.den_chum_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, jsonObject.getInt(AppConfig.den_chum_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, -1);
                }
                setupViewDenChumKH1();
            }
            //Den Tranh KH1
            if (jsonObject.has(AppConfig.den_tranh_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, jsonObject.getInt(AppConfig.den_tranh_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, -1);
                }
                setupViewDenTranhKH1();
            }
            //Quat Tran
            if (jsonObject.has(AppConfig.quat_tran)) {
                try {
                    prefManager.putInt(PrefManager.QUAT_TRAN, jsonObject.getInt(AppConfig.quat_tran));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.QUAT_TRAN, -1);
                }
                setupViewQuatTran();
            }
            //Den Tranh tri KH1
            if (jsonObject.has(AppConfig.den_trangtri_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, jsonObject.getInt(AppConfig.den_trangtri_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, -1);
                }
                setupViewDenTranhKH1();
            }
            //Den Tran KH2
            if (jsonObject.has(AppConfig.den_tran_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, jsonObject.getInt(AppConfig.den_tran_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, -1);
                }
                setupViewDenTranKH2();
            }
            //Den Chum KH2
            if (jsonObject.has(AppConfig.den_chum_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, jsonObject.getInt(AppConfig.den_chum_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, -1);
                }
                setupViewDenChumKH2();
            }
            //Den Tranh KH2
            if (jsonObject.has(AppConfig.den_tranh_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, jsonObject.getInt(AppConfig.den_tranh_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, -1);
                }
                setupViewDenTranhKH2();
            }
            //Den San
            if (jsonObject.has(AppConfig.den_san)) {
                try {
                    prefManager.putInt(PrefManager.DEN_SAN, jsonObject.getInt(AppConfig.den_san));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_SAN, -1);
                }
                setupViewDenSan();
            }
            //Den Cong
            if (jsonObject.has(AppConfig.den_cong)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CONG, jsonObject.getInt(AppConfig.den_cong));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CONG, -1);
                }
                setupViewDenCong();
            }
            //Den WC
            if (jsonObject.has(AppConfig.den_wc)) {
                try {
                    prefManager.putInt(PrefManager.DEN_WC, jsonObject.getInt(AppConfig.den_wc));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_WC, -1);
                }
                setupViewDenWC();
            }
            //Binh NL
            if (jsonObject.has(AppConfig.binh_nl)) {
                try {
                    prefManager.putInt(PrefManager.BINH_NL, jsonObject.getInt(AppConfig.binh_nl));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.BINH_NL, -1);
                }
                setupViewBinhNL();
            }
            //Den Cua Ngach
            if (jsonObject.has(AppConfig.den_cua_ngach)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, jsonObject.getInt(AppConfig.den_cua_ngach));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, -1);
                }
                setupViewDenCuaNgach();
            }
            //Den 1 bep
            if (jsonObject.has(AppConfig.den_bep_1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_BEP_1, jsonObject.getInt(AppConfig.den_bep_1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_BEP_1, -1);
                }
                setupViewDenBep1();
            }
            //Den 2 bep
            if (jsonObject.has(AppConfig.den_bep_2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_BEP_2, jsonObject.getInt(AppConfig.den_bep_2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_BEP_2, -1);
                }
                setupViewDenBep2();
            }
            //Khi Loc
            if (jsonObject.has(AppConfig.khi_loc)) {
                try {
                    prefManager.putInt(PrefManager.KHI_LOC, jsonObject.getInt(AppConfig.khi_loc));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.KHI_LOC, -1);
                }
                setupViewKhiLoc();
            }
            //AT Bep
            if (jsonObject.has(AppConfig.at_bep)) {
                try {
                    prefManager.putInt(PrefManager.AT_BEP, jsonObject.getInt(AppConfig.at_bep));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.AT_BEP, -1);
                }
                setupViewATBep();
            }
            //AT Tong
            if (jsonObject.has(AppConfig.at_tong)) {
                try {
                    prefManager.putInt(PrefManager.AT_TONG, jsonObject.getInt(AppConfig.at_tong));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.AT_TONG, -1);
                }
                setupViewATTong();
            }
            //Nhiet do va do am
            if (jsonObject.has(AppConfig.temp_humi)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.temp_humi);
                    double temp = jsonObject1.getDouble(AppConfig.KEY_TEMP);
                    double humi = jsonObject1.getDouble(AppConfig.KEY_HUMI);
                    prefManager.putDouble(PrefManager.TEMP, temp);
                    prefManager.putDouble(PrefManager.HUMI, humi);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.TEMP, -1);
                    prefManager.putDouble(PrefManager.HUMI, -1);
                }
                setupTempHumi();
            }
            //Khi CO bep
            if (jsonObject.has(AppConfig.co)) {
                try {
                    prefManager.putDouble(PrefManager.KHOI_CO, jsonObject.getDouble(AppConfig.co));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.KHOI_CO, -1);
                }
                setupKhoiCo();
            }
            //Dong Dien Tong
            if (jsonObject.has(AppConfig.dong_dien_tong)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.dong_dien_tong);
                    double amp = jsonObject1.getDouble(AppConfig.KEY_AMPE);
                    double vol = jsonObject1.getDouble(AppConfig.KEY_VOLTAGE);
                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, amp);
                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, vol);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, -1);
                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, -1);
                }
                setupViewDongDienTong();
            }

            if (jsonObject.has(AppConfig.cong_suat_tieu_thu)) {
                try {
                    double value = jsonObject.getDouble(AppConfig.cong_suat_tieu_thu);
                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, value);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
                }
                setupViewCongSuatTieuThu();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupViewDenTranKH1() {
        int value = prefManager.getInt(PrefManager.DEN_TRAN_KH1, -1);
        if (value == 0) {
            mTxtDenTranKh1.setText(getString(R.string.all_txt_off));
            mTxtDenTranKh1.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenTranKh1.setText(getString(R.string.all_txt_on));
            mTxtDenTranKh1.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenTranKh1.setText(getString(R.string.all_txt_error));
            mTxtDenTranKh1.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenChumKH1() {
        int value = prefManager.getInt(PrefManager.DEN_CHUM_KH1, -1);
        if (value == 0) {
            mTxtDenChumKh1.setText(getString(R.string.all_txt_off));
            mTxtDenChumKh1.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenChumKh1.setText(getString(R.string.all_txt_on));
            mTxtDenChumKh1.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenChumKh1.setText(getString(R.string.all_txt_error));
            mTxtDenChumKh1.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenTranhKH1() {
        int value = prefManager.getInt(PrefManager.DEN_TRANH_KH1, -1);
        if (value == 0) {
            mTxtDentranhKh1.setText(getString(R.string.all_txt_off));
            mTxtDentranhKh1.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDentranhKh1.setText(getString(R.string.all_txt_on));
            mTxtDentranhKh1.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDentranhKh1.setText(getString(R.string.all_txt_error));
            mTxtDentranhKh1.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewQuatTran() {
        int value = prefManager.getInt(PrefManager.DEN_TRAN_KH1, -1);
        if (value == 0 || value == 1 || value == 2 || value == 3) {
            if (value == 0) {
                mTxtQuatTran.setText("Tắt");
                mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorOff));
            } else {
                mTxtQuatTran.setText("Tốc độ: " + value);
                mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorOn));
            }
        } else {
            mTxtQuatTran.setText(getString(R.string.all_txt_error));
            mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorError));
        }
    }

    private void setupViewDenTrangTriKH1() {
        int value = prefManager.getInt(PrefManager.DEN_TRANGTRI_KH1, -1);
        if (value == 0) {
            mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_off));
            mTxtDenTrangTriKh1.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_on));
            mTxtDenTrangTriKh1.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_error));
            mTxtDenTrangTriKh1.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenTranKH2() {
        int value = prefManager.getInt(PrefManager.DEN_TRAN_KH2, -1);
        if (value == 0) {
            mTxtDenTranKh2.setText(getString(R.string.all_txt_off));
            mTxtDenTranKh2.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenTranKh2.setText(getString(R.string.all_txt_on));
            mTxtDenTranKh2.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenTranKh2.setText(getString(R.string.all_txt_error));
            mTxtDenTranKh2.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenChumKH2() {
        int value = prefManager.getInt(PrefManager.DEN_CHUM_KH2, -1);
        if (value == 0) {
            mTxtDenChumKh2.setText(getString(R.string.all_txt_off));
            mTxtDenChumKh2.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenChumKh2.setText(getString(R.string.all_txt_on));
            mTxtDenChumKh2.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenChumKh2.setText(getString(R.string.all_txt_error));
            mTxtDenChumKh2.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenTranhKH2() {
        int value = prefManager.getInt(PrefManager.DEN_TRANH_KH2, -1);
        if (value == 0) {
            mTxtDentranhKh2.setText(getString(R.string.all_txt_off));
            mTxtDentranhKh2.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDentranhKh2.setText(getString(R.string.all_txt_on));
            mTxtDentranhKh2.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDentranhKh2.setText(getString(R.string.all_txt_error));
            mTxtDentranhKh2.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenSan() {
        int value = prefManager.getInt(PrefManager.DEN_SAN, -1);
        if (value == 0) {
            mTxtDenSan.setText(getString(R.string.all_txt_off));
            mTxtDenSan.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenSan.setText(getString(R.string.all_txt_on));
            mTxtDenSan.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenSan.setText(getString(R.string.all_txt_error));
            mTxtDenSan.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenCong() {
        int value = prefManager.getInt(PrefManager.DEN_CONG, -1);
        if (value == 0) {
            mTxtDenCong.setText(getString(R.string.all_txt_off));
            mTxtDenCong.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenCong.setText(getString(R.string.all_txt_on));
            mTxtDenCong.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenCong.setText(getString(R.string.all_txt_error));
            mTxtDenCong.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenWC() {
        int value = prefManager.getInt(PrefManager.DEN_WC, -1);
        if (value == 0) {
            mTxtDenWC.setText(getString(R.string.all_txt_off));
            mTxtDenWC.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenWC.setText(getString(R.string.all_txt_on));
            mTxtDenWC.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenWC.setText(getString(R.string.all_txt_error));
            mTxtDenWC.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewBinhNL() {
        int value = prefManager.getInt(PrefManager.BINH_NL, -1);
        if (value == 0) {
            mTxtBinhNL.setText(getString(R.string.all_txt_off));
            mTxtBinhNL.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtBinhNL.setText(getString(R.string.all_txt_on));
            mTxtBinhNL.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtBinhNL.setText(getString(R.string.all_txt_error));
            mTxtBinhNL.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenCuaNgach() {
        int value = prefManager.getInt(PrefManager.DEN_CUA_NGACH, -1);
        if (value == 0) {
            mTxtDenCuaNgach.setText(getString(R.string.all_txt_off));
            mTxtDenCuaNgach.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenCuaNgach.setText(getString(R.string.all_txt_on));
            mTxtDenCuaNgach.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenCuaNgach.setText(getString(R.string.all_txt_error));
            mTxtDenCuaNgach.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenBep1() {
        int value = prefManager.getInt(PrefManager.DEN_BEP_1, -1);
        if (value == 0) {
            mTxtDenbep1.setText(getString(R.string.all_txt_off));
            mTxtDenbep1.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenbep1.setText(getString(R.string.all_txt_on));
            mTxtDenbep1.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenbep1.setText(getString(R.string.all_txt_error));
            mTxtDenbep1.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewDenBep2() {
        int value = prefManager.getInt(PrefManager.DEN_BEP_2, -1);
        if (value == 0) {
            mTxtDenBep2.setText(getString(R.string.all_txt_off));
            mTxtDenBep2.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtDenBep2.setText(getString(R.string.all_txt_on));
            mTxtDenBep2.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtDenBep2.setText(getString(R.string.all_txt_error));
            mTxtDenBep2.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewKhiLoc() {
        int value = prefManager.getInt(PrefManager.KHI_LOC, -1);
        if (value == 0 || value == 1 || value == 2 || value == 3) {
            if (value == 0) {
                mTxtKhiLoc.setText("Tắt");
                mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorOff));
            } else {
                mTxtKhiLoc.setText("Cấp " + value);
                mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorOn));
            }
        } else {
            mTxtKhiLoc.setText(getString(R.string.all_txt_error));
            mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorError));
        }
    }

    private void setupViewATBep() {
        int value = prefManager.getInt(PrefManager.AT_BEP, -1);
        if (value == 0) {
            mTxtATbep.setText(getString(R.string.all_txt_off));
            mTxtATbep.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtATbep.setText(getString(R.string.all_txt_on));
            mTxtATbep.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtATbep.setText(getString(R.string.all_txt_error));
            mTxtATbep.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewATTong() {
        int value = prefManager.getInt(PrefManager.AT_TONG, -1);
        if (value == 0) {
            mTxtATtong.setText(getString(R.string.all_txt_off));
            mTxtATtong.setTextColor(getResources().getColor(R.color.colorOff));
        } else if (value == 1) {
            mTxtATtong.setText(getString(R.string.all_txt_on));
            mTxtATtong.setTextColor(getResources().getColor(R.color.colorOn));
        } else {
            mTxtATtong.setText(getString(R.string.all_txt_error));
            mTxtATtong.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupTempHumi() {
        double temp = prefManager.getDouble(PrefManager.TEMP, -1);
        double humi = prefManager.getDouble(PrefManager.HUMI, -1);
        if (temp != -1) {
            mTxtTemp.setText(String.format("%s °C", AppUtils.doubleToStringFormat(temp)));
            mTxtTemp.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            mTxtTemp.setText(getString(R.string.all_txt_error));
            mTxtTemp.setTextColor(getResources().getColor(R.color.colorError));
        }
        if (humi != -1) {
            mTxtHumi.setText(String.format("%s %%", AppUtils.doubleToStringFormat(humi)));
            mTxtHumi.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            mTxtHumi.setText(getString(R.string.all_txt_error));
            mTxtHumi.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupKhoiCo() {
        double value = prefManager.getDouble(PrefManager.KHOI_CO, -1);
        if (value != -1) {
            mTxtCo.setText(String.format("%s/400max", (int) value));
            mTxtCo.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            mTxtCo.setText(getString(R.string.all_txt_error));
            mTxtCo.setTextColor(getResources().getColor(R.color.colorError));
        }
    }

    private void setupViewDongDienTong() {
        double ampe = prefManager.getDouble(PrefManager.DONGDIEN_AMPE, -1);
        double vol = prefManager.getDouble(PrefManager.DONGDIEN_VOL, -1);
        if (ampe != -1 && vol != -1) {
            mTxtVol.setText(String.format("%sV", AppUtils.doubleToStringFormat(vol)));
            mTxtAmp.setText(String.format("%sA", AppUtils.doubleToStringFormat(ampe)));
            mTxtVol.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            mTxtAmp.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            mTxtVol.setText(getString(R.string.all_txt_error));
            mTxtVol.setTextColor(getResources().getColor(R.color.colorError));
            mTxtAmp.setText(getString(R.string.all_txt_error));
            mTxtAmp.setTextColor(getResources().getColor(R.color.colorError));
        }

    }

    private void setupViewCongSuatTieuThu() {
        double value = prefManager.getDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
        if (value != -1) {
            if (value >= 1000) {
                int mw = (int) value / 1000;
                int kw = (int) value % 1000;
                mTxtCSTieuThu.setText(String.format("%sMW - %sKW", mw, kw));
            } else {
                mTxtCSTieuThu.setText(String.format("%sKW", (int) value));
            }
            mTxtCSTieuThu.setTextColor(getResources().getColor(R.color.colorTextPrimary));
        } else {
            mTxtCSTieuThu.setText(getString(R.string.all_txt_error));
            mTxtCSTieuThu.setTextColor(getResources().getColor(R.color.colorError));
        }
    }

}
