package com.veontomo.fiestatime.views;

import com.veontomo.fiestatime.api.Holiday;

/**
 * Interface for View in a MVP pattern that is used to add holidays.
 */
public interface AllHolidaysView extends MVPView {
    void addHoliday(Holiday h);

    void onHolidayClick(Holiday holiday);
}
