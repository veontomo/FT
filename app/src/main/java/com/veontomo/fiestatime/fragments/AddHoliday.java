package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.AddHolidayPresenter;
import com.veontomo.fiestatime.views.AddHolidayView;

import java.util.ArrayList;


public class AddHoliday extends Fragment implements AddHolidayView {
    /**
     * name of the token under which holiday's name is saved into the bundle
     */
    private final String NAME_TOKEN = "name";
    /**
     * name of the token under which holiday's next occurrence is saved into the bundle
     */
    private final String DATE_TOKEN = "date";
    /**
     * name of the token under which holiday's periodicity is saved into the bundle
     */
    private final String PERIODICITY_TOKEN = "periodicity";

    private final AddHolidayPresenter mPresenter = new AddHolidayPresenter(this);
    private EditText mHolidayNameView;
    private TextView mNextOccurrenceView;
    private Spinner mPeriodicityView;
    private Button mConfirmButton;
    private Button mCancelButton;
    private onActions hostingActivity;

    public AddHoliday() {
        // Required empty public constructor
    }

//    @Override
//    public void onViewStateRestored(Bundle b){
//        super.onViewStateRestored(b);
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter.onRestoreState(savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_holiday, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        mHolidayNameView = (EditText) getActivity().findViewById(R.id.frag_add_holiday_name);
        mNextOccurrenceView = (TextView) getActivity().findViewById(R.id.frag_add_holiday_next);
        mPeriodicityView = (Spinner) getActivity().findViewById(R.id.frag_add_holiday_periodicity);
        mConfirmButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_confirm);
        mCancelButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_cancel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.periodicity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPeriodicityView.setAdapter(adapter);

        mPresenter.bindView(this);

        attachListeners();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hostingActivity = (onActions) getActivity();
        Storage storage = new Storage(getActivity().getApplicationContext());
        mPresenter.setHolidayProvider(new HolidayDBProvider(storage));
    }

    @Override
    public void onDestroyView() {
        detachListeners();
        mCancelButton = null;
        mConfirmButton = null;
        mPeriodicityView.setAdapter(null);
        mPeriodicityView = null;
        mHolidayNameView = null;
        super.onDestroyView();

    }


    private void attachListeners() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onConfirm(mHolidayNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
                mHolidayNameView.setText(null);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCancel(mHolidayNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
                mHolidayNameView.setText(null);
            }
        });
        mNextOccurrenceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClick(v);

            }
        });
    }


    @Override
    public void onPause() {
        mPresenter.onPause(mHolidayNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
        super.onPause();

    }

    private void detachListeners() {
        mNextOccurrenceView.setOnClickListener(null);
        mCancelButton.setOnClickListener(null);
        mConfirmButton.setOnClickListener(null);
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
        mPresenter.onSaveState(b);
        super.onSaveInstanceState(b);
    }


    @Override
    public void initializeName() {
        Logger.log("view: initialize name: " + mPresenter.getHolidayName());
        this.mHolidayNameView.setText(mPresenter.getHolidayName());
    }

    @Override
    public void initializeNextOccurrence() {
        this.mNextOccurrenceView.setText(mPresenter.getNextOccurrence());
    }

    @Override
    public void initializePeriodicity() {
        this.mPeriodicityView.setSelection(mPresenter.getPeriodicity());
    }

    @Override
    public void onDateClick(View v) {
        mPresenter.onDateClick(v, getActivity().getFragmentManager());
    }

    @Override
    public void setDate(String date) {
        this.mNextOccurrenceView.setText(date);
    }

    @Override
    public String restoreName(@NonNull Bundle b) {
        return b.getString(NAME_TOKEN);
    }

    @Override
    public String restoreDate(Bundle b) {
        return b.getString(DATE_TOKEN);
    }

    @Override
    public int restorePeriodicity(Bundle b) {
        return b.getInt(PERIODICITY_TOKEN);
    }

    @Override
    public void onHolidayAdded(Holiday h) {
        Logger.log("holiday " + h.name + ", next: " + h.nextOccurrence + ", periodicity: " + h.periodicity +  " is added " );
        hostingActivity.onHolidayAdded(h);
    }

    @Override
    public void load(Holiday h) {
        this.mPresenter.load(h);
        initializeViews();
    }

    @Override
    public void initializeViews() {
        initializeName();
        initializePeriodicity();
        initializeNextOccurrence();

    }

    @Override
    public void onSaveState(Bundle b) {
        b.putString(NAME_TOKEN, mHolidayNameView.getEditableText().toString());
        b.putString(DATE_TOKEN, mNextOccurrenceView.getText().toString());
        b.putInt(PERIODICITY_TOKEN, mPeriodicityView.getSelectedItemPosition());
        Logger.log("saved: " + mHolidayNameView.getEditableText().toString() + ", " + mNextOccurrenceView.getText().toString() + ", " + mPeriodicityView.getSelectedItemPosition());
    }

    public interface onActions {
        void onHolidayAdded(Holiday h);
    }
}
