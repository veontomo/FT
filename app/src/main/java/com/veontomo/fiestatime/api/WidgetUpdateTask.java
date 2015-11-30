package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.Logger;
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

    private final WidgetPresenter caller;

    public WidgetUpdateTask(WidgetPresenter caller){
        this.caller = caller;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Logger.log("widget update task is called");
        Random random = new Random();
        int nearest = random.nextInt(30);
        caller.setNearest(nearest);
        caller.setNextNearest(nearest + random.nextInt(30));
        caller.setText(mockHolidays[nearest % mockHolidays.length]);

        return null;
    }

    @Override
    public void onPostExecute(Void v){
        Logger.log("widget update task is over");
        this.caller.onUpdated();
    };
}
