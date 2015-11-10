package com.veontomo.fiestatime;

import android.os.AsyncTask;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.fragments.Loadable;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads holidays
 *
 */
public class HolidayLoader extends AsyncTask<Void, Void, List<Holiday>>{

    private final AllHolidaysPresenter presenter;
    private final Storage storage;

    public HolidayLoader(Storage storage, AllHolidaysPresenter presenter) {
        this.presenter = presenter;
        this.storage = storage;
    }

    @Override
    protected List<Holiday> doInBackground(Void... params) {
//        List<Holiday> list = new ArrayList<>();
//        list.add(new Holiday("New Year", System.currentTimeMillis(), Holiday.YEAR));
//        list.add(new Holiday("Week-end", System.currentTimeMillis(), Holiday.WEEKLY));
        return storage.getHolidays();
    }

    @Override
    public void onPostExecute(List<Holiday> holidays){
        presenter.load(holidays);
    }
}
