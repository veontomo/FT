package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.presenters.MultiEventPresenter;
import com.veontomo.fiestatime.views.MultiEventView;


/**
 * This class is supposed to show forthcoming events.
 * But it seems that it is better to use {@link MultiEvents} class.
 */
public class ForthcomingEvents extends Fragment implements MultiEventView {

    private OnFragmentInteractionListener mListener;

    private final MultiEventPresenter mPresenter = new MultiEventPresenter(this, getActivity().getApplicationContext());

    private TextView mTextView;

    private ListView mEventList;

    public ForthcomingEvents() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Logger.log(this.getClass().getSimpleName() + ", method onCreate() is called with arguments");
        }
    }

    @Override
    public void setAdapter(BaseAdapter adapter){
        this.mEventList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forthcoming_events, container, false);
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
        mTextView.setText("see ForthcomingEvents class");
    }

    /**
     * Saves the state of the mView in the bundle.
     *
     * @param b
     */
    @Override
    public void saveState(Bundle b) {
        mPresenter.saveState(b);
    }

    /**
     * Restores the state of the mView from the bundle
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
    public void showMessage(int msg) {
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
    public void addEvent(Event h) {
        // TODO
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

    /**
     * This method is called when a click on a given event occurs
     *
     * @param event
     */
    @Override
    public void onEventClick(Event event) {

    }

    /**
     * Updates a holiday
     *
     * @param h
     */
    @Override
    public void updateEvent(Event h) {
        mPresenter.updateEvent(h);
    }


    public interface OnFragmentInteractionListener {
    }

}
