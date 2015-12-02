package com.veontomo.fiestatime.api;

import android.support.annotation.NonNull;

import com.veontomo.fiestatime.Logger;

import java.util.Calendar;

import javax.annotation.Nonnull;

/**
 * Holiday
 *
 */
public abstract class Holiday {
    /**
     * A string used to serialize Holiday instance
     */
    private final static String SEPARATOR = "#";

    protected String name;
    protected long nextOccurrence;
    protected long id;

//    public Holiday(String name, long next, int periodicity){
//        this(-1, name, next, periodicity);
//    }
//
//    public Holiday(long id, @Nonnull String name, long next, int periodicity){
//        this.name = name;
//        this.nextOccurrence = next;
//        this.periodicity = periodicity;
//        this.id = id;
//    }

    /**
     * Naive way to stringify a holiday.
     * One might change implementation of this method.
     * It is used for the ArrayAdapter.
     * @return
     */
    public String toString(){
        return this.name;
    }

    /**
     * Naive version of the serialization of a Holiday instance
     * @return
     */
    public String serialize() {
        return String.valueOf(id) + SEPARATOR + String.valueOf(nextOccurrence) + SEPARATOR + String.valueOf(periodicity) + SEPARATOR + name;
    }

    /**
     * Creates a holiday instance from a string.
     * It is inverse procedure to {@link #serialize()}.
     * @param holidayStr
     * @return
     */
    public static Holiday deserialize(String holidayStr){
        String[] arr = holidayStr.split(SEPARATOR, -2);
        Holiday h = null;
        if (arr.length == 4){
            long id = Long.parseLong(arr[0], 10);
            long next = Long.parseLong(arr[1], 10);
            int periodicity = Integer.parseInt(arr[2]);
            String name = arr[3];
            h = new Holiday(id, name, next, periodicity);
        }
        return h;
    }

    /**
     * Set the holiday date such that its next occurrence is after given time based in its periodicity.
     * @param time time in milliseconds
     */
    public abstract void adjustDate(long time);
//    {
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(this.nextOccurrence);
//        int period;
//        switch (this.periodicity){
//            case Holiday.MONTH:
//                period = Calendar.MONTH;
//                break;
//            case Holiday.WEEKLY:
//                period = Calendar.WEEK_OF_YEAR;
//                break;
//            case Holiday.YEAR:
//                period = Calendar.YEAR;
//                break;
//            default:
//                period = -1;
//        }
//        if (period == -1){
//            return;
//        }
//        while (nextOccurrence < time) {
//            c.add(period, 1);
//            nextOccurrence = c.getTimeInMillis();
//        }
//
//
//    }

    /**
     * Returns true if the holiday date should be adjusted and false otherwise.
     */
    public abstract boolean shouldAdjustDate(long time);
}
