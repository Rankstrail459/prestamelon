package com.tomasdarioam.prestamelon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.microblink.MicroblinkSDK;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.locale.LanguageUtils;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BlinkIdUISettings;

public class RegistryScanIdentificationDocumentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry_scan_identification_document, container, false);
    }

    private boolean mPair = false;
    public RegistryScanIdentificationDocumentFragment(int index) {
        if((index % 2) == 0) mPair = true;
    }

    MaterialButton mBtnScanIdDocument;
    TextView txvResultadoEscaner, txvMensaje;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View fragmentView = getView();

        mBtnScanIdDocument = fragmentView.findViewById(R.id.btn_scan_identification_document);

        mBtnScanIdDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanning();
            }
        });

        txvResultadoEscaner = fragmentView.findViewById(R.id.txvResultadoEscaner);

        txvMensaje = fragmentView.findViewById(R.id.txvMensaje);
    }

    //private BlinkIdCombinedRecognizer recognizer;
    private BlinkIdRecognizer recognizer;
    private RecognizerBundle recognizerBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, mPair));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, !mPair));

        MicroblinkSDK.setLicenseFile("MB_com.tomasdarioam.prestamelon_BlinkID_Android_2020-11-25.mblic", getContext());

        recognizer = new BlinkIdRecognizer();
        recognizer.setReturnFullDocumentImage(true);
        //recognizer = new BlinkIdCombinedRecognizer();
        // bundle recognizers into RecognizerBundle
        recognizerBundle = new RecognizerBundle(recognizer);
    }

    public void startScanning() {
        // Settings for BlinkIdActivity
        BlinkIdUISettings settings = new BlinkIdUISettings(recognizerBundle);

        LanguageUtils.setLanguageAndCountry("es", "", getContext());

        // tweak settings as you wish

        // Start activity
        ActivityRunner.startActivityForResult(this, RC_SCAN_DNI, settings);
    }

    int RC_SCAN_DNI = 22;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SCAN_DNI) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // load the data into all recognizers bundled within your RecognizerBundle
                recognizerBundle.loadFromIntent(data);

                // now every recognizer object that was bundled within RecognizerBundle
                // has been updated with results obtained during scanning session

                // you can get the result by invoking getResult on recognizer
                BlinkIdRecognizer.Result result = recognizer.getResult();
                //BlinkIdCombinedRecognizer.Result result = recognizer.getResult();

                //Comprobar que sea menor de edad

                //¿Quizás comprobar que escanea muchas veces?
                String resultadoEscaner = "Comprueba que tu nombre completo esté correcto,\nsi no vuelve a escanear:\n";

                int posicionNombre = resultadoEscaner.length();

                resultadoEscaner += result.getFirstName() + "\n";
                resultadoEscaner += result.getLastName();
                //resultadoEscaner += result.getLastName().replace("\n", " ");

                Spannable spannable = new SpannableString(resultadoEscaner);
                spannable.setSpan(new ForegroundColorSpan(getContext().getColor(R.color.secondaryDarkColor)), posicionNombre, resultadoEscaner.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new RelativeSizeSpan(1.2f), posicionNombre, resultadoEscaner.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                txvResultadoEscaner.setText(spannable);
                
                txvMensaje.setVisibility(View.GONE);

                txvResultadoEscaner.setVisibility(View.VISIBLE);

                //Hacer un nextFragment como en los demás
                callback.identificationDocumentScanned(result);

                /*
                if (result.getResultState() == recognizer.Result.State.Valid) {
                    // result is valid, you can use it however you wish
                }
                 */

            }
        }


    }

    IdentificationDocumentScannedListener callback;

    public void setIdentifcationDocumentScannedListener(IdentificationDocumentScannedListener callback) {
        this.callback = callback;
    }

    public interface IdentificationDocumentScannedListener {
        void identificationDocumentScanned(BlinkIdRecognizer.Result identificationDocumentResult);
    }
}
