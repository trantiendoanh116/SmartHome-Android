package com.iot.smarthome;

public class AppConfig {
    public final static String URL_SERVER_DEFAULT = "https://smarthome116.herokuapp.com";
    public final static String[] SERVER_ADDRESS = {"https://smart-home-hung.herokuapp.com", "https://smarthome116.herokuapp.com", "http://192.168.4.100:80"};
    public final static String[] SERVER_NAME = {"Mr.Hung's server", "Truong's server", "Server Windowns"};

    public final static String FIRESTORE_COLLECTION_DEVICE = "devices";
    public final static String FIRESTORE_COLLECTION_SENSOR = "sensor";


    public final static String DEN_TRAN_KH1_ID = "F1_D01";
    public final static String DEN_CHUM_KH1_ID = "F1_D02";
    public final static String DEN_TRANH_KH1_ID = "F1_D03";
    public final static String QUAT_TRAN_ID = "F1_D04";
    public final static String DEN_TRANGTRI_KH1_ID = "F1_D05";
    public final static String DEN_TRAN_KH2_ID = "F1_D06";
    public final static String DEN_CHUM_KH2_ID = "F1_D07";
    public final static String DEN_TRANH_KH2_ID = "F1_D08";
    public final static String DEN_SAN_ID = "F1_D09";
    public final static String DEN_CONG_ID = "F1_D10";
    public final static String DEN_WC_ID = "F1_D11";
    public final static String BINH_NL_ID = "F1_D12";
    public final static String DEN_CUA_NGACH_ID = "F1_D13";
    public final static String DEN_1_BEP_ID = "F1_D14";
    public final static String DEN_2_BEP_ID = "F1_D15";
    public final static String KHI_LOC_ID = "F1_D16";
    public final static String AT_BEP_ID = "F1_D17";
    public final static String AT_TONG_ID = "F1_D18";
    public final static String GARA_ID = "F1_D19";

    public final static String SENSOR_TEMP_HUMI_ID = "F1_S01";
    public final static String SENSOR_CO_ID = "F1_S02";
    public final static String SENSOR_ELECTRIC_ID = "F1_S03";
    public final static String SENSOR_DUST_ID = "F1_S04";

    public final static String KEY_TEMP = "temp";
    public final static String KEY_HUMI = "humi";
    public final static String KEY_AMPE = "amp";
    public final static String KEY_VOLTAGE = "vol";
    public static final String KEY_ENERGY = "energy";
    public static final String KEY_VALUE = "value";
    public static final String KEY_AUTO = "auto";


    public static final long DELAY_CHANGE_BTN_COLOR = 500;
    public static final int CONNECTION_TINMEOUT = 5 * 1000;
}
