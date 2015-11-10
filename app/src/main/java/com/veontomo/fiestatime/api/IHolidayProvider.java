package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.presenters.MVPPresenter;
import com.veontomo.fiestatime.views.MVPView;

/**
 * Holiday provider interface
 */
public interface IHolidayProvider {
    /**
     * Loads the holidays from its source (db, file or network) into the presenter
     * @param presenter
     */
    void loadInto(AllHolidaysPresenter presenter);

}
