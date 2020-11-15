package com.tomasdarioam.prestamelon;

import java.util.Collection;
import java.util.function.Predicate;

public class SearchUtils {
    private static <T> T findFirstByProperty(Collection<T> collection, Predicate<T> filter) {
        return collection.stream().filter(filter).findFirst().orElse(null);
    }

    private static <T> T findAllByProperty(Collection<T> collection, Predicate<T> filter) {
        return collection.stream().filter(filter).findAny().orElse(null);
    }

    public static class LoanOffers {
        public static LoanOffer findByItemName(Collection<LoanOffer> loanOffers, String itemName) {
            return findFirstByProperty(loanOffers, loanOffer -> itemName.equals(loanOffer.getItemName()));
        }
    }
}


