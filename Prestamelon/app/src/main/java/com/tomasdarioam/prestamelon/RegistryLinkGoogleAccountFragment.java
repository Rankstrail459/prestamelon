package com.tomasdarioam.prestamelon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegistryLinkGoogleAccountFragment extends Fragment {

    private boolean mPair = false;
    public RegistryLinkGoogleAccountFragment(int index) {
        if((index % 2) == 0) mPair = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, mPair));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, !mPair));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry_link_google_account, container, false);
    }

    GoogleSignInClient googleSignInClient;

    SignInButton btnSignIn;

    FirebaseAuth firebaseAuth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSignIn = getView().findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private final int RC_SIGN_IN = 20;

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        //Revisar REQUEST CODE
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount googleAccount = task.getResult(ApiException.class);

                //ESTE TOKEN Y EL PROCESO DE FIREBASEAUTH HAY QUE SACARLO A LA ACTIVITY PARA USARLO EN EL FRAGMENT FINAL
                //firebaseAuthWithGoogle(account.getIdToken());
                String idToken = googleAccount.getIdToken();

                callback.linkedGoogleAccount(idToken);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("SIGN_IN", "Google sign in failed", e);
                // ...
            }
        }
    }

    /*
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SIGN_IN", "signInResult:failed code=" + e.getStatusCode());
            //updateUI((FirebaseUser) null);
        }
    }
    */

    LinkGoogleAccountListener callback;

    public void setLinkGoogleAccountListener(LinkGoogleAccountListener callback) {
        this.callback = callback;
    }

    public interface LinkGoogleAccountListener {
        void linkedGoogleAccount(String idToken);
    }

}