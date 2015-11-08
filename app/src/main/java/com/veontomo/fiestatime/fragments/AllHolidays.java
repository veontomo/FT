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
import com.veontomo.fiestatime.presenters.AllHolidaysPresenter;
import com.veontomo.fiestatime.presenters.MVPPresenter;
import com.veontomo.fiestatime.views.MVPView;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays all available fragments
 */
public class AllHolidays extends ListFragment implements Loadable<List<Holiday>>, MVPView {

    private MVPPresenter mPresenter = new AllHolidaysPresenter(this);

    private ArrayAdapter<String> adapter;

    public AllHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> values = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_holidays, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.bindView(this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void load(List<Holiday> holidays) {
        for (Holiday holiday : holidays){
            adapter.add(holiday.name);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void onSaveState(Bundle b) {

    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        mPresenter.onSaveState(b);
        super.onSaveInstanceState(b);
    }


}
