package com.iot.smarthome.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.iot.smarthome.BuildConfig;

import java.util.HashSet;
import java.util.Set;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    public PrefManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
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
}
