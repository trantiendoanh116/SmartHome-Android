package com.iot.smarthome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.firebase.DocSnippets;
import com.iot.smarthome.firebase.FirestoreCallBack;
import com.iot.smarthome.utils.AppUtils;

import java.util.Map;

public class SensorFragment extends Fragment {
    private final String TAG = SensorFragment.class.getSimpleName();
    private PrefManager prefManager;
    private TextView mTxtTemp, mTxtHumi, mTxtCo, mTxtAmpVol, mTxtCSTieuThu, mTxtDustValue;
    private View mColorLevelDust;

    private DocSnippets docSnippets;

    public SensorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefManager = new PrefManager(getContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        docSnippets = new DocSnippets(db);
        initViews();
        setupUIValueDevice();
    }

    private void initViews() {
        mTxtTemp = getView().findViewById(R.id.cs01_temp_value);
        mTxtHumi = getView().findViewById(R.id.cs01_humi_value);
        mTxtCo = getView().findViewById(R.id.cs02_value);
        mTxtDustValue = getView().findViewById(R.id.cs04_value);
        mColorLevelDust = getView().findViewById(R.id.cs04_color_level);
        mTxtAmpVol = getView().findViewById(R.id.cs03_amp_vol);
        mTxtCSTieuThu = getView().findViewById(R.id.cs03_cs_value);
    }

    private void setupUIValueDevice() {
        setupTempHumi();
        setupKhoiCo();
        setupViewDongDienVaLuongDienTieuThu();
        setupViewDust();
    }


    private void setupTempHumi() {
        String sensorID = AppConfig.SENSOR_TEMP_HUMI_ID;
        docSnippets.listenValueSensor(sensorID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                double temp = (result.get(AppConfig.KEY_TEMP) instanceof Double) ? (double) result.get(AppConfig.KEY_TEMP) : -1;
                double humi  = (result.get(AppConfig.KEY_HUMI) instanceof Double)? (double)  result.get(AppConfig.KEY_HUMI) : -1;
                if (temp != -1) {
                    mTxtTemp.setText(String.format("%s °C", AppUtils.doubleToStringFormat(temp, 1)));
                    mTxtTemp.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                } else {
                    mTxtTemp.setText(getString(R.string.all_txt_error));
                    mTxtTemp.setTextColor(getResources().getColor(R.color.colorError));
                }
                if (humi != -1) {
                    mTxtHumi.setText(String.format("%s %%", AppUtils.doubleToStringFormat(humi, 1)));
                    mTxtHumi.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                } else {
                    mTxtHumi.setText(getString(R.string.all_txt_error));
                    mTxtHumi.setTextColor(getResources().getColor(R.color.colorError));
                }
            }

            @Override
            public void onError(String err) {
                mTxtTemp.setText(getString(R.string.all_txt_error));
                mTxtTemp.setTextColor(getResources().getColor(R.color.colorError));
                mTxtHumi.setText(getString(R.string.all_txt_error));
                mTxtHumi.setTextColor(getResources().getColor(R.color.colorError));
            }
        });
    }

    private void setupKhoiCo() {
        String sensorID = AppConfig.SENSOR_CO_ID;
        docSnippets.listenValueSensor(sensorID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                long value = (result.get(AppConfig.KEY_VALUE) instanceof Long) ? (long) result.get(AppConfig.KEY_VALUE) : -1;
                if (value != -1) {
                    mTxtCo.setText(String.format("%s ppm",  value));
                    mTxtCo.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                } else {
                    mTxtCo.setText(getString(R.string.all_txt_error));
                    mTxtCo.setTextColor(getResources().getColor(R.color.colorError));
                }

            }

            @Override
            public void onError(String err) {
                mTxtCo.setText(getString(R.string.all_txt_error));
                mTxtCo.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

    private void setupViewDust() {
        String sensorID = AppConfig.SENSOR_DUST_ID;
        docSnippets.listenValueSensor(sensorID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                long value = (result.get(AppConfig.KEY_VALUE) instanceof Long) ? (long) result.get(AppConfig.KEY_VALUE) : -1;
                if (value != -1) {
                    mTxtDustValue.setText((String.format("%s",  value)));
                    mTxtDustValue.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    if (value <= 50) {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustGood));
                    } else if (value <= 100) {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustNormal));
                    } else if (value <= 150) {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustLittlePollution));
                    } else if (value <= 200) {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustPollution));
                    } else if (value <= 300) {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustVeryPollution));
                    } else {
                        mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustDangerousPollution));
                    }
                } else {
                    mTxtDustValue.setText(getString(R.string.all_txt_error));
                    mTxtDustValue.setTextColor(getResources().getColor(R.color.colorError));
                    mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }

            }

            @Override
            public void onError(String err) {
                mTxtDustValue.setText(getString(R.string.all_txt_error));
                mTxtDustValue.setTextColor(getResources().getColor(R.color.colorError));
                mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
        });

    }

    private void setupViewDongDienVaLuongDienTieuThu() {
        String sensorID = AppConfig.SENSOR_ELECTRIC_ID;
        docSnippets.listenValueSensor(sensorID, new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                double ampe = (result.get(AppConfig.KEY_AMPE) instanceof Double) ? (double) result.get(AppConfig.KEY_AMPE) : -1;
                double vol  = (result.get(AppConfig.KEY_VOLTAGE) instanceof Double)? (double)  result.get(AppConfig.KEY_VOLTAGE) : -1;
                long energy  = (result.get(AppConfig.KEY_ENERGY) instanceof Long)? (long)  result.get(AppConfig.KEY_ENERGY) : -1;
                if (ampe != -1 && vol != -1 && energy != -1) {
                    mTxtAmpVol.setText(String.format("%sA/%sV", AppUtils.doubleToStringFormat(ampe, 1), AppUtils.doubleToStringFormat(vol, 1)));
                    if (energy >= 1000) {
                        int mw = (int) energy / 1000;
                        int kw = (int) energy % 1000;
                        mTxtCSTieuThu.setText(String.format("%sMW - %sKW", mw, kw));
                    } else {
                        mTxtCSTieuThu.setText(String.format("%sKW", (int) energy));
                    }
                    mTxtCSTieuThu.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                    mTxtAmpVol.setTextColor(getResources().getColor(R.color.colorTextPrimary));

                } else {
                    mTxtAmpVol.setText(getString(R.string.all_txt_error));
                    mTxtAmpVol.setTextColor(getResources().getColor(R.color.colorError));
                    mTxtCSTieuThu.setText(getString(R.string.all_txt_error));
                    mTxtCSTieuThu.setTextColor(getResources().getColor(R.color.colorError));
                }
            }

            @Override
            public void onError(String err) {
                mTxtAmpVol.setText(getString(R.string.all_txt_error));
                mTxtAmpVol.setTextColor(getResources().getColor(R.color.colorError));
                mTxtCSTieuThu.setText(getString(R.string.all_txt_error));
                mTxtCSTieuThu.setTextColor(getResources().getColor(R.color.colorError));
            }
        });

    }

}
