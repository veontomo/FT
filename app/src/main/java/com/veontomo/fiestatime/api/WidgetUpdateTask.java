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
        provider.setNearest(daysBetween(timeNow, events.get(0).nextOccurrence));
        if (!events2.isEmpty()) {
            provider.setNextNearest(daysBetween(timeNow, events2.get(0).nextOccurrence));
        }
        provider.setDescription(getEventsInfo(events));

    }

    /**
     * Calculates the number of complete days (24 hours) that interval [t1, t2] contains.
     *
     * @param t1 start time
     * @param t2 end time
     * @return
     */
    public int daysBetween(long t1, long t2) {
        if (t2 > t1) {
            return (int) ((t2 - t1) / MILLISEC_IN_DAY) + 1;
        }
        return (int) ((t2 - t1) / MILLISEC_IN_DAY);
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
