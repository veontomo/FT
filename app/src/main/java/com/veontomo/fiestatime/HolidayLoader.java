package com.veontomo.fiestatime;

import android.os.AsyncTask;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import java.util.List;

/**
 * Asynchronous task for Loading the holidays.
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
        return storage.getHolidays();
    }

    @Override
    public void onPostExecute(List<Holiday> holidays){
        presenter.load(holidays);
    }
}
