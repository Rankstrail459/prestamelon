package com.tomasdarioam.prestamelon;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseTest extends AppCompatActivity {

    TextView textFirebaseTest;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        textFirebaseTest = findViewById(R.id.text_firebase_test);
        Test();
    }

    private void Test() {
        /*
        List<DateTimeRange> deliveryAvailabilityDateTimeRanges = new ArrayList<DateTimeRange>();

        deliveryAvailabilityDateTimeRanges.add(new DateTimeRange(
                DateUtils.create(2020, 11, 19, 14, 0),
                DateUtils.create(2020, 11, 21, 15, 30)
                ));
         */

        //LoanOffer loanOffer = new LoanOffer("burro", deliveryAvailabilityDateTimeRanges);

        //getPreferences(MODE_PRIVATE).edit().remove("tEGwsuLl9Z9FqtEdLR2R").apply();

        List<LoanOffer> loanOffers = new ArrayList<LoanOffer>();

        LoanOffer newLoanOffer = new LoanOffer();

        db = FirebaseFirestore.getInstance();

        db.collection("loanOffers").document().set()

        db.collection("loanOffers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();

                        if(querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                LoanOffer loanOffer = document.toObject(LoanOffer.class);
                                loanOffer.DocumentUid = document.getId();
                                loanOffers.add(loanOffer);
                            }

                            saveLoanOffers(loanOffers);
                        }
                    } else {
                        Log.d("FIRESTORE", "Error getting documents: ", task.getException());
                    }
                }
                });
    }

    private void getLoanOffers() {

    }

    private void saveLoanOffers(List<LoanOffer> loanOffers) {
        SharedPreferencesUtils.LoanOffers.setLoanOffers(this, loanOffers);

        List<LoanOffer> loanOffers2 = SharedPreferencesUtils.LoanOffers.getLoanOffers(this);

        if(loanOffers2 != null) {
            for (LoanOffer loanOffer : loanOffers2) {
                Log.d("FIRESTORE", loanOffer.DocumentUid);
            }
        }
    }


}