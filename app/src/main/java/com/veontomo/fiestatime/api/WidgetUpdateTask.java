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
        for (Event h : events){
            h.adjustDate(time);
            itemProvider.save(h);
        }
        List<Event> event = itemProvider.getNearest(time);
        if (event != null) {
            updateProvider(event.get(0));
        }
        return null;
    }

    /**
     * Updates provider data based on what mEvents is coming.
     */
    private void updateProvider(@NonNull Event event) {
        int days = (int) ((event.nextOccurrence - System.currentTimeMillis()) / MILLISEC_IN_DAY);
        provider.setNearest(days);
        provider.setNextNearest(days + 8);
        provider.setDescription(event.name);
    }

    @Override
    public void onPostExecute(Void v) {
        this.provider.onUpdated();
    }

    ;
}
