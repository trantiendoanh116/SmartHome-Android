package com.iot.smarthome.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iot.smarthome.R;

public class Floor1Fragment extends Fragment {

    private TextView textLightStatus, textFanStatus, textAptStatus, textTemp, textHumi, textCo2;
    private Button btnFanOn, btnFanOff, btnChangLight, btnChangeApt;
    private int valueFan = 0, valueLight = 0, valueApt = 0;

    private DatabaseReference refLight, refApt, refFan, refTemp, refHumi, refCo2, refChangeLight, refChangeApt, refOffFan, refOnFan;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //staus
        refLight = firebaseDatabase.getReference("/values/light");
        refApt = firebaseDatabase.getReference("/values/apt");
        refFan = firebaseDatabase.getReference("/values/fan");
        refHumi = firebaseDatabase.getReference("/values/humi");
        refTemp = firebaseDatabase.getReference("/values/temp");
        refCo2 = firebaseDatabase.getReference("/values/co2");
        //action
        refChangeLight = firebaseDatabase.getReference("/action/change_light");
        refChangeApt = firebaseDatabase.getReference("/action/change_apt");
        refOffFan = firebaseDatabase.getReference("/action/off_fan");
        refOnFan = firebaseDatabase.getReference("/action/on_fan");
        initViews();
        setOnListener();

    }


    private void initViews() {

        btnChangLight = getView().findViewById(R.id.btn_change_light);
        textLightStatus = getView().findViewById(R.id.text_light_status);

        btnChangeApt = getView().findViewById(R.id.btn_change_fan);
        textAptStatus = getView().findViewById(R.id.text_apt_status);

        textFanStatus = getView().findViewById(R.id.text_fan_status);
        btnFanOff = getView().findViewById(R.id.button_off);
        btnFanOn = getView().findViewById(R.id.button_on);

        textCo2 = getView().findViewById(R.id.text_co2);
        textHumi = getView().findViewById(R.id.text_humidity);
        textTemp = getView().findViewById(R.id.text_temperature);
        //
        addEventListenerFirebase();
    }


    private void setOnListener() {
        btnChangLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refChangeLight.setValue(true);
//                if(valueLight ==0){
//                    refLight.setValue(1);
//                }else {
//                    refLight.setValue(0);
//                }
            }
        });

        btnChangeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refChangeApt.setValue(true);
//                if(valueApt ==0){
//                    refLight.setValue(1);
//                }else {
//                    refLight.setValue(0);
//                }
            }
        });


        btnFanOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refOffFan.setValue(true);
                //refFan.setValue(0);
            }
        });

        btnFanOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refOnFan.setValue(true);
//                valueFan++;
//                if (valueFan == 4) {
//                    valueFan = 1;
//                }
//                refFan.setValue(valueFan);
            }
        });

    }

    private void addEventListenerFirebase() {
        refLight.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 1) {
                    textLightStatus.setText("Bật");
                    valueLight = 1;
                } else {
                    valueLight = 0;
                    textLightStatus.setText("Tắt");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value light.", error.toException());
            }

        });

        refApt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 1) {
                    textAptStatus.setText("Bật");
                    valueApt = 1;
                } else {
                    textAptStatus.setText("Tắt");
                    valueApt = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value Apt.", error.toException());
            }

        });

        refFan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                valueFan = value;
                textFanStatus.setText(new StringBuilder().append("Tốc độ: ").append(value).toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value. fan", error.toException());
            }
        });

        refTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Integer.class);
                textTemp.setText(new StringBuilder().append(value).append("°C").toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value temp.", error.toException());
            }
        });
        refHumi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Integer.class);
                textHumi.setText(new StringBuilder().append(value).append("%").toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value humi.", error.toException());
            }
        });

        refCo2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Integer.class);
                textCo2.setText(String.valueOf(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value CO2.", databaseError.toException());
            }
        });
    }


}
