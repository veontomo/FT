package com.veontomo.fiestatime.api;


import java.util.ArrayList;
import java.util.List;

/**
 * Provider of mEvents: retrieves the mEvents (either from internet or from calendar).
 */
public class EventDBProvider implements IProvider<Event> {

    private final Storage mStorage;

    public EventDBProvider(Storage storage){
        this.mStorage = storage;
    }

    @Override
    public long save(Event event) {
        return mStorage.save(event);
    }


    @Override
    public List<Event> getItems() {
        return mStorage.getHolidays();
    }

    /**
     * Returns a holiday that comes first after given time
     * @param time time in milliseconds
     *
     * @return
     */
    @Override
    public Event getNearest(long time) {
        return mStorage.getNearest(time);

    }

    /**
     * Returns a list of mEvents whose dates should be adjusted.
     * @param time time in milliseconds
     */
    @Override
    public List<Event> toAdjustDate(long time) {
        List<Event> passed = mStorage.getHolidaysBefore(time);
        if (passed == null || passed.size() == 0){
            return null;
        }
        List<Event> result = new ArrayList<>();
        for (Event h : passed){
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
    public boolean update(Event item) {
        return mStorage.update(item);
    }


}
