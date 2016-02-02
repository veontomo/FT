package com.veontomo.fiestatime.presenters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.Factory;
import com.veontomo.fiestatime.api.IProvider;
import com.veontomo.fiestatime.views.AddEventView;
import com.veontomo.fiestatime.views.MVPView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding mEvents
 */
public class ManageEventPresenter implements MVPPresenter {

    private static final SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
    /**
     * name of the token under which the presenter saves the event's name in the bundle
     */
    private static final String NAME_TOKEN = "name";

    /**
     * name of the token under which the presenter saves the event's date in the bundle
     */
    private static final String DATE_TOKEN = "date";
    /**
     * name of the token under which the presenter saves the event's periodicity in the bundle
     */
    private static final String PERIODICITY_TOKEN = "periodicity";


    private final AddEventView view;

    /**
     * Date that corresponds to the event's next occurrence
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
    private long id = -1;

    private IProvider<Event> eventProvider;
    /**
     * list of canonical class names of available classes
     */
    private String[] mEventTypes;

    public ManageEventPresenter(AddEventView view) {
        this.view = view;
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
     * This method is called when the event has been deleted from the storage
     */
    public void onDeleted(){
        reset();
        view.showMessage(R.string.event_deleted);
        view.updateViews();
    }



    /**
     * This method is called when a user clicks the "confirm" button
     *
     * @param name content of the text view corresponding to event's name
     * @param next content of the date picker dialog text view corresponding to event's date
     * @param pos  index of the item selected from dropdown list corresponding to the event's periodicity
     */
    public void confirm(final String name, final String next, final int pos) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                view.setEnableButtons(false);
                int msgCode = -1;
                // elaborate three similar scenarios
                if (name == null || name.isEmpty()) {
                    msgCode = R.string.event_name_missing;
                } else if (next == null || next.isEmpty()) {
                    msgCode = R.string.event_next_occurrence_missing;
                } else if (eventProvider == null) {
                    msgCode = R.string.event_provider_missing;
                }
                if (msgCode != -1) {
                    // once the message text is present, it is time to show it and exit
                    view.showMessage(msgCode);
                    view.setEnableButtons(true);
                    return;
                }
                long nextOccurrence;
                try {
                    nextOccurrence = format.parse(next).getTime();
                } catch (ParseException e) {
                    msgCode = R.string.wrong_date;
                    view.showMessage(msgCode);
                    view.setEnableButtons(true);
                    return;
                }
                Factory<Event> factory = new Factory<>();
                Event h = factory.produce(mEventTypes[pos], id, name, nextOccurrence);
                if (id != -1) {
                    if (eventProvider.save(h) != -1) {
                        view.onEventUpdated(h);
                    }
                } else {
                    id = eventProvider.save(h);
                    if (id != -1) {
                        h = factory.produce(mEventTypes[pos], id, name, nextOccurrence);
                        view.onEventAdded(h);
                    } else {
                        msgCode = R.string.save_event_fail;
                        view.showMessage(msgCode);
                    }
                }
                view.setEnableButtons(true);
            }
        })).run();
    }

    /**
     * Set a provider of events
     */
    public void setEventProvider(IProvider<Event> hp) {
        this.eventProvider = hp;
    }


    /**
     * This method is called when a user clicks the "cancel" button
     *
     */
    public void reset() {
        this.id = -1;
        this.name = null;
        this.date = null;
        this.periodicity = -1;

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

    public String getEventName() {
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
     * Returns index at which given class is  present in {@link #mEventTypes}.
     * <br>
     * If nothing is found, -1 is returned.
     *
     * @param name
     * @return
     */
    private int indexOf(String name) {
        int len = mEventTypes.length;
        for (int i = 0; i < len; i++) {
            if (name.equals(mEventTypes[i])) {
                return i;
            }
        }
        return -1;
    }

    public void setEventTypes(String[] eventTypes) {
        mEventTypes = eventTypes;
    }

    /**
     * Deletes current event from the storage
     */
    public void deleteEvent() {
        if (id != -1) {
            (new DeleteEventTask(id, this, eventProvider)).execute();
        }


    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        /**
         * to whom the result of the date picker should be given
         */
        public static ManageEventPresenter presenter;
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
