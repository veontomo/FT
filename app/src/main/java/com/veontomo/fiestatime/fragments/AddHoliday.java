package com.veontomo.fiestatime.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentActions} interface
 * to handle interaction events.
 * Use the {@link AddHoliday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHoliday extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String HOLIDAY_NAME_TOKEN = "holidayName";
    private static final String NEXT_OCCURRENCE_TOKEN = "nextOccurrence";
    private static final String PERIODICITY_TOKEN = "periodicity";
    private final static String DATE_VIEW_ID_TOKEN = "textView";
    private final static String ONSCREEN_DATE_FORMAT = "d MMMM yyyy";
    private EditText mHolidayNameView;
    private TextView mNextOccurrenceView;
    private Spinner mPeriodicityView;
    private Button mConfirmButton;
    private Button mCancelButton;
    private String mHolidayName;
    private String mNextOccurrence;
    private int mPeriodicity = -1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentActions mHostActivity;

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
    // TODO: Rename and change types and number of parameters
    public static AddHoliday newInstance(String param1, String param2) {
        AddHoliday fragment = new AddHoliday();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        try {
            mHostActivity = (OnFragmentActions) getActivity();
        } catch (ClassCastException e) {
            Logger.log("hosting activity does not support fragment's interface");
            mHostActivity = null;
        }
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
        if (b != null) {
            this.mHolidayName = b.getString(HOLIDAY_NAME_TOKEN);
            this.mNextOccurrence = b.getString(NEXT_OCCURRENCE_TOKEN);
            this.mPeriodicity = b.getInt(PERIODICITY_TOKEN, -1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mHolidayNameView = (EditText) getActivity().findViewById(R.id.frag_add_holiday_name);
        mNextOccurrenceView = (TextView) getActivity().findViewById(R.id.frag_add_holiday_next);
        mPeriodicityView = (Spinner) getActivity().findViewById(R.id.frag_add_holiday_periodicity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.periodicity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPeriodicityView.setAdapter(adapter);

        mConfirmButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_confirm);
        mCancelButton = (Button) getActivity().findViewById(R.id.frag_add_holiday_cancel);

        attachListeners();
        if (this.mHolidayName != null) {
            this.mHolidayNameView.setText(this.mHolidayName);
        }
        if (this.mNextOccurrence == null) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(ONSCREEN_DATE_FORMAT);
            this.mNextOccurrence = format.format(calendar.getTime());
        }
        this.mNextOccurrenceView.setText(this.mNextOccurrence);
        if (this.mPeriodicity != -1) {
            this.mPeriodicityView.setSelection(this.mPeriodicity);

        }

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
                if (mHostActivity != null) {
                    mHostActivity.onConfirm();
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHostActivity != null) {
                    mHostActivity.onCancel();
                }
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mHostActivity != null) {
            mHostActivity.onConfirm();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mHostActivity = null;
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        b.putString(HOLIDAY_NAME_TOKEN, this.mHolidayNameView.getEditableText().toString());
        b.putString(NEXT_OCCURRENCE_TOKEN, this.mNextOccurrenceView.getText().toString());
        b.putInt(PERIODICITY_TOKEN, this.mPeriodicityView.getSelectedItemPosition());
        super.onSaveInstanceState(b);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     */
    public interface OnFragmentActions {
        void onConfirm();

        void onCancel();

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
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                SimpleDateFormat format = new SimpleDateFormat(ONSCREEN_DATE_FORMAT);
                tv.setText(format.format(calendar.getTime()));

            }
        }
    }

}
