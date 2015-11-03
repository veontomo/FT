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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActions} interface
 * to handle interaction events.
 * Use the {@link AddHoliday#newInstance} factory method to
 * create an instance of this fragment.
 */
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
                DialogFragment datePickerDialog = new DatePickerFragment();
                Bundle b = new Bundle();
                b.putInt(DATE_VIEW_ID_TOKEN, v.getId());
                datePickerDialog.setArguments(b);
                datePickerDialog.show(getActivity().getFragmentManager(), "datePicker");

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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private final static Calendar calendar = Calendar.getInstance();

        /**
         * Id of a text view that must display the date selected by the user
         */
        private int viewId;



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Bundle b = getArguments();
            if (b != null) {
                this.viewId = b.getInt(DATE_VIEW_ID_TOKEN, -1);
            }
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = (TextView) getActivity().findViewById(viewId);
            if (tv != null) {
                // use another copy of calendar since the first copy is used to set date picker
                // minimal date which must be today's date, not the last date that the user picks
                // by means of the dialog.
                final Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                // TODO
//                tv.setText(format.format(cal.getTime()));

            }
        }
    }

}
