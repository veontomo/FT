package com.veontomo.fiestatime;

import android.os.AsyncTask;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.fragments.Loadable;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads holidays
 *
 */
public class HolidayLoader extends AsyncTask<Void, Void, List<Holiday>>{

    private final Loadable<List<Holiday>> target;
    private final Storage storage;

    public HolidayLoader(Loadable<List<Holiday>> target, Storage storage) {
        this.target = target;
        this.storage = storage;
    }

    @Override
    protected List<Holiday> doInBackground(Void... params) {
//        List<Holiday> list = new ArrayList<>();
//        list.add(new Holiday("New Year", System.currentTimeMillis(), Holiday.YEAR));
//        list.add(new Holiday("Week-end", System.currentTimeMillis(), Holiday.WEEK));
        return storage.getHolidays();
    }

    @Override
    public void onPostExecute(List<Holiday> holidays){
        target.load(holidays);
    }
}
