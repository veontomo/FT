package com.veontomo.fiestatime.api;

import java.util.Calendar;

import javax.annotation.Nonnull;

/**
 *  Represents an event that occurs once a year
 */
public class YearEvent extends Event {
    public YearEvent(String name, long next) {
        this(-1, name, next);
    }

    public YearEvent(long id, @Nonnull String name, long next) {
        this.id = id;
        this.name = name;
        this.nextOccurrence = next;
    }

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
     * Returns true if the event date should be adjusted and false otherwise.
     *
     * @param time
     */
    @Override
    public boolean shouldAdjustDate(long time) {
        return this.nextOccurrence < time;
    }
}
