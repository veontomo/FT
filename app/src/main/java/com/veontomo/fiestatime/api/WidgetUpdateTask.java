package com.veontomo.fiestatime.api;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.fiestatime.presenters.WidgetPresenter;

import java.util.List;

/**
 * Updates the state of the widget in the asynchronous manner.
 */
public class WidgetUpdateTask extends AsyncTask<Void, Void, Void> {

    /**
     * The number of milliseconds in a day
     */
    private static final int MILLISEC_IN_DAY = 1000 * 60 * 60 * 24;

    private final WidgetPresenter provider;
    private final IProvider<Event> itemProvider;

    public WidgetUpdateTask(WidgetPresenter caller, IProvider<Event> itemProvider) {
        this.provider = caller;
        this.itemProvider = itemProvider;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final long time = System.currentTimeMillis() - MILLISEC_IN_DAY;
        List<Event> nearestEvents = itemProvider.toAdjustDate(time);
        for (Event h : nearestEvents) {
            h.adjustDate(time);
            itemProvider.save(h);
        }
        nearestEvents = itemProvider.getNearest(time);
        if (!nearestEvents.isEmpty()) {
            long nearestDate = nearestEvents.get(0).nextOccurrence;
            List<Event> afterNearestEvents = itemProvider.getNearest(nearestDate);
            updateProvider(nearestEvents, afterNearestEvents);
        }
        return null;
    }

    /**
     * Updates the provider based on two lists: the nearest events and events that come
     * immediately after nearest ones.
     *
     * @param events  list of nearest events. It is supposed to be not null and not empty
     * @param events2 list of after-nearest events. It is supposed to be not null, but might be empty
     */
    private void updateProvider(@NonNull final List<Event> events, @NonNull final List<Event> events2) {
        long timeNow = System.currentTimeMillis();
        provider.setNearest(daysToEvent(events.get(0).nextOccurrence, timeNow));
        if (!events2.isEmpty()) {
            provider.setNextNearest(daysToEvent(events2.get(0).nextOccurrence, timeNow));
        }
        provider.setDescription(getEventsInfo(events));

    }

    /**
     * Calculates the number of days from moment of time t2 to moment of time t1
     *
     * @param t1
     * @param t2
     * @return
     */
    public int daysToEvent(long t1, long t2) {
        return (int) ((t1 - t2) / MILLISEC_IN_DAY) + 1;
    }

    private String getEventsInfo(@NonNull final List<Event> events) {
        StringBuilder builder = new StringBuilder();
        for (Event e : events) {
            builder.append(e.name);
            builder.append(System.getProperty("line.separator", " "));
        }
        return builder.toString();
    }

    @Override
    public void onPostExecute(Void v) {
        this.provider.onUpdated();
    }

}
