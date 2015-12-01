package com.veontomo.fiestatime.api;


import java.util.ArrayList;
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

    /**
     * Returns a list of holidays whose dates should be adjusted.
     * @param time time in milliseconds
     */
    @Override
    public List<Holiday> toAdjustDate(long time) {
        List<Holiday> passed = mStorage.getHolidaysBefore(time);
        if (passed == null || passed.size() == 0){
            return null;
        }
        List<Holiday> result = new ArrayList<>();
        for (Holiday h : passed){
            if (h.shouldAdjustDate(time)){
                result.add(h);
            }
        }
        return result;

    }

    /**
     * Updates record that is already present in the storage
     *
     * @param item
     */
    @Override
    public boolean update(Holiday item) {
        return mStorage.update(item);
    }


}
