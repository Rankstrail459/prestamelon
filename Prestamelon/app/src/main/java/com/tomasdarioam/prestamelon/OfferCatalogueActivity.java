package com.tomasdarioam.prestamelon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class OfferCatalogueActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        //setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        //setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        findViewById(android.R.id.content).setTransitionName("shared_element_container");

        MaterialContainerTransform materialContainerTransform1 = new MaterialContainerTransform();
        materialContainerTransform1.addTarget(android.R.id.content);
        materialContainerTransform1.setDuration(300L);
        materialContainerTransform1.setFadeMode(MaterialContainerTransform.FADE_MODE_IN);


        MaterialContainerTransform materialContainerTransform2 = new MaterialContainerTransform();
        materialContainerTransform2.addTarget(android.R.id.content);
        materialContainerTransform2.setDuration(250L);
        materialContainerTransform2.setFadeMode(MaterialContainerTransform.FADE_MODE_IN);

        window.setSharedElementEnterTransition(materialContainerTransform1);
        //window.setSharedElementExitTransition(materialContainerTransform2);

        window.setSharedElementsUseOverlay(false);;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offer_catalogue);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_offer_catalogue);

        recyclerView = findViewById(R.id.recycler_view_offers);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;

            switch(item.getItemId()) {
                case R.id.menu_item_my_loans:
                    intent = new Intent(this, MyLoansActivity.class);
                    break;
                default:
                    return false;
            }

            Pair<View, String> pairMainContainer = new Pair<>(recyclerView, "shared_element_main_container");
            Pair<View, String> pairNavigationContainer = new Pair<>(bottomNavigationView, "shared_element_navigation_container");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    this, pairMainContainer, pairNavigationContainer
            );

            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent, options.toBundle());

            return true;
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}