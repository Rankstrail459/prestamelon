package com.tomasdarioam.prestamelon;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanOffer {// implements Exclude {

    //private DateTimeRange[] mDeliveryAvailabilityDateTimeRanges;
    private Item mLoanItem;
    private List<DateTimeRange> mAvailabilityDateTimeRanges;
    private List<Comment> mComments;
    //private String mDocumentUid;

    public LoanOffer() {

    }

    public LoanOffer(Item loanItem, List<DateTimeRange> availabilityDateTimeRanges) {
        setLoanItem(loanItem);
        setAvailabilityDateTimeRanges(availabilityDateTimeRanges);
    }

    public Item getLoanItem() {
        return mLoanItem;
    }

    public void setLoanItem(Item loanItem) {
        mLoanItem = loanItem;
    }

    public void addLoanItemRating(int rating) {
        mLoanItem.addRating(rating);
    }

    public List<DateTimeRange> getAvailabilityDateTimeRanges() {
        return mAvailabilityDateTimeRanges;
    }

    public void setAvailabilityDateTimeRanges(List<DateTimeRange> AvailabilityDateTimeRanges) {
        mAvailabilityDateTimeRanges = AvailabilityDateTimeRanges;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    /*
    @Exclude
    public String getDocumentUid() {
        return mDocumentUid;
    }

    @Exclude
    public void setDocumentUid(String documentUid) {
        mDocumentUid = documentUid;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

     */


    /*
    public Map<String, Object> getStartDateTime() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDateTime", mDateTimeRange.getStartDate());
        map.put("daysDuration", mDateTimeRange.getDaysDuration());
        map.put("timeDuration", mDateTimeRange.getTimeDuration());

        return map;
    }

    public void setStartDateTime(Map<String, Object> map) {
        mDateTimeRange = new DateTimeRange(
                (Timestamp) map.get("startDateTime"),
                (int) map.get("daysDuration"),
                (int) map.get("timeDuration")
        );
    }
    */
}


