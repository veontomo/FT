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
        long time = System.currentTimeMillis();
        List<Event> events = itemProvider.toAdjustDate(System.currentTimeMillis());
        for (Event h : events) {
            h.adjustDate(time);
            itemProvider.save(h);
        }
        events = itemProvider.getNearest(time);
        updateProvider(events);
        return null;
    }

    /**
     * Updates provider data based on what events is coming.
     */
    private void updateProvider(@NonNull final List<Event> events) {
        Event e = events.get(0);
        int days = (int) ((e.nextOccurrence - System.currentTimeMillis()) / MILLISEC_IN_DAY) + 1 ;
        provider.setNearest(days);
        provider.setNextNearest(days + 8);
        provider.setDescription(getEventsInfo(events));
    }

    private String getEventsInfo(@NonNull final List<Event> events){
        StringBuilder builder = new StringBuilder();
        for (Event e : events){
            builder.append(e.name);
            builder.append(" ");
        }
        return builder.toString();
    }

    @Override
    public void onPostExecute(Void v) {
        this.provider.onUpdated();
    }

    ;
}
