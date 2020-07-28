package com.iot.smarthome.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.iot.smarthome.AppConfig;
import com.iot.smarthome.R;
import com.iot.smarthome.common.PrefManager;
import com.iot.smarthome.utils.NetworkUtil;

public class SettingsActivity extends AppCompatActivity {
    final String TAG = SettingsActivity.class.getSimpleName();
    private ConstraintLayout mLayoutSignOut, mLayoutChangeServer;
    private Toolbar mToolbar;
    FirebaseAuth mAuth;
    private TextView txtNameServer;
    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_settings);
        prefManager = new PrefManager(this);

        mLayoutSignOut = findViewById(R.id.layout_sign_out);
        mLayoutChangeServer = findViewById(R.id.setting_server);
        txtNameServer = findViewById(R.id.txt_name_server);

        final String[] addressServer = AppConfig.SERVER_ADDRESS;
        final String[] nameServer = AppConfig.SERVER_NAME;
        final String currentServer = prefManager.getString(PrefManager.SERVER_ADDRESS, AppConfig.URL_SERVER_DEFAULT);
        if (TextUtils.equals(currentServer, addressServer[0])) {
            txtNameServer.setText(nameServer[0]);
        } else {
            txtNameServer.setText(nameServer[1]);
        }

        mLayoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isConnectedToNetwork(SettingsActivity.this)) {
                    signOut();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage(getString(R.string.all_msg_warning_network));
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        mLayoutChangeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Chọn server");
                builder.setItems(nameServer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newAddress = addressServer[which];
                        if (!TextUtils.equals(newAddress, currentServer)) {
                            showDialogWarningChangeServer(newAddress, nameServer[which]);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showDialogWarningChangeServer(final String newAddress, String nameServer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setMessage(String.format("Bạn có chắc chắn sử dụng %s làm server", nameServer));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                prefManager.putString(PrefManager.SERVER_ADDRESS, newAddress);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("EXTRA_INIT_DATA", true);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void signOut() {
        if (mAuth.getCurrentUser() != null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(getString(R.string.settings_msg_confirm_logout));
            alert.setPositiveButton(getString(R.string.settings_lbl_logout), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AuthUI.getInstance().signOut(SettingsActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    });
                }
            });
            alert.setNegativeButton(getString(R.string.all_txt_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(getString(R.string.all_msg_error));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            alert.show();
        }

    }
}
