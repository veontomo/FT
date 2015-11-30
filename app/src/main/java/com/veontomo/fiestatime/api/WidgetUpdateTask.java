package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.presenters.WidgetPresenter;

import java.util.Random;

/**
 * Updates the state of the widget in the asynchronous manner.
 */
public class WidgetUpdateTask extends AsyncTask<Void, Void, Void>{

    /**
     * a mock for the forthcoming holiday names
     */
    private final String[] mockHolidays = new String[]{"New Year", "holiday", "Saturday", "Birthday"};

    private final WidgetPresenter provider;

    public WidgetUpdateTask(WidgetPresenter caller){
        this.provider = caller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Random random = new Random();
        int nearest = random.nextInt(30);
        provider.setNearest(nearest);
        provider.setNextNearest(nearest + random.nextInt(30));
        provider.setDescription(mockHolidays[nearest % mockHolidays.length]);
        return null;
    }

    @Override
    public void onPostExecute(Void v){
        this.provider.onUpdated();
    };
}
