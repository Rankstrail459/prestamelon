package com.tomasdarioam.prestamelon;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;

class DateUtils {
    public static Date create(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);
        return calendar.getTime();
    }
}

class DateTimeRange {
    private Date mStartDateTime;
    private Date mEndDateTime;

    public DateTimeRange(Date startDateTime, Date endDateTime) {
        changeStartDateTime(startDateTime);
        changeEndDateTime(endDateTime);
    }

    public DateTimeRange(Timestamp startDateTime, Timestamp endDateTime) {
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
    }

    public DateTimeRange() {

    }

    public Timestamp getStartDate() {
        return new Timestamp(mStartDateTime);
    }

    public void setStartDateTime(Timestamp startDateTime) {
        mStartDateTime = startDateTime.toDate();
    }

    public void changeStartDateTime(Date startDateTime) {
        mStartDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return new Timestamp(mEndDateTime);
    }

    public void setEndDateTime(Timestamp endDateTime) {
        mEndDateTime = endDateTime.toDate();
    }

    public void changeEndDateTime(Date endDateTime) {
        mEndDateTime = endDateTime;
    }
}

