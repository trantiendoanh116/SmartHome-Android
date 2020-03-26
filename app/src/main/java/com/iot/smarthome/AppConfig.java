package com.iot.smarthome;

public class AppConfig {
    //public final static String URL_SERVER = "https://smart-home-hung.herokuapp.com";
    public final static String URL_SERVER_DEFAULT = "https://smarthome116.herokuapp.com";
    public final static String[] SERVER_ADDRESS = {"https://smart-home-hung.herokuapp.com", "https://smarthome116.herokuapp.com"};
    public final static String[] SERVER_NAME = {"Mr.Hung's server", "Truong's server"};
    public final static String SOCKET_NAMESPACE_APP = "/android";
    public final static String SOCKET_NAMESPACE_BACKGROUND = "/android_bckg";

    public final static String EVENT_RECEIVE_DATA = "DATA";
    public final static String EVENT_CHECK_CONNECT = "CHECK";
    public final static String EVENT_CONTROL = "CONTROL";

    public final static String den_tran_kh1 = "F1_D01";
    public final static String den_chum_kh1 = "F1_D02";
    public final static String den_tranh_kh1 = "F1_D03";
    public final static String quat_tran = "F1_D04";
    public final static String den_trangtri_kh1 = "F1_D05";
    public final static String den_tran_kh2 = "F1_D06";
    public final static String den_chum_kh2 = "F1_D07";
    public final static String den_tranh_kh2 = "F1_D08";
    public final static String den_san = "F1_D09";
    public final static String den_cong = "F1_D10";
    public final static String den_wc = "F1_D11";
    public final static String binh_nl = "F1_D12";
    public final static String den_cua_ngach = "F1_D13";
    public final static String den_bep_1 = "F1_D14";
    public final static String den_bep_2 = "F1_D15";
    public final static String khi_loc = "F1_D16";
    public final static String at_bep = "C_D01";
    public final static String at_tong = "C_D02";
    public final static String temp_humi = "C_S01";
    public final static String co = "C_S02";
    public final static String dust = "C_S04";
    public final static String do_dien_tong = "C_S03";

    public final static String KEY_TEMP = "TEMP";
    public final static String KEY_HUMI = "HUMI";
    public final static String KEY_AMPE = "AMP";
    public final static String KEY_VOLTAGE = "VOL";
    public static final String KEY_ENERGY = "ENERGY";


    public static final long DELAY_CHANGE_BTN_COLOR = 500;
    public static final int CONNECTION_TINMEOUT = 5 * 1000;
}
