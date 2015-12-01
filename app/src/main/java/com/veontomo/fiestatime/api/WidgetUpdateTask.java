package com.veontomo.fiestatime.api;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.fiestatime.presenters.WidgetPresenter;

import java.util.Calendar;
import java.util.Random;

/**
 * Updates the state of the widget in the asynchronous manner.
 */
public class WidgetUpdateTask extends AsyncTask<Void, Void, Void>{

    /**
     * The number of milliseconds in a day
     */
    private static final int MILLISEC_IN_DAY = 1000 * 60 * 60 * 24;
    /**
     * a mock for the forthcoming holiday names
     */
    private final String[] mockHolidays = new String[]{"New Year", "holiday", "Saturday", "Birthday"};

    private final WidgetPresenter provider;
    private final IProvider<Holiday> itemProvider;

    public WidgetUpdateTask(WidgetPresenter caller, IProvider<Holiday> itemProvider){
        this.provider = caller;
        this.itemProvider = itemProvider;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Holiday holiday = itemProvider.getNearest();
        if (holiday != null) {
            updateProvider(holiday);
        }
        return null;
    }

    /**
     * Updates provider data based on what holidays is coming.
     */
    private void updateProvider(@NonNull Holiday holiday) {
        int days = (int) (holiday.nextOccurrence - System.currentTimeMillis()) / MILLISEC_IN_DAY;
        provider.setNearest(days);
        provider.setNextNearest(days + 8);
        provider.setDescription(holiday.name);
    }

    @Override
    public void onPostExecute(Void v){
        this.provider.onUpdated();
    };
}
