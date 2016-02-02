package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.DetailedAdapter;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.RetrieveAllEventsTask;
import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.MultiEventPresenter;
import com.veontomo.fiestatime.views.MultiEventView;

/**
 * Displays all available fragments
 */
public class MultiEvents extends Fragment implements MultiEventView {

    private final MultiEventPresenter mPresenter = new MultiEventPresenter(this);

    private DetailedAdapter<Event> mAdapter;

    private onActions hostingActivity;

    private ListView mEventList;

    public MultiEvents() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.log("MultiEvents onActivityCreated");

        Storage storage = new Storage(getActivity().getApplicationContext());
        EventDBProvider provider = new EventDBProvider(storage);
        RetrieveAllEventsTask task = new RetrieveAllEventsTask(provider);
        mPresenter.setTask(task);
        hostingActivity = (onActions) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log("MultiEvents onCreateView");
        restoreState(savedInstanceState);
        return inflater.inflate(R.layout.fragment_all_events, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mEventList = (ListView) getActivity().findViewById(R.id.frag_all_event_list);
        this.mAdapter = new DetailedAdapter<>();
        this.mEventList.setAdapter(this.mAdapter);

        mPresenter.bindView(this);

    }


    @Override
    public void updateViews() {
        mAdapter.notifyDataSetChanged();
        Logger.log("MultiEvents updateView is done");
    }

    @Override
    public void saveState(Bundle b) {
        Logger.log("MultiEvents saveState");
        mPresenter.saveState(b);
    }

    /**
     * Restores the state of the view from the bundle
     *
     * @param b
     */
    @Override
    public void restoreState(Bundle b) {
        Logger.log("MultiEvents restoreState");
        mPresenter.restoreState(b);
    }

    /**
     * Displays a short message
     *
     * @param msg
     */
    @Override
    public void showMessage(int msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        Logger.log("MultiEvents onSaveInstanceState");
        saveState(b);
        super.onSaveInstanceState(b);
    }

    @Override
    public void addEvent(Event h) {
        Logger.log("MultiEvents addEvent");
        mPresenter.addEvent(h);
    }

    /**
     * Eliminates a holiday at given position
     *
     * @param pos
     */
    @Override
    public void deleteEvent(int pos) {
        // TODO
    }

    public void onEventClick(Event event) {
        if (hostingActivity != null) {
            hostingActivity.onEventClicked(event);
        }
    }

    /**
     * Updates an event
     *
     * @param h
     */
    @Override
    public void updateEvent(Event h) {
        mPresenter.updateEvent(h);
    }


    public interface onActions {
        void onEventClicked(Event h);
    }
}
