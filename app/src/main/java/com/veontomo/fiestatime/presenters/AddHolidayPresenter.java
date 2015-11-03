package com.veontomo.fiestatime.presenters;

import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.views.MVPView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding holidays
 */
public class AddHolidayPresenter implements MVPPresenter {
    private final MVPView view;

    private static final String HOLIDAY_NAME_TOKEN = "holidayName";
    private static final String NEXT_OCCURRENCE_TOKEN = "nextOccurrence";
    private static final String PERIODICITY_TOKEN = "periodicity";

    private static final String ONSCREEN_DATE_FORMAT = "d MMMM yyyy";
    private static final SimpleDateFormat format = new SimpleDateFormat(ONSCREEN_DATE_FORMAT);
    private String mHolidayName;
    private String mNextOccurrence;
    private int mPeriodicity = -1;


    public AddHolidayPresenter(MVPView view) {
        this.view = view;

    }

    @Override
    public void onStart() {


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

    public String getNextOccurrence(){
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public int getPeriodicity(){
        return Holiday.WEEKLY;
    }
}
