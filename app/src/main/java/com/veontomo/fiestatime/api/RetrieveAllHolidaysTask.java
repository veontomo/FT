package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;

import java.util.List;

/**
 * Task that retrieves all mEvents from the storage
 */
public class RetrieveAllHolidaysTask implements ITask {

    private final IProvider provider;
    private MultiHolidaysPresenter presenter;


    public RetrieveAllHolidaysTask(IProvider provider) {
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


    private class HolidayLoader extends AsyncTask<Void, Void, List<Event>> {

        private final IProvider provider;
        private final MultiHolidaysPresenter presenter;

        public HolidayLoader(IProvider provider, MultiHolidaysPresenter presenter) {
            this.provider = provider;
            this.presenter = presenter;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            return provider.getItems();
        }

        @Override
        public void onPostExecute(List<Event> events) {
            presenter.load(events);
        }
    }

}
