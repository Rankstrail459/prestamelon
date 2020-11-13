package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.transition.platform.MaterialSharedAxis;

public class RegistryConfirmationFragment extends Fragment {

    private boolean mPair = false;
    public RegistryConfirmationFragment(int index) {
        if((index % 2) == 0) mPair = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, mPair));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, !mPair));
        /*
        byte[] bytes = newUser.getByteArray("profilePhoto");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mNewUser.mProfilePhoto = bitmap;

        bytes = newUser.getByteArray("fullDocumentImage");
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mNewUser.mFullDocumentImage = bitmap;
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeNewUserEditableInformation();

        Button buttonEditProfilePhoto = view.findViewById(R.id.button_edit_profile_photo);

        buttonEditProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.confirmation(ConfirmationRequestCodes.RC_EDIT_PROFILE_PHOTO);
            }
        });

        Button buttonEditProfileName = view.findViewById(R.id.button_edit_profile_name);

        buttonEditProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.confirmation(ConfirmationRequestCodes.RC_EDIT_PROFILE_NAME);
            }
        });
    }

    private void placeNewUserEditableInformation() {
        View view = getView();

        Bundle newUser = getArguments();

        TextView textProfileName = view.findViewById(R.id.text_profile_name);
        textProfileName.setText(newUser.getString("profileName"));

        ImageView imageProfilePhoto = view.findViewById(R.id.image_profile_photo);
        byte[] byteArray = newUser.getByteArray("profilePhoto");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageProfilePhoto.setImageBitmap(bitmap);
    }

    ConfirmationListener callback;

    public void setConfirmationListener(ConfirmationListener callback) {
        this.callback = callback;
    }

    public enum ConfirmationRequestCodes {
        RC_EDIT_PROFILE_PHOTO, RC_EDIT_PROFILE_NAME, RC_REGISTRY_CONFIRMATION, RC_RETURN_REGISTRY_CONFIRMATION
    }

    public interface ConfirmationListener {
        void confirmation(ConfirmationRequestCodes requestCode);
    }
}