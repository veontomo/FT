package com.veontomo.fiestatime.presenters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.DatePicker;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.views.AddHolidayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding holidays
 */
public class AddHolidayPresenter implements MVPPresenter {
    private static final String HOLIDAY_NAME_TOKEN = "holidayName";
    private static final String NEXT_OCCURRENCE_TOKEN = "nextOccurrence";
    private static final String PERIODICITY_TOKEN = "periodicity";
    private static final SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
    private final AddHolidayView view;
    private String mHolidayName;
    private String mNextOccurrence;
    private int mPeriodicity = -1;


    public AddHolidayPresenter(AddHolidayView view) {
        this.view = view;

    }

    @Override
    public void onStart() {


    }

    public void onDateChosen(String date) {
        view.setDate(date);
        Logger.log("setting date " + date);
    }

    /**
     * This method is called when a user clicks the "confirm" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
    @Override
    public void onConfirm(String name, String next, int pos) {
        /// TODO
    }

    /**
     * This method is called when a user clicks the "cancel" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
    @Override
    public void onCancel(String name, String next, int pos) {
        /// TODO
    }

    public String getHolidayName() {
        return "Dumb text";
    }

    public String getNextOccurrence() {
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public int getPeriodicity() {
        return Holiday.WEEKLY;
    }

    public void onDateClick(FragmentManager fm) {
        DialogFragment datePickerDialog = new DatePickerFragment();
        DatePickerFragment.target = this;
        datePickerDialog.show(fm, "datePicker");

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public static AddHolidayPresenter target;
        private final Calendar calendar = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // use another copy of calendar since the first copy is used to set date picker
            // minimal date which must be today's date, not the last date that the user picks
            // by means of the dialog.
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            target.onDateChosen(format.format(cal.getTime()));

        }
    }


}
