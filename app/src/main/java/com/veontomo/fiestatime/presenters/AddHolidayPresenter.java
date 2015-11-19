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
import com.veontomo.fiestatime.api.IHolidayProvider;
import com.veontomo.fiestatime.views.AddHolidayView;
import com.veontomo.fiestatime.views.MVPView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding holidays
 */
public class AddHolidayPresenter implements MVPPresenter {
    private static final SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
    /**
     * name of the token under which the presenter saves the holiday's name in the bundle
     */
    private static final String NAME_TOKEN = "name";

    /**
     * name of the token under which the presenter saves the holiday's date in the bundle
     */
    private static final String DATE_TOKEN = "date";
    /**
     * name of the token under which the presenter saves the holiday's periodicity in the bundle
     */
    private static final String PERIODICITY_TOKEN = "periodicity";


    private final AddHolidayView view;

    /**
     * Date that corresponds to the holiday's next occurrence
     */
    private String date;

    /**
     * Holiday name
     */
    private String name;

    /**
     * Holiday periodicity
     */
    private int periodicity;

    /**
     * Holiday id.
     */
    private long id;

    private IHolidayProvider holidayProvider;

    public AddHolidayPresenter(AddHolidayView view) {
        this.view = view;
        if (this.date == null){
            Calendar calendar = Calendar.getInstance();
            this.date = format.format(calendar.getTime());
        }
    }

    @Override
    public void onStart() {


    }

    @Override
    public void bindView(MVPView v) {
        v.updateViews();
    }

    /**
     * Save the current values inserted by user.
     *
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
    }

    /**
     * This method is called when a user clicks the "confirm" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
    @Override
    public void onConfirm(final String name, final String next, final int pos) {
        (new Thread(new Runnable(){
            @Override
            public void run() {
                if (name == null || next == null ){
                    view.showMessage("Both name and date must be set!");
                } else if (holidayProvider != null){
                    try {
                        long nextOccurrence = format.parse(next).getTime();
                        Holiday h = new Holiday(name, nextOccurrence, pos);
                        long id = holidayProvider.save(h);
                        if (id != -1){
                            h = new Holiday(id, name, nextOccurrence, pos);
                            view.onHolidayAdded(h);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Logger.log("Failed to parse next occurrence: " + next);
                    }
                }
            }
        })).run();
    }

    /**
     * Set a provider of the holidays
     */
    public void setHolidayProvider(IHolidayProvider hp) {
        this.holidayProvider = hp;
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

    @Override
    public void saveState(Bundle b) {
        // TODO: save the other fields as well
        b.putString(NAME_TOKEN, this.name);
        b.putString(DATE_TOKEN, this.date);
        b.putInt(PERIODICITY_TOKEN, this.periodicity);
    }

    @Override
    public void restoreState(Bundle b) {
        if (b != null) {
            this.name = b.getString(NAME_TOKEN);
            this.date = b.getString(DATE_TOKEN);
            this.periodicity = b.getInt(PERIODICITY_TOKEN);
        }

    }

    public String toString(){
        return this.name + " " + this.date + " " + this.periodicity;
    }

    public String getHolidayName() {
        return this.name;
    }

    public String getNextOccurrence() {
        return this.date;
    }

    public int getPeriodicity() {
        return this.periodicity;
    }

    public void onDateClick(View v, FragmentManager fm) {
        DialogFragment datePickerDialog = new DatePickerFragment();
        DatePickerFragment.presenter = this;
        DatePickerFragment.boundView = v;
        Logger.log("previously selected date " + this.date);
        datePickerDialog.show(fm, "datePicker");

    }

    public void load(Holiday h) {
        this.name = h.name;
        this.date = format.format(h.nextOccurrence);
        this.periodicity = h.periodicity;
        this.id = h.id;
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
