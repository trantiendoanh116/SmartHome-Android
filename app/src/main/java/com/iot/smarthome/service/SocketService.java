package com.iot.smarthome.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketService extends Service {
    private static final String TAG = "SocketService";
    private Socket mSocket;
    private PrefManager prefManager;

    public SocketService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(this);
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        try {

            mSocket = IO.socket(AppUtils.getAddressServer(getApplicationContext()) + AppConfig.SOCKET_NAMESPACE_APP);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("ERROR :", e.toString());
        }
        initSocket();

        return START_STICKY;
    }

    private void initSocket() {
        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_RECONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onConnectError);

        //I send the an auth event to the socket.io server
        //auth();
        mSocket.on(AppConfig.EVENT_RECEIVE_DATA, onReceived);
    }

//    public void auth() {
//        String mSender = Prefs.getUserGuid(mContext);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_guid", mSender);
//            jsonObject.put("device_id", "android");
//            this.mSocket.emit("auth", jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "connected " + mSocket.connected());
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "error " + args[0].toString());
        }
    };

    private Emitter.Listener onReceived = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(TAG, "received rtm");
            //Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT).show();
            try {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "" + data.toString());
                processData(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private void processData(JSONObject jsonObject) {
        try {
            //Den Tran KH1
            if (jsonObject.has(AppConfig.den_tran_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, jsonObject.getInt(AppConfig.den_tran_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, -1);
                }
            }
            //Den Chum KH1
            if (jsonObject.has(AppConfig.den_chum_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, jsonObject.getInt(AppConfig.den_chum_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, -1);
                }
            }
            //Den Tranh KH1
            if (jsonObject.has(AppConfig.den_tranh_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, jsonObject.getInt(AppConfig.den_tranh_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, -1);
                }
            }
            //Quat Tran
            if (jsonObject.has(AppConfig.quat_tran)) {
                try {
                    prefManager.putInt(PrefManager.QUAT_TRAN, jsonObject.getInt(AppConfig.quat_tran));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.QUAT_TRAN, -1);
                }
            }
            //Den Tranh tri KH1
            if (jsonObject.has(AppConfig.den_trangtri_kh1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, jsonObject.getInt(AppConfig.den_trangtri_kh1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, -1);
                }

            }
            //Den Tran KH2
            if (jsonObject.has(AppConfig.den_tran_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, jsonObject.getInt(AppConfig.den_tran_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, -1);
                }
            }
            //Den Chum KH2
            if (jsonObject.has(AppConfig.den_chum_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, jsonObject.getInt(AppConfig.den_chum_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, -1);
                }
            }
            //Den Tranh KH2
            if (jsonObject.has(AppConfig.den_tranh_kh2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, jsonObject.getInt(AppConfig.den_tranh_kh2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, -1);
                }
            }
            //Den San
            if (jsonObject.has(AppConfig.den_san)) {
                try {
                    prefManager.putInt(PrefManager.DEN_SAN, jsonObject.getInt(AppConfig.den_san));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_SAN, -1);
                }
            }
            //Den Cong
            if (jsonObject.has(AppConfig.den_cong)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CONG, jsonObject.getInt(AppConfig.den_cong));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CONG, -1);
                }
            }
            //Den WC
            if (jsonObject.has(AppConfig.den_wc)) {
                try {
                    prefManager.putInt(PrefManager.DEN_WC, jsonObject.getInt(AppConfig.den_wc));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_WC, -1);
                }
            }
            //Binh NL
            if (jsonObject.has(AppConfig.binh_nl)) {
                try {
                    prefManager.putInt(PrefManager.BINH_NL, jsonObject.getInt(AppConfig.binh_nl));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.BINH_NL, -1);
                }
            }
            //Den Cua Ngach
            if (jsonObject.has(AppConfig.den_cua_ngach)) {
                try {
                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, jsonObject.getInt(AppConfig.den_cua_ngach));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, -1);
                }
            }
            //Den 1 bep
            if (jsonObject.has(AppConfig.den_bep_1)) {
                try {
                    prefManager.putInt(PrefManager.DEN_BEP_1, jsonObject.getInt(AppConfig.den_bep_1));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_BEP_1, -1);
                }
            }
            //Den 2 bep
            if (jsonObject.has(AppConfig.den_bep_2)) {
                try {
                    prefManager.putInt(PrefManager.DEN_BEP_2, jsonObject.getInt(AppConfig.den_bep_2));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.DEN_BEP_2, -1);
                }
            }
            //Khi Loc
            if (jsonObject.has(AppConfig.khi_loc)) {
                try {
                    prefManager.putInt(PrefManager.KHI_LOC, jsonObject.getInt(AppConfig.khi_loc));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.KHI_LOC, -1);
                }
            }
            //AT Bep
            if (jsonObject.has(AppConfig.at_bep)) {
                try {
                    prefManager.putInt(PrefManager.AT_BEP, jsonObject.getInt(AppConfig.at_bep));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.AT_BEP, -1);
                }
            }
            //AT Tong
            if (jsonObject.has(AppConfig.at_tong)) {
                try {
                    prefManager.putInt(PrefManager.AT_TONG, jsonObject.getInt(AppConfig.at_tong));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putInt(PrefManager.AT_TONG, -1);
                }
            }
            //Nhiet do va do am
            if (jsonObject.has(AppConfig.temp_humi)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.temp_humi);
                    double temp = jsonObject1.getDouble(AppConfig.KEY_TEMP);
                    double humi = jsonObject1.getDouble(AppConfig.KEY_HUMI);
                    prefManager.putDouble(PrefManager.TEMP, temp);
                    prefManager.putDouble(PrefManager.HUMI, humi);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.TEMP, -1);
                    prefManager.putDouble(PrefManager.HUMI, -1);
                }
            }
            //Khi CO bep
            if (jsonObject.has(AppConfig.co)) {
                try {
                    prefManager.putDouble(PrefManager.KHOI_CO, jsonObject.getDouble(AppConfig.co));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.KHOI_CO, -1);
                }
            }
            //Dong dien tong (Ampe & vol)
            if (jsonObject.has(AppConfig.do_dien_tong)) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.do_dien_tong);
                    double amp = jsonObject1.getDouble(AppConfig.KEY_AMPE);
                    double vol = jsonObject1.getDouble(AppConfig.KEY_VOLTAGE);
                    double energy = jsonObject1.getDouble(AppConfig.KEY_ENERGY);
                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, amp);
                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, vol);
                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, energy);
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, -1);
                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, -1);
                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(AppConfig.EVENT_RECEIVE_DATA, onReceived);
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
