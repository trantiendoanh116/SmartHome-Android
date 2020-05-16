package com.iot.smarthome.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.iot.smarthome.R;
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.firebase.DocSnippets;
import com.iot.smarthome.firebase.FirestoreCallBack;
import com.iot.smarthome.utils.AppUtils;

import java.util.Map;

public class SensorFragment extends Fragment {
    private final String TAG = SensorFragment.class.getSimpleName();
    private Toolbar mToolbar;
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
        setOnListener();
        setupUIValueDevice();
        listenEventFirestore();
    }

    private void listenEventFirestore() {

        docSnippets.listenToDocument(new FirestoreCallBack() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d(TAG, "Update ui sensor");
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
        mTxtTemp = getView().findViewById(R.id.cs01_temp_value);
        mTxtHumi = getView().findViewById(R.id.cs01_humi_value);
        mTxtCo = getView().findViewById(R.id.cs02_value);
        mTxtDustValue = getView().findViewById(R.id.cs04_value);
        mColorLevelDust = getView().findViewById(R.id.cs04_color_level);
        mTxtAmpVol = getView().findViewById(R.id.cs03_amp_vol);
        mTxtCSTieuThu = getView().findViewById(R.id.cs03_cs_value);
    }

    public void setupUIValueDevice() {
        setupTempHumi();
        setupKhoiCo();
        setupViewDongDienVaLuongDienTieuThu();
        setupViewDust();
    }

    private void setOnListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.container, new HomeFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }, 350);
            }
        });

    }

//
//    private void processData(Map<String, Object> data) {
//        //Nhiet do va do am
//        if (data.containsKey(AppConfig.temp_humi)) {
//            try {
//                HashMap<String, Double> hashMap = (HashMap<String, Double>) data.get(AppConfig.temp_humi);
//                double temp = new Double(hashMap.get(AppConfig.KEY_TEMP).toString());
//                double humi = new Double(hashMap.get(AppConfig.KEY_HUMI).toString());
//                prefManager.putDouble(PrefManager.TEMP, temp);
//                prefManager.putDouble(PrefManager.HUMI, humi);
//            } catch (Exception e) {
//                Log.e(TAG, "Nhiet do vaf do am: " + e.getMessage());
//                prefManager.putDouble(PrefManager.TEMP, -1);
//                prefManager.putDouble(PrefManager.HUMI, -1);
//            }
//            setupTempHumi();
//        }
//        //Khi CO bep
//        if (data.containsKey(AppConfig.co)) {
//            try {
//                prefManager.putDouble(PrefManager.KHOI_CO, new Double(data.get(AppConfig.co).toString()));
//            } catch (Exception e) {
//                Log.e(TAG, "Co: " + e.getMessage());
//                prefManager.putDouble(PrefManager.KHOI_CO, -1);
//            }
//            setupKhoiCo();
//        }
//        //Nong do bui min
//        if (data.containsKey(AppConfig.dust)) {
//            try {
//                prefManager.putDouble(PrefManager.DUST, new Double(data.get(AppConfig.dust).toString()));
//            } catch (Exception e) {
//                Log.e(TAG, "Bui: " + e.getMessage());
//                prefManager.putDouble(PrefManager.DUST, -1);
//            }
//            setupViewDust();
//        }
//        //Dong Dien Tong
//        if (data.containsKey(AppConfig.do_dien_tong)) {
//            try {
//                HashMap<String, Object> hashMap = (HashMap<String, Object>) data.get(AppConfig.do_dien_tong);
//                double amp = new Double(hashMap.get(AppConfig.KEY_AMPE).toString());
//                double vol = new Double(hashMap.get(AppConfig.KEY_VOLTAGE).toString());
//                double energy = new Double(hashMap.get(AppConfig.KEY_ENERGY).toString());
//                prefManager.putDouble(PrefManager.DONGDIEN_AMPE, amp);
//                prefManager.putDouble(PrefManager.DONGDIEN_VOL, vol);
//                prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, energy);
//            } catch (Exception e) {
//                Log.e(TAG, "Dien: " + e.getMessage());
//                prefManager.putDouble(PrefManager.DONGDIEN_AMPE, -1);
//                prefManager.putDouble(PrefManager.DONGDIEN_VOL, -1);
//                prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
//            }
//            setupViewDongDienVaLuongDienTieuThu();
//        }
//    }

    private void setupTempHumi() {
        double temp = prefManager.getDouble(PrefManager.TEMP, -1);
        double humi = prefManager.getDouble(PrefManager.HUMI, -1);
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

    private void setupViewDust() {
        double value = prefManager.getDouble(PrefManager.DUST, -1);
        if (value != -1) {
            mTxtDustValue.setText(String.format("%s µg/m3", (int) value));
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
            mColorLevelDust.setBackgroundColor(getResources().getColor(R.color.colorDustGood));
        }
    }

    private void setupViewDongDienVaLuongDienTieuThu() {
        double ampe = prefManager.getDouble(PrefManager.DONGDIEN_AMPE, -1);
        double vol = prefManager.getDouble(PrefManager.DONGDIEN_VOL, -1);
        double energy = prefManager.getDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
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

}
