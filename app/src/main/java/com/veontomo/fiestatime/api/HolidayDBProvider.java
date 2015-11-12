package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.HolidayLoader;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;

/**
 * Provider of holidays: retrieves the holidays (either from internet or from calendar).
 */
public class HolidayDBProvider implements IHolidayProvider {

    private final Storage mStorage;

    public HolidayDBProvider(Storage storage){
        this.mStorage = storage;
    }


    @Override
    public void loadInto(AllHolidaysPresenter presenter) {
        HolidayLoader loader = new HolidayLoader(this.mStorage, presenter);
        loader.execute();

    }

    @Override
    public long save(Holiday holiday) {
        return mStorage.save(holiday.name, holiday.nextOccurrence, holiday.periodicity);
    }
}
