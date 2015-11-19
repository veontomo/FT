package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.IHolidayProvider;
import com.veontomo.fiestatime.fragments.AllHolidays;
import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiHolidaysView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario Rossi on 19/11/2015 at 15:58.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public abstract class MultiHolidaysPresenter implements MVPPresenter {
    private final static String HOLIDAYS_TOKEN = "holidays";
    protected final MultiHolidaysView view;
    protected ArrayList<Holiday> holidays;
    protected IHolidayProvider holidayProvider;

    public MultiHolidaysPresenter(MultiHolidaysView view) {
        this.view = view;
    }


    @Override
    public void bindView(MVPView v) {

    }

    /**
     * Set a provider of the holidays
     */
    public void setHolidayProvider(IHolidayProvider hp) {
        this.holidayProvider = hp;
    }

    @Override
    public void saveState(Bundle b) {
        String[] holidaysArray = serialize(this.holidays);
        b.putStringArray(HOLIDAYS_TOKEN, holidaysArray);
    }

    /**
     * Converts list of holidays into array of strings.
     *
     * @param data
     * @return
     */
    private String[] serialize(List<Holiday> data) {
        int s = data.size();
        String[] result = new String[s];
        Holiday h;
        for (int i = 0; i < s; i++) {
            h = data.get(i);
            result[i] = h.serialize();
        }
        return result;
    }

    /**
     * Recreates list of holidays from array of strings
     *
     * @param items
     * @return
     */
    private ArrayList<Holiday> deserialize(String[] items) {
        ArrayList<Holiday> result = new ArrayList<>();
        Holiday h;
        for (String item : items) {
            h = Holiday.deserialize(item);
            if (h != null) {
                result.add(h);
            } else {
                Logger.log("Failed to deserialize " + item);
            }
        }
        return result;
    }


    @Override
    public void restoreState(Bundle b) {
        if (b != null) {
            String[] items = b.getStringArray(HOLIDAYS_TOKEN);
            this.holidays = deserialize(items);
        }
    }

    /**
     * Loads holidays into the presenter AND initializes the view
     * TODO: split the method in two, since it performs two actions
     */
    public void load(List<Holiday> holidays) {
        this.holidays = new ArrayList<>();
        for (Holiday holiday : holidays) {
            this.holidays.add(holiday);
        }
        view.updateViews();
    }


}
