package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.presenters.MVPPresenter;
import com.veontomo.fiestatime.views.MVPView;

import java.util.List;

/**
 * Holiday provider interface
 */
public interface IHolidayProvider {
    /**
     * Loads the holidays from its source (db, file or network) into the presenter
     * @param presenter
     */
    void loadInto(AllHolidaysPresenter presenter);

    /**
     * Saves the holiday into a storage and returns the id with which the holiday can be retrieved.
     * @param holiday
     */
    long save(Holiday holiday);

    /**
     * Passes the list of holidays to whom it may concern.
     * @param holidays
     */
    void onLoad(List<Holiday> holidays);
}
