package com.tomasdarioam.prestamelon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class HubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        /*
        FragmentManager mFragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //transaction.replace(R.id.frame_layout_hub, new HubOffersCatalogueFragment());



        transaction.commit();

         */

        Intent intent = new Intent(HubActivity.this, PostNewOfferActivity.class);

        startActivity(intent);
    }
}