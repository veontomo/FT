package com.veontomo.fiestatime.views;

import com.veontomo.fiestatime.api.Holiday;

/**
 * Interface for View in a MVP pattern that is used to add holidays.
 */
public interface MultiHolidaysView extends MVPView {
    /**
     * Adds a holiday
     * @param h
     */
    void addHoliday(Holiday h);

    /**
     * Eliminates a holiday at given position
     * @param pos
     */
    void deleteHoliday(int pos);

    /**
     * This method is called when a click on a given holiday occurs
     * @param holiday
     */
    void onHolidayClick(Holiday holiday);

    /**
     * Updates a holiday
     * @param h
     */
    void updateHoliday(Holiday h);
}
