package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.presenters.MultiEventPresenter;

import java.util.List;

/**
 * Task that retrieves all mEvents from the storage
 */
public class RetrieveAllEventsTask implements ITask {

    private final IProvider provider;
    private MultiEventPresenter presenter;


    public RetrieveAllEventsTask(IProvider provider) {
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
    public void setOnDataLoaded(MultiEventPresenter presenter) {
        this.presenter = presenter;
    }


    private class HolidayLoader extends AsyncTask<Void, Void, List<Event>> {

        private final IProvider provider;
        private final MultiEventPresenter presenter;

        public HolidayLoader(IProvider provider, MultiEventPresenter presenter) {
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
