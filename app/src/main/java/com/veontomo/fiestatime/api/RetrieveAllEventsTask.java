package com.veontomo.fiestatime.api;

import android.os.AsyncTask;

import com.veontomo.fiestatime.presenters.MultiEventPresenter;

import java.util.List;

/**
 * Task that retrieves all mEvents from the storage
 */
public class RetrieveAllEventsTask extends AsyncTask<Void, Void, Void> {

    private final IProvider mProvider;
    private final MultiEventPresenter mPresenter;


    public RetrieveAllEventsTask(IProvider provider, MultiEventPresenter presenter) {
        this.mProvider = provider;
        this.mPresenter = presenter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<Event> events = this.mProvider.getItems();
        this.mPresenter.load(events);
        return null;
    }

    @Override
    public void onPostExecute(Void v) {
        this.mPresenter.onLoaded();
    }

}
