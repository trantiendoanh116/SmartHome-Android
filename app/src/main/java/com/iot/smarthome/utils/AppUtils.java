package com.iot.smarthome.utils;

import android.content.Context;

import com.iot.smarthome.AppConfig;
import com.iot.smarthome.common.PrefManager;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;

public class AppUtils {

    public static String getAddressServer(Context context) {
        PrefManager prefManager = new PrefManager(context);
        return prefManager.getString(PrefManager.SERVER_ADDRESS, AppConfig.URL_SERVER_DEFAULT);
    }

    public static String doubleToStringFormat(double number, int numAfterDot) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(numAfterDot);
            numberFormat.setMinimumFractionDigits(0);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            return numberFormat.format(number);
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

    public static double getValueSensor(Map<String, Object> result, String key){
        Object object = result.get(key);
        if(object instanceof Double){
            return (double) object;
        }else if(object instanceof Long){
            return (double) (long) object;
        }else{
            return -1;
        }
    }

}
