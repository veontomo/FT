package com.veontomo.fiestatime.presenters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
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
    private static final SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
    private final AddHolidayView view;

    /**
     * holiday name
     */
    private String name;
    /**
     * Nearest holiday occurrence
     */
    private String date;
    /**
     * Holiday periodicity
     */
    private int periodicity;

    public AddHolidayPresenter(AddHolidayView view) {
        Logger.log("creating new presenter");
        this.view = view;

    }

    @Override
    public void onStart() {


    }

    /**
     * Save the current values inserted by user.
     * @param name
     * @param date
     * @param pos
     */
    public void onPause(String name, String date, int pos) {
        this.name = name;
        this.date = date;
        this.periodicity = pos;
    }

    public void onDateChosen(String date) {
        this.date = date;
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

    public void onDateClick(View v, FragmentManager fm) {
        DialogFragment datePickerDialog = new DatePickerFragment();
        DatePickerFragment.presenter = this;
        DatePickerFragment.boundView = v;
        Logger.log("previously selected date " + this.date);
        datePickerDialog.show(fm, "datePicker");

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        /**
         * to whom the result of the date picker should be given
         */
        public static AddHolidayPresenter presenter;
        /**
         * a view to which current date picker is bound
         */
        public static View boundView;
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
            presenter.onDateChosen(format.format(cal.getTime()));

        }
    }


}
