package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.HolidayLoader;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;

import java.util.List;

/**
 * Provider of holidays: retrieves the holidays (either from internet or from calendar).
 */
public class HolidayDBProvider implements IHolidayProvider {

    private final Storage mStorage;
    private MultiHolidaysPresenter presenter;
    private ITask task;

    public HolidayDBProvider(Storage storage){
        this.mStorage = storage;
    }


    @Override
    public void lazyLoadAll(MultiHolidaysPresenter presenter) {
        this.presenter = presenter;
        HolidayLoader loader = new HolidayLoader(this.mStorage, this);
        loader.execute();

    }

    @Override
    public long save(Holiday holiday) {
        return mStorage.save(holiday.name, holiday.nextOccurrence, holiday.periodicity);
    }

    /**
     * Passes the list of holidays to whom it may concern.
     *
     * @param holidays
     */
    @Override
    public void onLoad(List<Holiday> holidays) {
        if (presenter != null){
            presenter.load(holidays);
        }

    }

    @Override
    public void lazyLoadForthcoming(MultiHolidaysPresenter presenter) {
        this.presenter = presenter;
        HolidayLoader loader = new HolidayLoader(this.mStorage, this);
        loader.execute();
    }

    @Override
    public void setTask(ITask task) {
        this.task = task;
    }
}
