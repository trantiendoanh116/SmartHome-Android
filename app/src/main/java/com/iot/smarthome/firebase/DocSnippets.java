package com.iot.smarthome.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.iot.smarthome.AppConfig;

public class DocSnippets {
    private static final String TAG = "DocSnippets";
    private final FirebaseFirestore db;

    public DocSnippets(FirebaseFirestore db) {
        this.db = db;
    }


    public void getDocument(FirestoreCallBack callBack) {
        // [START get_document]
        DocumentReference docRef = db.collection(AppConfig.FIRESTORE_CONLLECTION_PATH).document("tientruong");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        callBack.onSuccess(document.getData());
                    } else {
                        callBack.onError("No such document");
                        Log.w(TAG, "No such document");
                    }
                } else {
                    callBack.onError("Get failed with: " + task.getException());
                    Log.e(TAG, "get failed with ", task.getException());
                }
            }
        });
        // [END get_document]
    }

    public ListenerRegistration listenToDocument(FirestoreCallBack callBack) {
        // [START listen_document]
        final DocumentReference docRef = db.collection(AppConfig.FIRESTORE_CONLLECTION_PATH).document("tientruong");
        return docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    callBack.onError("Listen failed: " + e.getMessage());
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    callBack.onSuccess(snapshot.getData());
                } else {
                    callBack.onError("Current data: null");
                    Log.w(TAG, "Current data: null");
                }
            }
        });
        // [END listen_document]
    }

//    private void processData(JSONObject jsonObject) {
//        try {
//            //Den Tran KH1
//            if (jsonObject.has(AppConfig.den_tran_kh1)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, jsonObject.getInt(AppConfig.den_tran_kh1));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_TRAN_KH1, -1);
//                }
//            }
//            //Den Chum KH1
//            if (jsonObject.has(AppConfig.den_chum_kh1)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, jsonObject.getInt(AppConfig.den_chum_kh1));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_CHUM_KH1, -1);
//                }
//            }
//            //Den Tranh KH1
//            if (jsonObject.has(AppConfig.den_tranh_kh1)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, jsonObject.getInt(AppConfig.den_tranh_kh1));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_TRANH_KH1, -1);
//                }
//            }
//            //Quat Tran
//            if (jsonObject.has(AppConfig.quat_tran)) {
//                try {
//                    prefManager.putInt(PrefManager.QUAT_TRAN, jsonObject.getInt(AppConfig.quat_tran));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.QUAT_TRAN, -1);
//                }
//            }
//            //Den Tranh tri KH1
//            if (jsonObject.has(AppConfig.den_trangtri_kh1)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, jsonObject.getInt(AppConfig.den_trangtri_kh1));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_TRANGTRI_KH1, -1);
//                }
//
//            }
//            //Den Tran KH2
//            if (jsonObject.has(AppConfig.den_tran_kh2)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, jsonObject.getInt(AppConfig.den_tran_kh2));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_TRAN_KH2, -1);
//                }
//            }
//            //Den Chum KH2
//            if (jsonObject.has(AppConfig.den_chum_kh2)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, jsonObject.getInt(AppConfig.den_chum_kh2));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_CHUM_KH2, -1);
//                }
//            }
//            //Den Tranh KH2
//            if (jsonObject.has(AppConfig.den_tranh_kh2)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, jsonObject.getInt(AppConfig.den_tranh_kh2));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_TRANH_KH2, -1);
//                }
//            }
//            //Den San
//            if (jsonObject.has(AppConfig.den_san)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_SAN, jsonObject.getInt(AppConfig.den_san));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_SAN, -1);
//                }
//            }
//            //Den Cong
//            if (jsonObject.has(AppConfig.den_cong)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_CONG, jsonObject.getInt(AppConfig.den_cong));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_CONG, -1);
//                }
//            }
//            //Den WC
//            if (jsonObject.has(AppConfig.den_wc)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_WC, jsonObject.getInt(AppConfig.den_wc));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_WC, -1);
//                }
//            }
//            //Binh NL
//            if (jsonObject.has(AppConfig.binh_nl)) {
//                try {
//                    prefManager.putInt(PrefManager.BINH_NL, jsonObject.getInt(AppConfig.binh_nl));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.BINH_NL, -1);
//                }
//            }
//            //Den Cua Ngach
//            if (jsonObject.has(AppConfig.den_cua_ngach)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, jsonObject.getInt(AppConfig.den_cua_ngach));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_CUA_NGACH, -1);
//                }
//            }
//            //Den 1 bep
//            if (jsonObject.has(AppConfig.den_bep_1)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_BEP_1, jsonObject.getInt(AppConfig.den_bep_1));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_BEP_1, -1);
//                }
//            }
//            //Den 2 bep
//            if (jsonObject.has(AppConfig.den_bep_2)) {
//                try {
//                    prefManager.putInt(PrefManager.DEN_BEP_2, jsonObject.getInt(AppConfig.den_bep_2));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.DEN_BEP_2, -1);
//                }
//            }
//            //Khi Loc
//            if (jsonObject.has(AppConfig.khi_loc)) {
//                try {
//                    prefManager.putInt(PrefManager.KHI_LOC, jsonObject.getInt(AppConfig.khi_loc));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.KHI_LOC, -1);
//                }
//            }
//            //AT Bep
//            if (jsonObject.has(AppConfig.at_bep)) {
//                try {
//                    prefManager.putInt(PrefManager.AT_BEP, jsonObject.getInt(AppConfig.at_bep));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.AT_BEP, -1);
//                }
//            }
//            //AT Tong
//            if (jsonObject.has(AppConfig.at_tong)) {
//                try {
//                    prefManager.putInt(PrefManager.AT_TONG, jsonObject.getInt(AppConfig.at_tong));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putInt(PrefManager.AT_TONG, -1);
//                }
//            }
//            //Nhiet do va do am
//            if (jsonObject.has(AppConfig.temp_humi)) {
//                try {
//                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.temp_humi);
//                    double temp = jsonObject1.getDouble(AppConfig.KEY_TEMP);
//                    double humi = jsonObject1.getDouble(AppConfig.KEY_HUMI);
//                    prefManager.putDouble(PrefManager.TEMP, temp);
//                    prefManager.putDouble(PrefManager.HUMI, humi);
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putDouble(PrefManager.TEMP, -1);
//                    prefManager.putDouble(PrefManager.HUMI, -1);
//                }
//            }
//            //Khi CO bep
//            if (jsonObject.has(AppConfig.co)) {
//                try {
//                    prefManager.putDouble(PrefManager.KHOI_CO, jsonObject.getDouble(AppConfig.co));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putDouble(PrefManager.KHOI_CO, -1);
//                }
//            }
//            //Nong do bui min
//            if (jsonObject.has(AppConfig.dust)) {
//                try {
//                    prefManager.putDouble(PrefManager.DUST, jsonObject.getDouble(AppConfig.dust));
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putDouble(PrefManager.DUST, -1);
//                }
//            }
//            //Dong dien tong (Ampe & vol)
//            if (jsonObject.has(AppConfig.do_dien_tong)) {
//                try {
//                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConfig.do_dien_tong);
//                    double amp = jsonObject1.getDouble(AppConfig.KEY_AMPE);
//                    double vol = jsonObject1.getDouble(AppConfig.KEY_VOLTAGE);
//                    double energy = jsonObject1.getDouble(AppConfig.KEY_ENERGY);
//                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, amp);
//                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, vol);
//                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, energy);
//                } catch (JSONException e) {
//                    Log.e(TAG, e.getMessage());
//                    prefManager.putDouble(PrefManager.DONGDIEN_AMPE, -1);
//                    prefManager.putDouble(PrefManager.DONGDIEN_VOL, -1);
//                    prefManager.putDouble(PrefManager.CONG_SUAT_TIEU_THU, -1);
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
