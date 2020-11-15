package com.tomasdarioam.prestamelon;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanOffer {
    public String DocumentUid;

    private String mUserUid;
    private String mItemName;

    //private DateTimeRange[] mDeliveryAvailabilityDateTimeRanges;
    private List<DateTimeRange> mDeliveryAvailabilityDateTimeRanges;
    private List<Comment> mComments;

    public LoanOffer() {

    }

    public LoanOffer(String itemName, List<DateTimeRange> deliveryAvailabilityDateTimeRanges) {
        setItemName(itemName);
        setDeliveryAvailabilityDateTimeRanges(deliveryAvailabilityDateTimeRanges);
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public List<DateTimeRange> getDeliveryAvailabilityDateTimeRanges() {
        return mDeliveryAvailabilityDateTimeRanges;
    }

    public void setDeliveryAvailabilityDateTimeRanges(List<DateTimeRange> deliveryAvailabilityDateTimeRanges) {
        this.mDeliveryAvailabilityDateTimeRanges = deliveryAvailabilityDateTimeRanges;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

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


