package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;

/**
 * Task interface.
 *
 * Retrieves mEvents from storage.
 */
public interface ITask {
    void execute();

    /**
     * Sets a instance that will be called once the presenter receives the data.
     * @param presenter
     */
    void setOnDataLoaded(MultiHolidaysPresenter presenter);
}
