package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.ITask;
import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiHolidaysView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MultiHolidaysPresenter implements MVPPresenter {
    private final static String HOLIDAYS_TOKEN = "holidays";
    protected final MultiHolidaysView view;
    protected ArrayList<Holiday> holidays;
    protected ITask mTask;

    public MultiHolidaysPresenter(MultiHolidaysView view) {
        this.view = view;
    }


    @Override
    public void bindView(final MVPView v) {
        if (this.holidays != null) {
            v.updateViews();
        } else if (mTask != null) {
            mTask.setOnDataLoaded(this);
            mTask.execute();
        }
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
        int s = (data == null) ? 0 : data.size();
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


    public void setTask(ITask task) {
        this.mTask = task;
    }

    public ArrayList<Holiday> getHolidays() {
        return this.holidays;
    }


    public void addHoliday(Holiday h) {
        this.holidays.add(h);
        view.updateViews();
    }

    /**
     * Elaborates a click on item with number index.
     *
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.holidays.get(index));
        view.onHolidayClick(this.holidays.get(index));
    }


    public void updateHoliday(Holiday h) {
        for (Holiday holiday : this.holidays) {
            if (holiday.id == h.id) {
                int pos = this.holidays.indexOf(holiday);
                this.holidays.set(pos, h);
                view.updateViews();

            }
        }

    }
}
