package com.veontomo.fiestatime.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayDBProvider;
import com.veontomo.fiestatime.api.RetrieveNearestHolidaysTask;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.MultiHolidaysPresenter;
import com.veontomo.fiestatime.views.MultiHolidaysView;

import java.util.ArrayList;


/**
 */
public class ForthcomingHolidays extends ListFragment implements MultiHolidaysView {

    private OnFragmentInteractionListener mListener;

    private final MultiHolidaysPresenter mPresenter = new MultiHolidaysPresenter(this);

    private TextView mTextView;

    public ForthcomingHolidays() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forthcoming_holidays, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.log("Forthcoming onActivityCreated");
        ArrayList<Holiday> values = new ArrayList<>();
        ArrayAdapter<Holiday> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        Storage storage = new Storage(getActivity().getApplicationContext());
        HolidayDBProvider provider = new HolidayDBProvider(storage);
        RetrieveNearestHolidaysTask task = new RetrieveNearestHolidaysTask(provider);
        mPresenter.setTask(task);
        hostingActivity = (onActions) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        mTextView = (TextView) getActivity().findViewById(R.id.frag_forth_holiday_name);
        mPresenter.bindView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateViews() {
        // TODO
        mTextView.setText("see ForthcomingHolidays class");
    }

    /**
     * Saves the state of the view in the bundle.
     *
     * @param b
     */
    @Override
    public void saveState(Bundle b) {
        mPresenter.saveState(b);
    }

    /**
     * Restores the state of the view from the bundle
     *
     * @param b
     */
    @Override
    public void restoreState(Bundle b) {
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
        mPresenter.saveState(b);
        super.onSaveInstanceState(b);
    }

    /**
     * Adds a holiday
     *
     * @param h
     */
    @Override
    public void addHoliday(Holiday h) {
        // TODO
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

    /**
     * This method is called when a click on a given holiday occurs
     *
     * @param holiday
     */
    @Override
    public void onHolidayClick(Holiday holiday) {

    }


    public interface OnFragmentInteractionListener {
    }

}
