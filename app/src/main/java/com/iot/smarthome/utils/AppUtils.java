package com.iot.smarthome.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class AppUtils {

    public static String doubleToStringFormat(double number) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            return numberFormat.format(number);
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

}
