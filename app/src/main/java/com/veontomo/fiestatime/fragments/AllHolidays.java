package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onActions} interface
 * to handle interaction events.
 */
public class AllHolidays extends ListFragment implements Loadable<List<Holiday>> {


    private onActions mHostActivity;

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
        try {
            mHostActivity = (onActions) getActivity();
        } catch (ClassCastException e) {
            Logger.log("AllHolidays is embedded to an activity that does not support interaction");
            mHostActivity = null;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mHostActivity != null){
            mHostActivity.onItemClick(position);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHostActivity = null;
    }


    @Override
    public void load(@NonNull List<Holiday> holidays) {
        for (Holiday holiday : holidays){
            adapter.add(holiday.name);
        }
        adapter.notifyDataSetChanged();


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onActions {
        void onItemClick(int pos);
    }

}