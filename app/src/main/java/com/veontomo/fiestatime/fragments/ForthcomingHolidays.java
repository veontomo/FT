package com.veontomo.fiestatime.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.presenters.ForthcomingHolidaysPresenter;
import com.veontomo.fiestatime.views.MVPView;


/**
 */
public class ForthcomingHolidays extends Fragment implements MVPView {

    private static final String NAME_TOKEN = "holidayName";
    private OnFragmentInteractionListener mListener;

    private final ForthcomingHolidaysPresenter mPresenter = new ForthcomingHolidaysPresenter(this);

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
    public void initializeViews() {
        mTextView.setText("XXXXXXXXXXXXXX");
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        mPresenter.onSaveState(b);
        super.onSaveInstanceState(b);
    }
    @Override
    public void onSaveState(Bundle b) {
        b.putString(NAME_TOKEN, mTextView.getText().toString());

    }

    public interface OnFragmentInteractionListener {
    }

}
