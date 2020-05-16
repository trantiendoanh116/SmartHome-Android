package com.iot.smarthome.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.activities.HomeActivity;
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.firebase.DocSnippets;
import com.iot.smarthome.firebase.FirestoreCallBack;
import com.iot.smarthome.network.HttpConnectionClient;
import com.iot.smarthome.utils.AppUtils;
import com.iot.smarthome.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = HomeFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mTextWaring;
    private PrefManager prefManager;
    private TextView mTxtDenTranKh1, mTxtDenChumKh1, mTxtDentranhKh1, mTxtQuatTran, mTxtDenTrangTriKh1, mTxtDenTranKh2, mTxtDenChumKh2, mTxtDentranhKh2, mTxtDenSan,
            mTxtDenCong, mTxtDenWC, mTxtBinhNL, mTxtDenCuaNgach, mTxtDenbep1, mTxtDenBep2, mTxtKhiLoc, mTxtATtong, mTxtATbep;
    private Button btnDenTranKh1, btnDenChumKh1, btnDenTranhKh1, btnOffQuatTran, btnOnQuatTran, btnDenTrangTriKh1, btnDenTranKh2, btnDenChumKh2, btnDenTranhKh2, btnDenSan,
            btnDenCong, btnDenWC, btnBinhNL, btnDenCuaNgach, btnDenBep1, btnDenBep2, btnOnKhiLoc, btnOffKhiLoc, btnATtong, btnATbep;

    private DocSnippets docSnippets;
    ListenerRegistration registration;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        docSnippets = new DocSnippets(db);
        initViews();
        setOnListener();
        listenEventFirestore();
    }


    private void listenEventFirestore() {
        registration = docSnippets.listenToDocument(new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d(TAG, "Update ui device");
                setupUIValueDevice();
            }

            @Override
            public void onError(String err) {
                Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
            }
        });
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


        setupUIValueDevice();
    }

    private void setupUIValueDevice() {
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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.container, new SensorFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }, 350);
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

    private void pingServerAndRefresh() {
        final ProgressDialog spinner = new ProgressDialog(getContext());
        spinner.setMessage("Vui lòng chờ...");
        spinner.show();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject objectStatus = new JSONObject();
                try {
                    final HttpURLConnection response = HttpConnectionClient.get(AppUtils.getAddressServer(getContext()) + "/ping", new HashMap<String, String>());
                    if (Objects.requireNonNull(response).getResponseCode() == 200) {
                        objectStatus.put("status", 200);

                    }
                } catch (Exception e) {
                    Log.e("HomeActivity", e.getMessage());
                    e.printStackTrace();
                }

            }
        });
        t.start();
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
            sendToServer(jsonObject);
            Log.d(TAG, "Send to server: " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendToServer(JSONObject jsonObject) {
        if (NetworkUtil.isConnectedToNetwork(getContext())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final HttpURLConnection response = HttpConnectionClient.post(AppUtils.getAddressServer(getContext()) + "/device/control", new HashMap<String, String>(), jsonObject.toString(), new HashMap<String, String>());
                    try {
                        if (response != null && response.getResponseCode() == 201) {
                            Log.d(TAG, "Success");
                        } else if (response != null && response.getResponseCode() == 400) {
                            showDialog("ESP8266 đã ngắt kết nối");
                        } else {
                            showDialog("Server đã ngắt kết nối");
                        }
                    } catch (IOException e) {
                        showDialog(e.getMessage());
                    }
                }
            }).start();
        } else {
            showDialog("Không có kết nối mạng. Vui lòng kiểm tra và thử lại!");
        }
    }

    public void showDialog(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        });

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
        int value = prefManager.getInt(PrefManager.QUAT_TRAN, -1);
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

}
