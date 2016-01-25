package com.veontomo.fiestatime.api;

import javax.annotation.Nonnull;

/**
 * Represents an event that occurs just once
 */
public class SingleEvent extends Event {
    public SingleEvent(String name, long next) {
        this(-1, name, next);
    }

    public SingleEvent(long id, @Nonnull String name, long next) {
        this.id = id;
        this.name = name;
        this.nextOccurrence = next;
    }


    /**
     * Returns true if the event date should be adjusted and false otherwise.
     * <p/>
     * For single-occurring events no date adjustment is needed.
     *
     * @param time
     */
    @Override
    public boolean shouldAdjustDate(long time) {
        return false;
    }



    /**
     * For single-occurring events no date adjustment is needed.
     *
     * @param time time in milliseconds
     */
    @Override
    public void adjustDate(long time) {
        // no date adjustment for this class
    }
}
