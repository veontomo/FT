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
    private final IProvider<Holiday> itemProvider;

    public WidgetUpdateTask(WidgetPresenter caller, IProvider<Holiday> itemProvider){
        this.provider = caller;
        this.itemProvider = itemProvider;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Holiday[][] groups = itemProvider.getForthcomingGroups(2);
        updateProvider(groups);
        return null;
    }

    /**
     * Updates provider data based on what holidays are coming.
     */
    private void updateProvider(Holiday[][] groups) {
        // TODO: this is just a stub. Make a realistic implementation
        Random random = new Random();
        int nearest = random.nextInt(30);
        provider.setNearest(nearest);
        provider.setNextNearest(nearest + random.nextInt(30));
        provider.setDescription(mockHolidays[nearest % mockHolidays.length]);
    }

    @Override
    public void onPostExecute(Void v){
        this.provider.onUpdated();
    };
}
