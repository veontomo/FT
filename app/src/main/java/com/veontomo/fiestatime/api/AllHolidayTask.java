package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;

import java.util.List;

/**
 * Task that retrieves all holidays from the storage
 */
public class AllHolidayTask implements ITask {

    private final IHolidayProvider provider;
    private MultiHolidaysPresenter presenter;


    public AllHolidayTask(IHolidayProvider provider) {
        this.provider = provider;
    }

    @Override
    public void execute() {
        HolidayLoader worker = new HolidayLoader(provider, presenter);
        worker.execute();
    }

    /**
     * Sets a instance that will be called once the presenter receives the data.
     *
     * @param presenter
     */
    public void setOnDataLoaded(MultiHolidaysPresenter presenter) {
        this.presenter = presenter;
    }


    private class HolidayLoader extends AsyncTask<Void, Void, List<Holiday>> {

        private final IHolidayProvider provider;
        private final MultiHolidaysPresenter presenter;

        public HolidayLoader(IHolidayProvider provider, MultiHolidaysPresenter presenter) {
            this.provider = provider;
            this.presenter = presenter;
        }

        @Override
        protected List<Holiday> doInBackground(Void... params) {
            return provider.getHolidays();
        }

        @Override
        public void onPostExecute(List<Holiday> holidays) {
            presenter.load(holidays);
        }
    }

}
