package com.veontomo.fiestatime.api;


import android.support.annotation.NonNull;

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
        return mStorage.getEvents();
    }

    /**
     * Returns a holiday that comes first after given time
     * @param time time in milliseconds
     *
     * @return
     */
    @Override
    @NonNull
    public List<Event> getNearest(long time) {
        return mStorage.getNearest(time);

    }

    /**
     * Returns a list of mEvents whose dates should be adjusted.
     * @param time time in milliseconds
     */
    @Override
    public List<Event> toAdjustDate(long time) {
        List<Event> passed = mStorage.getEventsBefore(time);
        List<Event> result = new ArrayList<>();
        for (Event h : passed){
            if (h.shouldAdjustDate(time)){
                result.add(h);
            }
        }
        return result;

    }



}
