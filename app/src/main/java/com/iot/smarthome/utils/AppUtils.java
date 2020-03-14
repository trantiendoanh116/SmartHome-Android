package com.iot.smarthome.utils;

import android.content.Context;

import com.iot.smarthome.AppConfig;
import com.iot.smarthome.common.PrefManager;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class AppUtils {

    public static String getAddressServer(Context context) {
        PrefManager prefManager = new PrefManager(context);
        return prefManager.getString(PrefManager.SERVER_ADDRESS, AppConfig.URL_SERVER_DEFAULT);
    }

    public static String doubleToStringFormat(double number, int numAfterDot) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(numAfterDot);
            numberFormat.setMinimumFractionDigits(numAfterDot);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            return numberFormat.format(number);
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

}
