package com.veontomo.fiestatime.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.views.AllHolidaysView;

import java.util.ArrayList;

/**
 * Displays all available fragments
 */
public class AllHolidays extends ListFragment implements AllHolidaysView {

    private final AllHolidaysPresenter mPresenter = new AllHolidaysPresenter(this);

    private ArrayAdapter<Holiday> adapter;

    private onActions hostingActivity;

    public AllHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Holiday> values = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        Storage storage = new Storage(getActivity().getApplicationContext());
        mPresenter.setHolidayProvider(new HolidayDBProvider(storage) );
        hostingActivity = (onActions) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter.onRestoreState(savedInstanceState);
        return inflater.inflate(R.layout.fragment_all_holidays, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void onLoadFields() {
        adapter.clear();
        adapter.addAll(mPresenter.getHolidays());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveState(Bundle b) {

    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        mPresenter.onSaveState(b);
        super.onSaveInstanceState(b);
    }

    @Override
    public void addHoliday(Holiday h) {
        mPresenter.addHoliday(h);
    }

    public void onHolidayClick(Holiday holiday) {
        if (hostingActivity != null){
            hostingActivity.onHolidayClicked(holiday);
        }
    }


    public interface onActions {
        void onHolidayClicked(Holiday h);
    }
}
