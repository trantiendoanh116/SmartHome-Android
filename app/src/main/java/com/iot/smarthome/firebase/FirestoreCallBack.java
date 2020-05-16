package com.iot.smarthome.firebase;

import java.util.Map;

public interface FirestoreCallBack {
    void onSuccess(Map<String, Object> result);

    void onError(String err);
}
