package com.tomasdarioam.prestamelon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    private final int REQUEST_CODE_PERMISSIONS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

         */

        //googleSignInClient = GoogleSignIn.getClient(this, gso);

        //firebaseAuth = FirebaseAuth.getInstance();
    }

    //private GoogleSignInClient googleSignInClient;
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();

        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        boolean havePermissions = true;

        for (String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                havePermissions = false;

                Intent intent = new Intent(MainActivity.this, RequestPermissionsActivity.class);

                startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
            }
        }

        if(havePermissions) checkUser();
    }

    private void login() {
        startActivity(new Intent(MainActivity.this, FirebaseTest.class));
    }

    private void register() {
        Intent intent = new Intent(this, RegistryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void checkUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) register();
        else login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PERMISSIONS && resultCode == RESULT_OK) checkUser();
        else finish();
    }
}