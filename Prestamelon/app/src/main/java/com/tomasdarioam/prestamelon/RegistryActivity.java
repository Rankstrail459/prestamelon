package com.tomasdarioam.prestamelon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RegistryActivity extends AppCompatActivity
        implements RegistryMessageFragment.TimerExpiredListener,
        RegistryLinkGoogleAccountFragment.LinkGoogleAccountListener,
        RegistryTakeProfilePhotoFragment.ProfilePhotoTakenListener,
        RegistryChooseProfileNameFragment.ProfileNameChosenListener,
        RegistryConfirmationFragment.ConfirmationListener,
        RegistryScanIdentificationDocumentFragment.IdentificationDocumentScannedListener {

    private MaterialButton mButtonNext;

    private List<Fragment> mRegisterFragmentsList;
    private FragmentManager mFragmentManager;
    private int mFragmentIndex = 0;
    private int mFragmentCounter = -1;

    private NewUser mNewUser;

    private final RegistryConfirmationFragment.ConfirmationRequestCodes mConfirmationFragment =
            RegistryConfirmationFragment.ConfirmationRequestCodes.RC_REGISTRY_CONFIRMATION;

    private void nextFragment() {
        mFragmentCounter++;
        changeFragment(mRegisterFragmentsList.get(mFragmentCounter));
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }


    private void firebaseAuthWithGoogle() {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(mNewUser.googleIdToken, null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGN_IN", "signInWithCredential:success");

                            String newUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("users").document(newUserUid).set(mNewUser.getPrestamelonUser());

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            String profilePhotoPath = String.format("profilePhotos/%s.jpg", newUserUid);
                            StorageReference profilePhotoRef = storage.getReference(profilePhotoPath);
                            //profilePhotoRef.putBytes(.profilePhoto);
                            profilePhotoRef.putBytes(bitmapToByteArray(mNewUser.profilePhoto));

                            String identificationDocumentPath = String.format("identificationDocuments/%s.jpg", newUserUid);
                            StorageReference identificationDocumentRef = storage.getReference(identificationDocumentPath);
                            identificationDocumentRef.putBytes(bitmapToByteArray(mNewUser.identificationDocument));
                            //FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGN_IN", "signInWithCredential:failure", task.getException());


                            //updateUI((FirebaseUser) null);
                        }

                        // ...
                    }
                });
    }

    private List<Fragment> createRegistryFragmentsList() {
        List<Fragment> registryFragmentsList = new ArrayList<>();

        registryFragmentsList.add(new RegistryScanIdentificationDocumentFragment(mFragmentIndex++));
        registryFragmentsList.add(new RegistryChooseProfileNameFragment(mFragmentIndex++));
        registryFragmentsList.add(new RegistryTakeProfilePhotoFragment(mFragmentIndex++));
        registryFragmentsList.add(new RegistryLinkGoogleAccountFragment(mFragmentIndex++));
        registryFragmentsList.add(new RegistryConfirmationFragment(mFragmentIndex++));

        return registryFragmentsList;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        byte[] bytes = null;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            bytes = stream.toByteArray();

            //Recomendado para la optimización
            /*
            stream.close();
            bitmap.recycle();
             */
        } catch(Exception exception) { }

        return bytes;
    }

    public void changeToConfirmChangesButton() {
        mButtonNext.setText("Confirmar");

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonNext.setEnabled(false);
                switch(mConfirmationFragment) {
                    case RC_RETURN_REGISTRY_CONFIRMATION:
                        changeFragment(new RegistryConfirmationFragment(mFragmentIndex++));
                    case RC_REGISTRY_CONFIRMATION:
                        firebaseAuthWithGoogle();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void profilePhotoTaken(Bitmap profilePhoto) {
        mNewUser.profilePhoto = profilePhoto;

        mButtonNext.setEnabled(true);
    }

    @Override
    public void profileNameChosen(String profileName) {
        mNewUser.profileName = profileName;
        mButtonNext.setEnabled(true);
    }

    @Override
    public void linkedGoogleAccount(String googleIdToken) {
        mNewUser.googleIdToken = googleIdToken;

        mButtonNext.setEnabled(true);
    }

    @Override
    public void timerExpired() {
        mButtonNext.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        mFragmentManager = getSupportFragmentManager();

        mRegisterFragmentsList = createRegistryFragmentsList();

        mButtonNext = findViewById(R.id.btnSiguiente);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonNext.setEnabled(false);
                if(mRegisterFragmentsList.size()-1 != mFragmentCounter)
                    nextFragment();
            }
        });

        nextFragment();

        mNewUser = new NewUser();
    }

    @Override
    public void confirmation(RegistryConfirmationFragment.ConfirmationRequestCodes requestCode) {
        if(requestCode == RegistryConfirmationFragment.ConfirmationRequestCodes.RC_EDIT_PROFILE_PHOTO)
            changeFragment(new RegistryTakeProfilePhotoFragment(mFragmentIndex++));
        else if(requestCode == RegistryConfirmationFragment.ConfirmationRequestCodes.RC_EDIT_PROFILE_NAME)
            changeFragment(new RegistryChooseProfileNameFragment(mFragmentIndex++));
    }

    @Override
    public void identificationDocumentScanned(BlinkIdRecognizer.Result identificationDocumentResult) {
        String firstNameResult = identificationDocumentResult.getFirstName();
        String lastNameResult = identificationDocumentResult.getLastName();

        mNewUser.saveAvailableProfileNames(firstNameResult, lastNameResult);

        mNewUser.identificationDocument = identificationDocumentResult.getFullDocumentImage().convertToBitmap();

        mButtonNext.setEnabled(true);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof RegistryMessageFragment) {
            RegistryMessageFragment mensajeFragment = (RegistryMessageFragment) fragment;

            mensajeFragment.setTimerExpiredListener(this);
        } else if (fragment instanceof RegistryScanIdentificationDocumentFragment) {
            RegistryScanIdentificationDocumentFragment vincularGoogleFragment =
                    (RegistryScanIdentificationDocumentFragment) fragment;

            vincularGoogleFragment.setIdentifcationDocumentScannedListener(this);
        } else if (fragment instanceof RegistryLinkGoogleAccountFragment) {
            RegistryLinkGoogleAccountFragment vincularGoogleFragment =
                    (RegistryLinkGoogleAccountFragment) fragment;

            vincularGoogleFragment.setLinkGoogleAccountListener(this);
        } else if (fragment instanceof RegistryTakeProfilePhotoFragment) {
            RegistryTakeProfilePhotoFragment capturarFotoPerfilFragment =
                    (RegistryTakeProfilePhotoFragment) fragment;

            capturarFotoPerfilFragment.setProfilePhotoTakenListener(this);
        } else if(fragment instanceof RegistryChooseProfileNameFragment) {
            RegistryChooseProfileNameFragment registryChooseProfileNameFragment =
                    (RegistryChooseProfileNameFragment) fragment;

            Bundle bundle = new Bundle();
            bundle.putStringArray("availableProfileNames", mNewUser.GetAvailableProfileNames());
            registryChooseProfileNameFragment.setArguments(bundle);

            registryChooseProfileNameFragment.setProfileNameChosenListener(this);
        } else if(fragment instanceof RegistryConfirmationFragment) {
            RegistryConfirmationFragment registryConfirmationFragment =
                    (RegistryConfirmationFragment) fragment;
            registryConfirmationFragment.setArguments(mNewUser.GetRegistryConfirmationBundle());

            registryConfirmationFragment.setConfirmationListener(this);

            changeToConfirmChangesButton();
            mButtonNext.setEnabled(true);

        }
    }

    class NewUser {
        public String profileName, googleIdToken;
        public Bitmap profilePhoto, identificationDocument;

        private String[] mAvailableProfileNames;

        public String[] GetAvailableProfileNames() {
            return mAvailableProfileNames;
        }

        public Bundle GetRegistryConfirmationBundle() {
            Bundle newUser = new Bundle();

            newUser.putString("profileName", profileName);
            newUser.putByteArray("profilePhoto", bitmapToByteArray(profilePhoto));

            return newUser;
        }

        public void saveAvailableProfileNames(String firstNameResult, String lastNameResult){
            int availableNamesCounter = 4;

            String firstName, middleName = null;

            if(firstNameResult.contains(" ")) {
                availableNamesCounter = 12;

                int nameSeparationPosition = firstNameResult.indexOf(' ');

                firstName = firstNameResult.substring(0, nameSeparationPosition);
                middleName = firstNameResult.substring(nameSeparationPosition + 1);
            } else {
                firstName = firstNameResult;
            }

            String[] availableNames = new String[availableNamesCounter];

            int lastNameSeparationPosition = lastNameResult.indexOf("\n");

            String firstLastName = lastNameResult.substring(0, lastNameSeparationPosition);
            String secondLastName = lastNameResult.substring(lastNameSeparationPosition + 1);


            availableNames[0] = firstName;
            availableNames[1] = firstName + ' ' + firstLastName;
            availableNames[2] = firstName + ' ' + secondLastName;
            availableNames[3] = firstName + ' ' + firstLastName + ' ' + secondLastName;

            if(availableNamesCounter == 12) {
                availableNames[4] = middleName;
                availableNames[5] = middleName + ' ' + firstLastName;
                availableNames[6] = middleName + ' ' + secondLastName;
                availableNames[7] = middleName + ' ' + firstLastName + ' ' + secondLastName;
                availableNames[8] = firstName + ' ' + middleName;
                availableNames[9] = firstName + ' ' + middleName + ' ' + firstLastName;
                availableNames[10] = firstName + ' ' + middleName + ' ' + secondLastName;
                availableNames[11] = firstName + ' ' + middleName + ' ' + firstLastName + ' ' + secondLastName;
            }

            mAvailableProfileNames = availableNames;
        }

        public PrestamelonUser getPrestamelonUser() {
            return new PrestamelonUser(profileName);
        }

        public class PrestamelonUser {
            private final String mProfileName;

            public PrestamelonUser(String profileName) {
                mProfileName = profileName;
            }

            public String getProfileName() {
                return mProfileName;
            }
        }
    }
}