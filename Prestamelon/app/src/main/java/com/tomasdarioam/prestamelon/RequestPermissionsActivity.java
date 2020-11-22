package com.tomasdarioam.prestamelon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class RequestPermissionsActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSIONS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(getResources().getColor(R.color.secondaryDarkColor));
        setContentView(R.layout.activity_permissions_request);

        final TextView textMessage = findViewById(R.id.text_view_message);

        final boolean[] firstRequest = {true};

        MaterialButton mButtonRequestPermissions = findViewById(R.id.button_request_permissions);
        mButtonRequestPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                requestPermissions(permissions);




                if(!firstRequest[0])
                    for (String permission : permissions) {
                        if(!shouldShowRequestPermissionRationale(permission)) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        }
                    }

                if(firstRequest[0]) firstRequest[0] = false;
                /*

                for (String permission : permissions) {
                    if(!shouldShowRequestPermissionRationale(permission)) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        requestedPermissionsInSettings = true;
                        break;
                    }
                }

                if(requestedPermissionsInSettings) finish();
                */
            }
        });
    }

    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
    }

    private void permissionsAccepted() {
        setResult(RESULT_OK);

        finish();
    }

    private boolean mPermissionsRequested = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_PERMISSIONS) finish();

        boolean permissionsAccepted = true;

        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                permissionsAccepted = false;
            }
        }

        if (permissionsAccepted) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            permissionsAccepted();
        }
        else mPermissionsRequested = true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}