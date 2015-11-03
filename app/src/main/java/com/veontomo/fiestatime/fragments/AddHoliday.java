package com.veontomo.fiestatime.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.presenters.AddHolidayPresenter;
import com.veontomo.fiestatime.views.AddHolidayView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class AddHoliday extends Fragment implements AddHolidayView {

    private AddHolidayPresenter mPresenter = new AddHolidayPresenter(this);
    private EditText mHolidayNameView;
    private TextView mNextOccurrenceView;
    private Spinner mPeriodicityView;
    private Button mConfirmButton;
    private Button mCancelButton;

    private static final String DATE_VIEW_ID_TOKEN = "textView";


    public AddHoliday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHoliday.
     */
    public static AddHoliday newInstance(String param1, String param2) {
        AddHoliday fragment = new AddHoliday();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_holiday, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);


    }

    @Override
    public void onViewStateRestored(Bundle b) {
        super.onViewStateRestored(b);
//        if (b != null) {
//            this.mHolidayName = b.getString(HOLIDAY_NAME_TOKEN);
//            this.mNextOccurrence = b.getString(NEXT_OCCURRENCE_TOKEN);
//            this.mPeriodicity = b.getInt(PERIODICITY_TOKEN, -1);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHolidayNameView = (EditText) getActivity().findViewById(R.id.frag_add_holiday_name);
        mNextOccurrenceView = (TextView) getActivity().findViewById(R.id.frag_add_holiday_next);
        mNextOccurrenceView.setText("31 November 2015");
        mPeriodicityView = (Spinner) getActivity().findViewById(R.id.frag_add_holiday_periodicity);
        mConfirmButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_confirm);
        mCancelButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_cancel);

        attachListeners();
        fillInViews();

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
                onConfirm();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onCancel();
            }
        });
        mNextOccurrenceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClick(v);

            }
        });


    }

    private void detachListeners() {
        mNextOccurrenceView.setOnClickListener(null);
        mCancelButton.setOnClickListener(null);
        mConfirmButton.setOnClickListener(null);
    }


    @Override
    public void onSaveInstanceState(Bundle b) {
//        b.putString(HOLIDAY_NAME_TOKEN, this.mHolidayNameView.getEditableText().toString());
//        b.putString(NEXT_OCCURRENCE_TOKEN, this.mNextOccurrenceView.getText().toString());
//        b.putInt(PERIODICITY_TOKEN, this.mPeriodicityView.getSelectedItemPosition());
        super.onSaveInstanceState(b);

    }

    @Override
    public void fillInViews() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.periodicity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPeriodicityView.setAdapter(adapter);
    }

    @Override
    public void setName() {
        this.mHolidayNameView.setText(mPresenter.getHolidayName());
    }

    @Override
    public void setNextOccurrence() {
        this.mNextOccurrenceView.setText(mPresenter.getNextOccurrence());
    }

    @Override
    public void setPeriodicity() {
        this.mPeriodicityView.setSelection(mPresenter.getPeriodicity());
    }


    @Override
    public void onConfirm() {
        mPresenter.onConfirm(mHolidayNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
    }

    @Override
    public void onCancel() {
        mPresenter.onCancel(mHolidayNameView.getEditableText().toString(), mNextOccurrenceView.getText().toString(), mPeriodicityView.getSelectedItemPosition());
    }

    @Override
    public void onSetDate() {

    }

    @Override
    public void onDateClick(View v) {
        mPresenter.onDateClick(getActivity().getFragmentManager());
    }


    @Override
    public void setDate(String date) {
        this.mNextOccurrenceView.setText(date);
    }
}
