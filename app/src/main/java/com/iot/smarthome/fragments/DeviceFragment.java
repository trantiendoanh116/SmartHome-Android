package com.iot.smarthome.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
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

public class DeviceFragment extends Fragment implements View.OnClickListener {
    private final String TAG = DeviceFragment.class.getSimpleName();
    private TextView mTxtDenTranKh1, mTxtDenChumKh1, mTxtDentranhKh1, mTxtQuatTran, mTxtStateQuatTran, mTxtDenTrangTriKh1, mTxtDenTranKh2, mTxtDenChumKh2, mTxtDentranhKh2, mTxtDenSan,
            mTxtDenCong, mTxtDenWC, mTxtBinhNL, mTxtDenCuaNgach, mTxtDenbep1, mTxtDenBep2, mTxtKhiLoc, mTxtStateKhiLoc, mTxtATtong, mTxtATbep;
    private Button btnDenTranKh1, btnDenChumKh1, btnDenTranhKh1, btnOffQuatTran, btnOnQuatTran, btnDenTrangTriKh1, btnDenTranKh2, btnDenChumKh2, btnDenTranhKh2, btnDenSan,
            btnDenCong, btnDenWC, btnBinhNL, btnDenCuaNgach, btnDenBep1, btnDenBep2, btnOnKhiLoc, btnOffKhiLoc, btnATtong, btnATbep, btnGaraOnOff;
    private ImageView imgGaraUp, imgGaraDown, imgGaraPause;

    private DocSnippets docSnippets;

    public DeviceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        docSnippets = new DocSnippets(db);
        initViews();
        setOnListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews() {
        mTxtDenTranKh1 = requireView().findViewById(R.id.f1d01_value);
        btnDenTranKh1 = getView().findViewById(R.id.f1d01_btn);

        mTxtDenChumKh1 = getView().findViewById(R.id.f1d02_value);
        btnDenChumKh1 = getView().findViewById(R.id.f1d02_btn);

        mTxtDentranhKh1 = getView().findViewById(R.id.f1d03_value);
        btnDenTranhKh1 = getView().findViewById(R.id.f1d03_btn);

        mTxtQuatTran = getView().findViewById(R.id.f1d04_value);
        mTxtStateQuatTran = getView().findViewById(R.id.f1d04_state);
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
        mTxtStateKhiLoc = getView().findViewById(R.id.f1d16_state);

        mTxtATbep = getView().findViewById(R.id.cd01_value);
        btnATbep = getView().findViewById(R.id.cd01_btn);

        mTxtATtong = getView().findViewById(R.id.cd02_value);
        btnATtong = getView().findViewById(R.id.cd02_btn);

        btnGaraOnOff = getView().findViewById(R.id.btn_gara_on_off);
        imgGaraUp = getView().findViewById(R.id.btn_gara_up);
        imgGaraDown = getView().findViewById(R.id.btn_gara_down);
        imgGaraPause = getView().findViewById(R.id.btn_gara_pause);

        setValueDevice();
    }

    private void setValueDevice() {
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
        imgGaraUp.setOnClickListener(this);
        imgGaraDown.setOnClickListener(this);
        imgGaraPause.setOnClickListener(this);
        btnGaraOnOff.setOnClickListener(this);

    }

//    private void pingServerAndRefresh() {
//        final ProgressDialog spinner = new ProgressDialog(getContext());
//        spinner.setMessage("Vui lòng chờ...");
//        spinner.show();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final JSONObject objectStatus = new JSONObject();
//                try {
//                    final HttpURLConnection response = HttpConnectionClient.get(AppUtils.getAddressServer(getContext()) + "/ping", new HashMap<String, String>());
//                    if (Objects.requireNonNull(response).getResponseCode() == 200) {
//                        objectStatus.put("status", 200);
//
//                    }
//                } catch (Exception e) {
//                    Log.e("HomeActivity", e.getMessage());
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        t.start();
//    }


    @Override
    public void onClick(View v) {
        try {
            JSONObject jsonObject = new JSONObject();
            int id = v.getId();
            switch (id) {
                case R.id.f1d01_btn:
                    Log.d(TAG, "Button DenTranKH1 clicked");
                    jsonObject.put(AppConfig.DEN_TRAN_KH1_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_CHUM_KH1_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_TRANH_KH1_ID, "change");

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
                    jsonObject.put(AppConfig.QUAT_TRAN_ID, "off");

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
                    jsonObject.put(AppConfig.QUAT_TRAN_ID, "on");

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
                    jsonObject.put(AppConfig.DEN_TRANGTRI_KH1_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_TRAN_KH2_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_CHUM_KH2_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_TRANH_KH2_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_SAN_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_CONG_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_WC_ID, "change");

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
                    jsonObject.put(AppConfig.BINH_NL_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_CUA_NGACH_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_1_BEP_ID, "change");

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
                    jsonObject.put(AppConfig.DEN_2_BEP_ID, "change");

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
                    jsonObject.put(AppConfig.KHI_LOC_ID, "on");

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
                    jsonObject.put(AppConfig.KHI_LOC_ID, "off");

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
                    jsonObject.put(AppConfig.AT_BEP_ID, "change");

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
                    jsonObject.put(AppConfig.AT_TONG_ID, "change");

                    btnATtong.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnATtong.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnATtong.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.btn_gara_on_off:
                    Log.d(TAG, "Gara ON/OFF");
                    jsonObject.put(AppConfig.GARA_ID, "ON/OFF");
                    btnGaraOnOff.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    btnGaraOnOff.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            btnGaraOnOff.setBackgroundColor(getResources().getColor(R.color.colorBtnNormal));
                        }
                    }).start();
                    break;
                case R.id.btn_gara_up:
                    Log.d(TAG, "Gara UP");
                    jsonObject.put(AppConfig.GARA_ID, "UP");
                    imgGaraUp.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    imgGaraUp.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imgGaraUp.setBackgroundColor(getResources().getColor(R.color.fui_transparent));
                        }
                    }).start();
                    break;
                case R.id.btn_gara_pause:
                    Log.d(TAG, "Gara PAUSE");
                    jsonObject.put(AppConfig.GARA_ID, "PAUSE");
                    imgGaraPause.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    imgGaraPause.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imgGaraPause.setBackgroundColor(getResources().getColor(R.color.fui_transparent));
                        }
                    }).start();
                    break;
                case R.id.btn_gara_down:
                    Log.d(TAG, "Gara DOWN");
                    jsonObject.put(AppConfig.GARA_ID, "DOWN");
                    imgGaraDown.setBackgroundColor(getResources().getColor(R.color.colorBtnClicked));
                    imgGaraDown.animate().setDuration(AppConfig.DELAY_CHANGE_BTN_COLOR).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imgGaraDown.setBackgroundColor(getResources().getColor(R.color.fui_transparent));
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

    private void showDialog(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        String deviceID = AppConfig.DEN_TRAN_KH1_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenTranKh1.setText(getString(R.string.all_txt_error));
                mTxtDenTranKh1.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenChumKH1() {
        String deviceID = AppConfig.DEN_CHUM_KH1_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenChumKh1.setText(getString(R.string.all_txt_error));
                mTxtDenChumKh1.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

    private void setupViewDenTranhKH1() {
        String deviceID = AppConfig.DEN_TRANH_KH1_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDentranhKh1.setText(getString(R.string.all_txt_error));
                mTxtDentranhKh1.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

    private void setupViewQuatTran() {
        String deviceID = AppConfig.QUAT_TRAN_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
                boolean auto = (result.get(AppConfig.KEY_AUTO) instanceof Boolean) && (boolean) result.get(AppConfig.KEY_AUTO);
                if (value == 0 || value == 1 || value == 2 || value == 3) {
                    if (value == 0) {
                        mTxtQuatTran.setText("Tắt");
                        mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorOff));
                    } else {
                        mTxtQuatTran.setText(String.format("Tốc độ: %d", value));
                        mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorOn));
                    }
                    if (auto) {
                        mTxtStateQuatTran.setVisibility(View.VISIBLE);
                    } else {
                        mTxtStateQuatTran.setVisibility(View.GONE);
                    }
                } else {
                    mTxtQuatTran.setText(getString(R.string.all_txt_error));
                    mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorError));
                    mTxtStateQuatTran.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String err) {
                mTxtQuatTran.setText(getString(R.string.all_txt_error));
                mTxtQuatTran.setTextColor(getResources().getColor(R.color.colorError));
                mTxtStateQuatTran.setVisibility(View.GONE);
            }
        });

    }

    private void setupViewDenTrangTriKH1() {
        String deviceID = AppConfig.DEN_TRANGTRI_KH1_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenTrangTriKh1.setText(getString(R.string.all_txt_error));
                mTxtDenTrangTriKh1.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenTranKH2() {
        String deviceID = AppConfig.DEN_TRAN_KH2_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenTranKh2.setText(getString(R.string.all_txt_error));
                mTxtDenTranKh2.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

    private void setupViewDenChumKH2() {
        String deviceID = AppConfig.DEN_CHUM_KH2_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenChumKh2.setText(getString(R.string.all_txt_error));
                mTxtDenChumKh2.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenTranhKH2() {
        String deviceID = AppConfig.DEN_TRANH_KH2_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDentranhKh2.setText(getString(R.string.all_txt_error));
                mTxtDentranhKh2.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenSan() {
        String deviceID = AppConfig.DEN_SAN_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenSan.setText(getString(R.string.all_txt_error));
                mTxtDenSan.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenCong() {
        String deviceID = AppConfig.DEN_CONG_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenCong.setText(getString(R.string.all_txt_error));
                mTxtDenCong.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

    private void setupViewDenWC() {
        String deviceID = AppConfig.DEN_WC_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenWC.setText(getString(R.string.all_txt_error));
                mTxtDenWC.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewBinhNL() {
        String deviceID = AppConfig.BINH_NL_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtBinhNL.setText(getString(R.string.all_txt_error));
                mTxtBinhNL.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenCuaNgach() {
        String deviceID = AppConfig.DEN_CUA_NGACH_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenCuaNgach.setText(getString(R.string.all_txt_error));
                mTxtDenCuaNgach.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenBep1() {
        String deviceID = AppConfig.DEN_1_BEP_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenbep1.setText(getString(R.string.all_txt_error));
                mTxtDenbep1.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewDenBep2() {
        String deviceID = AppConfig.DEN_2_BEP_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtDenBep2.setText(getString(R.string.all_txt_error));
                mTxtDenBep2.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewKhiLoc() {
        String deviceID = AppConfig.KHI_LOC_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
                boolean auto = (result.get(AppConfig.KEY_AUTO) instanceof Boolean) && (boolean) result.get(AppConfig.KEY_AUTO);
                if (value == 0 || value == 1 || value == 2 || value == 3) {
                    if (value == 0) {
                        mTxtKhiLoc.setText("Tắt");
                        mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorOff));
                    } else {
                        mTxtKhiLoc.setText("Cấp " + value);
                        mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorOn));
                    }
                    if(auto){
                        mTxtStateKhiLoc.setVisibility(View.VISIBLE);
                    }else{
                        mTxtStateKhiLoc.setVisibility(View.GONE);
                    }
                } else {
                    mTxtKhiLoc.setText(getString(R.string.all_txt_error));
                    mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorError));
                    mTxtStateKhiLoc.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String err) {
                mTxtKhiLoc.setText(getString(R.string.all_txt_error));
                mTxtKhiLoc.setTextColor(getResources().getColor(R.color.colorError));
                mTxtStateKhiLoc.setVisibility(View.GONE);
            }
        });
    }

    private void setupViewATBep() {
        String deviceID = AppConfig.AT_BEP_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtATbep.setText(getString(R.string.all_txt_error));
                mTxtATbep.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupViewATTong() {
        String deviceID = AppConfig.AT_TONG_ID;
        docSnippets.listenValueDevice(deviceID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Object object = result.get(AppConfig.KEY_VALUE);
                int value = (object instanceof Long) ? (int) (long) object : -1;
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

            @Override
            public void onError(String err) {
                mTxtATtong.setText(getString(R.string.all_txt_error));
                mTxtATtong.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

}
