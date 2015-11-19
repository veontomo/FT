package com.veontomo.fiestatime;

import android.os.AsyncTask;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.IHolidayProvider;
import com.veontomo.fiestatime.api.Storage;

import java.util.List;

/**
 * Async loader for forthcoming events
 */
public class ForthcomingHolidayLoader extends AsyncTask<Void, Void, List<Holiday>> {

    private final IHolidayProvider provider;
    private final Storage storage;

    public ForthcomingHolidayLoader(Storage storage, IHolidayProvider provider) {
        this.provider = provider;
        this.storage = storage;
    }

    @Override
    protected List<Holiday> doInBackground(Void... params) {
        return storage.getHolidays();
    }

    @Override
    public void onPostExecute(List<Holiday> holidays){
        provider.onLoad(holidays);
    }
}