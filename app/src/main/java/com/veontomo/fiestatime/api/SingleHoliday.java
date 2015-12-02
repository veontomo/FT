package com.veontomo.fiestatime.api;

import javax.annotation.Nonnull;

/**
 * Represents a holiday that occurs just once
 */
public class SingleHoliday extends Holiday {
    public SingleHoliday(String name, long next) {
        this(-1, name, next);
    }

    public SingleHoliday(long id, @Nonnull String name, long next) {
        this.id = id;
        this.name = name;
        this.nextOccurrence = next;
    }


    /**
     * Returns true if the holiday date should be adjusted and false otherwise.
     * <p/>
     * For single-occurring holidays no date adjustment is needed.
     *
     * @param time
     */
    @Override
    public boolean shouldAdjustDate(long time) {
        return false;
    }

    /**
     * Returns the serialized version of the instance.
     *
     * @return
     */
    @Override
    public String serialize() {
        String separator = "#";
        return "SingleHoliday" + separator + String.valueOf(id) + separator + String.valueOf(nextOccurrence) + separator + name;
    }

    /**
     * For single-occurring holidays no date adjustment is needed.
     *
     * @param time time in milliseconds
     */
    @Override
    public void adjustDate(long time) {
        return;
    }
}
