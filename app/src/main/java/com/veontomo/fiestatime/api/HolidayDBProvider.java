package com.veontomo.fiestatime.api;


import java.util.List;

/**
 * Provider of holidays: retrieves the holidays (either from internet or from calendar).
 */
public class HolidayDBProvider implements IProvider<Holiday> {

    private final Storage mStorage;

    public HolidayDBProvider(Storage storage){
        this.mStorage = storage;
    }

    @Override
    public long save(Holiday holiday) {
        return mStorage.save(holiday.name, holiday.nextOccurrence, holiday.periodicity);
    }


    @Override
    public List<Holiday> getItems() {
        return mStorage.getHolidays();
    }

    /**
     * Returns forthcoming holiday
     *
     * @return
     */
    @Override
    public Holiday getNearest() {
        return mStorage.getNearest();

    }


}
