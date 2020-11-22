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
import com.google.type.DateTime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
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


        CarFeatures carFeatures = new CarFeatures(Features.Grades.HIGH, Features.CarTypes.SPORTS);

        Item loanItem = new Item("Cochazo", "kek", "Buen coche", carFeatures, new CarSubcategory());

        loanItem.addRating(1);
        loanItem.addRating(5);

        List<DateTimeRange> availability = new ArrayList<>();

        availability.add(new DateTimeRange(
                DateUtils.create(2021, 0, 1, 0, 0),
                DateUtils.create(2021, 0, 1, 1, 0)
        ));

        availability.add(new DateTimeRange(
                DateUtils.create(2022, 1, 1, 0, 0),
                DateUtils.create(2022, 1, 1, 1, 0)
        ));

        LoanOffer loanOffer = new LoanOffer(loanItem, availability);

        List<Comment> comments = new ArrayList<>();

        Comment comment = new Comment("lol", "Vaya mierda de buga, man");
        comment.addCommentReply(new BaseComment("kek", "Mierda tu"));
        comment.addCommentReply(new BaseComment("lol", "xdddddddddd"));
        comments.add(comment);

        loanOffer.setComments(comments);

        db = FirebaseFirestore.getInstance();

        db.collection("loanOffers").document().set(loanOffer);
        /*
        LoanOffer newLoanOffer = new LoanOffer("cabritillo", availability);

        List<Comment> comments = new ArrayList<>();

        comments.add(new Comment("123", "Buenas", DateUtils.create(2021, 1, 1, 0, 0)));
        comments.add(new Comment("123", "Adiós", DateUtils.create(2021, 1, 1, 1, 0)));

        newLoanOffer.setComments(comments);



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
        */
    }

    private void getLoanOffers() {

    }

    private void saveLoanOffers(List<LoanOffer> loanOffers) {
        SharedPreferencesUtils.LoanOffers.setLoanOffers(this, loanOffers);

        List<LoanOffer> loanOffers2 = SharedPreferencesUtils.LoanOffers.getLoanOffers(this);

        /*
        if(loanOffers != null) {
            for (LoanOffer loanOffer : loanOffers) {
                Log.d("FIRESTORE", loanOffer.DocumentUid);
            }
        }
         */
    }


}