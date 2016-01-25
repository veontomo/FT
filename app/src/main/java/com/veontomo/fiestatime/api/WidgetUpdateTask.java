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

    private WidgetPresenter provider;
    private IProvider<Event> itemProvider;

    public WidgetUpdateTask(WidgetPresenter caller, IProvider<Event> itemProvider) {
        this.provider = caller;
        this.itemProvider = itemProvider;
    }

    public WidgetUpdateTask() {
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
        provider.setDaysToNearest(daysBetween(timeNow, events.get(0).nextOccurrence));
        if (!events2.isEmpty()) {
            provider.setDaysToNextNearest(daysBetween(timeNow, events2.get(0).nextOccurrence));
        }
        provider.setNearestEvents(events);
    }

    /**
     * Gives the description of the number of days that remain until the event time.
     * <br>
     * If the event occurs (w.r.t. the reference time) in period
     * <ol>
     * <li>from 0 to 24 hours, then return +1.</li>
     * <li>from 24 to 48 hours, then return +2.</li>
     * <li>from 48 to 72 hours, then return +3.</li>
     * </ol>
     * <br>
     * If the event occurred (w.r.t. the reference time) in period
     * <ol>
     * <li>from 0 to 24 hours ago, then return 0.</li>
     * <li>from 24 to 48 hours ago, then return -1.</li>
     * <li>from 48 to 72 hours ago, then return -2.</li>
     * </ol>
     *
     * @param reference reference time
     * @param eventTime event time
     * @return
     */
    public int daysBetween(long reference, long eventTime) {
        if (eventTime > reference) {
            return (int) ((eventTime - reference) / MILLISEC_IN_DAY) + 1;
        }
        return (int) ((eventTime - reference) / MILLISEC_IN_DAY);
    }



    @Override
    public void onPostExecute(Void v) {
        this.provider.onUpdated();
    }

}
