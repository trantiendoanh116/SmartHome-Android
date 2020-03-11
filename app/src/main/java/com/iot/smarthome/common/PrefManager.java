package com.iot.smarthome.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.iot.smarthome.BuildConfig;

import java.util.HashSet;
import java.util.Set;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static String APP_CONNECT_SERVER = "IS_APP_CONNECT";
    public static String ESP8266_CONNECT_SERVER = "IS_ESP8266_CONNECT";
    public static String DEN_TRAN_KH1 = "F1_D01";
    public static String DEN_CHUM_KH1 = "F1_D02";
    public static String DEN_TRANH_KH1 = "F1_D03";
    public static String QUAT_TRAN = "F1_D04";
    public static String DEN_TRANGTRI_KH1 = "F1_D05";
    public static String DEN_TRAN_KH2 = "F1_D06";
    public static String DEN_CHUM_KH2 = "F1_D07";
    public static String DEN_TRANH_KH2 = "F1_D08";
    public static String DEN_SAN = "F1_D09";
    public static String DEN_CONG = "F1_D10";
    public static String DEN_WC = "F1_D11";
    public static String BINH_NL = "F1_D12";
    public static String DEN_CUA_NGACH = "F1_D13";
    public static String DEN_BEP_1 = "F1_D14";
    public static String DEN_BEP_2 = "F1_D15";
    public static String KHI_LOC = "F1_D16";
    public static String AT_BEP = "C_D01";
    public static String AT_TONG = "C_D02";
    public static String TEMP = "C_S01_TEMP";
    public static String HUMI = "C_S01_HUMI";
    public static String KHOI_CO = "C_S02";
    public static String DONGDIEN_AMPE = "C_S03_AMPE";
    public static String DONGDIEN_VOL = "C_S03_VOL";
    public static String CONG_SUAT_TIEU_THU = "C_S03_ENERGY";

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public String getString(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return pref.getLong(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return pref.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }

    public Set<String> getStringSet(String key, HashSet<String> defValue) {
        return pref.getStringSet(key, defValue);
    }

    public Float getFloat(String key, float defValue) {
        return pref.getFloat(key, defValue);
    }

    public Double getDouble(String key, float defValue) {
        return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(defValue)));
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putDouble(String key, double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }
}
