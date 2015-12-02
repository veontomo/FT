package com.veontomo.fiestatime.api;

import android.support.annotation.NonNull;

import com.veontomo.fiestatime.Logger;

import java.util.Calendar;

import javax.annotation.Nonnull;

/**
 * Event
 *
 */
public abstract class Event {
    protected String name;
    protected long nextOccurrence;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getNextOccurrence() {
        return nextOccurrence;
    }

    protected long id;

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
     * Returns the serialized version of the instance.
     *
     * @return
     */
    public String serialize() {
        final String separator = "#";
        return getClass().getSimpleName() + separator + String.valueOf(id) + separator + String.valueOf(nextOccurrence) + separator + name;
    }


    /**
     * Set the holiday date such that its next occurrence is after given time based in its periodicity.
     * @param time time in milliseconds
     */
    public abstract void adjustDate(long time);

    /**
     * Returns true if the holiday date should be adjusted and false otherwise.
     */
    public abstract boolean shouldAdjustDate(long time);


}
