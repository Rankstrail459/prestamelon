package com.tomasdarioam.prestamelon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesUtils {
    private static final Type LOAN_OFFERS_TYPE = new TypeToken<List<LoanOffer>>() {}.getType();

    static class LoanOffers {
        public static List<LoanOffer> getLoanOffers(Context context) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            String json = sharedPreferences.getString("loanOffers", null);

            return new Gson().fromJson(json, LOAN_OFFERS_TYPE);
        }

        public static void setLoanOffers(Context context, List<LoanOffer> loanOffers) {
            String json = new Gson().toJson(loanOffers);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("loanOffers", json);
            editor.apply();
        }

        public static void addLoanOffer(Context context, LoanOffer loanOffer) {

        }

        public static void changeLoanOffer(Context context, String loanOfferId, LoanOffer newLoanOffer) {

        }
    }

}
