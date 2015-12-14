package com.veontomo.fiestatime.presenters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.Factory;
import com.veontomo.fiestatime.api.IProvider;
import com.veontomo.fiestatime.views.AddHolidayView;
import com.veontomo.fiestatime.views.MVPView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding mEvents
 */
public class AddHolidayPresenter implements MVPPresenter {
    private final static String[] classes = new String[]{"com.veontomo.fiestatime.api.SingleEvent",
            "com.veontomo.fiestatime.api.WeekEvent",
            "com.veontomo.fiestatime.api.MonthEvent",
            "com.veontomo.fiestatime.api.YearEvent"};

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
     * Event name
     */
    private String name;

    /**
     * Event periodicity
     */
    private int periodicity;

    /**
     * Event id.
     */
    private long id;

    private IProvider<Event> holidayProvider;

    public AddHolidayPresenter(AddHolidayView view) {
        this.view = view;
        if (this.date == null) {
            Calendar calendar = Calendar.getInstance();
            this.date = format.format(calendar.getTime());
        }
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
    public void onConfirm(final String name, final String next, final int pos) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                view.setEnableButtons(false);
                String msg = null;
                // elaborate three similar scenarios
                if (name == null || name.isEmpty()) {
                    msg = "Give a name to the holiday!";
                } else if (next == null || next.isEmpty()) {
                    msg = "Choose the holiday next occurrence!";
                } else if (holidayProvider == null) {
                    msg = "Can not save";
                }
                if (msg != null) {
                    // once the message text is present, it is time to show it and exit
                    view.showMessage(msg);
                    view.setEnableButtons(true);
                    return;
                }
                long nextOccurrence;
                try {
                    nextOccurrence = format.parse(next).getTime();
                } catch (ParseException e) {
                    view.showMessage("Failed to elaborate the holiday date!");
                    view.setEnableButtons(true);
                    return;
                }
                Factory<Event> factory = new Factory<>();
                Event h = factory.produce(classes[pos], id, name, nextOccurrence);
                if (id != -1) {
                    if (holidayProvider.save(h) != -1) {
                        view.onHolidayUpdated(h);
                    }
                } else {
                    id = holidayProvider.save(h);
                    if (id != -1) {
                        h = factory.produce(classes[pos], id, name, nextOccurrence);
                        view.onHolidayAdded(h);
                    } else {
                        view.showMessage("Failed to save the holiday!");
                    }
                }
                view.setEnableButtons(true);
            }
        })).run();
    }

    /**
     * Set a provider of the mEvents
     */
    public void setHolidayProvider(IProvider hp) {
        this.holidayProvider = hp;
    }


    /**
     * This method is called when a user clicks the "cancel" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
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

    public String toString() {
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

    public void load(Event h) {
        this.name = h.getName();
        this.date = format.format(h.getNextOccurrence());
        this.periodicity = indexOf(h.getClass().getCanonicalName());
        this.id = h.getId();
    }

    /**
     * Returns index at which given class is  present in {@link #classes}.
     * <br>
     * If nothing is found, -1 is returned.
     *
     * @param name
     * @return
     */
    private int indexOf(String name) {
        int len = classes.length;
        for (int i = 0; i < len; i++) {
            if (name.equals(classes[i])) {
                return i;
            }
        }
        return -1;
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
