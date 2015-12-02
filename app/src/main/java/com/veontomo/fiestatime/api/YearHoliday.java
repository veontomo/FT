package com.veontomo.fiestatime.api;

import java.util.Calendar;

/**
 *  Represents a holiday with year periodicity
 */
public class YearHoliday extends Holiday {
    /**
     * Set the holiday date such that its next occurrence is after given time based in its periodicity.
     *
     * @param time time in milliseconds
     */
    @Override
    public void adjustDate(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.nextOccurrence);
        while (nextOccurrence < time) {
            c.add(Calendar.YEAR, 1);
            nextOccurrence = c.getTimeInMillis();
        }
    }

    /**
     * Returns true if the holiday date should be adjusted and false otherwise.
     *
     * @param time
     */
    @Override
    public boolean shouldAdjustDate(long time) {
        return false;
    }
}
