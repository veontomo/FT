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
    Spinner spinner;
    TextView dateView;
    private Button confirmButton;
    private Button cancelButton;
    private final static String DATE_VIEW_ID_TOKEN = "textView";
    private final static String ONSCREEN_DATE_FORMAT = "d MMMM yyyy";
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
        dateView = (TextView) view.findViewById(R.id.frag_add_holiday_next);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(ONSCREEN_DATE_FORMAT);
        dateView.setText(format.format(calendar.getTime()));
        spinner = (Spinner) view.findViewById(R.id.frag_add_holiday_periodicity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.periodicity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        confirmButton = (Button) view.findViewById(R.id.frag_add_holiday_confirm);
        cancelButton = (Button) view.findViewById(R.id.frag_add_holiday_cancel);

        attachListeners();
    }

    @Override
    public void onDestroyView() {
        detachListeners();
        cancelButton = null;
        confirmButton = null;
        spinner.setAdapter(null);
        spinner = null;
        super.onDestroyView();

    }


    private void attachListeners() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHostActivity != null) {
                    mHostActivity.onConfirm();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHostActivity != null) {
                    mHostActivity.onCancel();
                }
            }
        });
        dateView.setOnClickListener(new View.OnClickListener() {
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
        dateView.setOnClickListener(null);
        cancelButton.setOnClickListener(null);
        confirmButton.setOnClickListener(null);
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
