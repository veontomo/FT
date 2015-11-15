package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.api.IHolidayProvider;
import com.veontomo.fiestatime.fragments.AllHolidays;
import com.veontomo.fiestatime.views.MVPView;

import java.util.ArrayList;
import java.util.List;


/**
 * Presenter for the all-holidays view.
 */
public class AllHolidaysPresenter implements MVPPresenter {


    private final AllHolidays view;

    private final static String HOLIDAY_NAMES_TOKEN = "names";

    private ArrayList<Holiday> holidays;

    private IHolidayProvider holidayProvider;


    public AllHolidaysPresenter(AllHolidays view) {
        this.view = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void bindView(final MVPView v) {
        if (this.holidays != null) {
            v.initializeViews();
        } else if (holidayProvider != null) {
            holidayProvider.lazyLoad(this);
        }
    }

    @Override
    public void onConfirm(String name, String next, int pos) {

    }

    @Override
    public void onCancel(String name, String next, int pos) {

    }

    @Override
    public void onSaveState(Bundle b) {
        Logger.log("saving the presenter state" + this.holidays.size());
        String[] holidaysArray = serialize(this.holidays);
        b.putStringArray(HOLIDAY_NAMES_TOKEN, holidaysArray);
    }

    @Override
    public void onRestoreState(Bundle b) {
        if (b != null) {
            Logger.log("restoring the presenter state");

            this.holidays = deserialize(b.getStringArray(HOLIDAY_NAMES_TOKEN));
        } else {
            Logger.log("nothing to restore from!");
        }
    }

    /**
     * Converts list of holidays into array of strings.
     * @param data
     * @return
     */
    private String[] serialize(List<Holiday> data){
        int s = data.size();
        String[] result = new String[s];
        Holiday h;
        for (int i = 0; i < s; i++){
            h = data.get(i);
            result[i] = h.serialize();
        }
        return result;
    }

    /**
     * Recreates list of holidays from array of strings
     * @param items
     * @return
     */
    private ArrayList<Holiday> deserialize(String[] items){
        ArrayList<Holiday> result = new ArrayList<>();
        for (String item : items){
            result.add(Holiday.deserialize(item));
        }
        return result;
    }


    public ArrayList<Holiday> getHolidays() {
        return this.holidays;
    }

    /**
     * Set a provider of the holidays
     */
    public void setHolidayProvider(IHolidayProvider hp) {
        this.holidayProvider = hp;
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
        view.initializeViews();
    }


    public void addHoliday(Holiday h){
        this.holidays.add(h);
        view.initializeViews();
    }

    /**
     * Elaborates a click on item with number index.
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.holidays.get(index));
        view.onHolidayClick(this.holidays.get(index));
    }
}
