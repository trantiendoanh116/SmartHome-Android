package com.iot.smarthome.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.iot.smarthome.AppConfig;

public class DocSnippets {
    private static final String TAG = "DocSnippets";
    private final FirebaseFirestore db;

    public DocSnippets(FirebaseFirestore db) {
        this.db = db;
    }

    public void listenValueDevice(String deviceID,FirestoreCallBack callBack) {
        final DocumentReference docRef = db.collection(AppConfig.FIRESTORE_COLLECTION_DEVICE).document(deviceID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
                }
            }
        });
    }

    public void listenValueSensor(String deviceID,FirestoreCallBack callBack) {
        final DocumentReference docRef = db.collection(AppConfig.FIRESTORE_COLLECTION_SENSOR).document(deviceID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
                }
            }
        });
    }


//    public void getDocument(FirestoreCallBack callBack) {
//        // [START get_document]
//        DocumentReference docRef = db.collection(AppConfig.FIRESTORE_CONLLECTION_PATH).document("tientruong");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//
//                        callBack.onSuccess(document.getData());
//                    } else {
//                        callBack.onError("No such document");
//                        Log.w(TAG, "No such document");
//                    }
//                } else {
//                    callBack.onError("Get failed with: " + task.getException());
//                    Log.e(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//        // [END get_document]
//    }


}
