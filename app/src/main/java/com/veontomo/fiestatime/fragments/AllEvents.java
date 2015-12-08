package com.veontomo.fiestatime.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.RetrieveAllHolidaysTask;
import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;
import com.veontomo.fiestatime.views.MultiHolidaysView;

import java.util.ArrayList;

/**
 * Displays all available fragments
 */
public class AllEvents extends ListFragment implements MultiHolidaysView {

    private final MultiHolidaysPresenter mPresenter = new MultiHolidaysPresenter(this);

    private ArrayAdapter<Event> adapter;

    private onActions hostingActivity;

    public AllEvents() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.log("AllEvents onActivityCreated");
        ArrayList<Event> values = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        Storage storage = new Storage(getActivity().getApplicationContext());
        EventDBProvider provider = new EventDBProvider(storage);
        RetrieveAllHolidaysTask task = new RetrieveAllHolidaysTask(provider);
        mPresenter.setTask(task);
        hostingActivity = (onActions) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log("AllEvents onCreateView");
        restoreState(savedInstanceState);
        return inflater.inflate(R.layout.fragment_all_events, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.log("AllEvents onStart");
        mPresenter.bindView(this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mPresenter.onItemClick(position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void updateViews() {
        Logger.log("AllEvents updateView");
        adapter.clear();
        adapter.addAll(mPresenter.getEvents());
        adapter.notifyDataSetChanged();
        Logger.log("AllEvents updateView is done");
    }

    @Override
    public void saveState(Bundle b) {
        Logger.log("AllEvents saveState");
        mPresenter.saveState(b);
    }

    /**
     * Restores the state of the view from the bundle
     *
     * @param b
     */
    @Override
    public void restoreState(Bundle b) {
        Logger.log("AllEvents restoreState");
        mPresenter.restoreState(b);
    }

    /**
     * Displays a short message
     *
     * @param msg
     */
    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        Logger.log("AllEvents onSaveInstanceState");
        saveState(b);
        super.onSaveInstanceState(b);
    }

    @Override
    public void addHoliday(Event h) {
        Logger.log("AllEvents addHoliday");
        mPresenter.addHoliday(h);
    }

    /**
     * Eliminates a holiday at given position
     *
     * @param pos
     */
    @Override
    public void deleteHoliday(int pos) {
        // TODO
    }

    public void onHolidayClick(Event event) {
        if (hostingActivity != null) {
            hostingActivity.onHolidayClicked(event);
        }
    }

    /**
     * Updates a holiday
     *
     * @param h
     */
    @Override
    public void updateHoliday(Event h) {
        mPresenter.updateHoliday(h);
    }


    public interface onActions {
        void onHolidayClicked(Event h);
    }
}
