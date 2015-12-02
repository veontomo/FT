package com.veontomo.fiestatime.api;

import javax.annotation.Nonnull;

/**
 * Represents a holiday that occurs just once
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
     * Returns true if the holiday date should be adjusted and false otherwise.
     * <p/>
     * For single-occurring mEvents no date adjustment is needed.
     *
     * @param time
     */
    @Override
    public boolean shouldAdjustDate(long time) {
        return false;
    }



    /**
     * For single-occurring mEvents no date adjustment is needed.
     *
     * @param time time in milliseconds
     */
    @Override
    public void adjustDate(long time) {
        return;
    }
}
