package com.veontomo.fiestatime.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.HolidayDBProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.presenters.AddHolidayPresenter;
import com.veontomo.fiestatime.views.AddHolidayView;


public class AddHoliday extends Fragment implements AddHolidayView {
    private final AddHolidayPresenter mPresenter = new AddHolidayPresenter(this);
    private EditText mHolidayNameView;
    private TextView mNextOccurrenceView;
    private Spinner mPeriodicityView;
    private Button mConfirmButton;
    private Button mCancelButton;
    private onActions hostingActivity;

    public AddHoliday() { }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restoreState(savedInstanceState);
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

        // TODO: make the presenter use a task in order to load holiday info (if any) into
        // the edit view. See how it is done in {@link AllHolidays#onActivityCreated}

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
        saveState(b);
        super.onSaveInstanceState(b);
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
    public void onHolidayAdded(Holiday h) {
        hostingActivity.onHolidayAdded(h);
        mHolidayNameView.setText(null);
    }

    @Override
    public void onHolidayUpdated(Holiday h) {
        hostingActivity.onHolidayUpdated(h);
    }

    @Override
    public void load(Holiday h) {
        this.mPresenter.load(h);
        updateViews();
    }

    /**
     * Disables "confirm" and "cancel" buttons
     *
     * @param status true to enable buttons, false to disable them
     */
    @Override
    public void setEnableButtons(boolean status) {
        mConfirmButton.setClickable(status);
        mCancelButton.setClickable(status);
    }

    @Override
    public void updateViews() {
        this.mHolidayNameView.setText(mPresenter.getHolidayName());
        this.mNextOccurrenceView.setText(mPresenter.getNextOccurrence());
        this.mPeriodicityView.setSelection(mPresenter.getPeriodicity());
    }


    @Override
    public void saveState(Bundle b) {
        mPresenter.saveState(b);
    }

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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public interface onActions {
        void onHolidayAdded(Holiday h);
        void onHolidayUpdated(Holiday h);
    }
}
