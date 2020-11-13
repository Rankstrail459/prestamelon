package com.tomasdarioam.prestamelon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.transition.platform.MaterialSharedAxis;

public class RegistryChooseProfileNameFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry_choose_profile_name, container, false);
    }

    private boolean mPair = false;
    public RegistryChooseProfileNameFragment(int index) {
        if((index % 2) == 0) mPair = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, mPair));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, !mPair));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View fragmentView = getView();

        final String[] availableProfileNames = getArguments().getStringArray("availableProfileNames");

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(
                        getContext(),
                        R.layout.item_simple_cream,
                        R.id.txvItem,
                        availableProfileNames);

        AutoCompleteTextView nombresDesplegable = fragmentView.findViewById(R.id.nombresDesplegable);

        nombresDesplegable.setAdapter(adaptador);

        nombresDesplegable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nextFragment(availableProfileNames[i]);

            }
        });
    }

    private void nextFragment(String profileName) {
        callback.profileNameChosen(profileName);
    }

    ProfileNameChosenListener callback;

    public void setProfileNameChosenListener(ProfileNameChosenListener callback) {
        this.callback = callback;
    }

    public interface ProfileNameChosenListener {
        void profileNameChosen(String profileName);
    }
}
