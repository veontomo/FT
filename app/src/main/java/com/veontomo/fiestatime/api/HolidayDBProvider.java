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

    public HolidayDBProvider(Storage storage){
        this.mStorage = storage;
    }

    @Override
    public long save(Holiday holiday) {
        return mStorage.save(holiday.name, holiday.nextOccurrence, holiday.periodicity);
    }


    @Override
    public List<Holiday> getHolidays() {
        return mStorage.getHolidays();
    }




}
