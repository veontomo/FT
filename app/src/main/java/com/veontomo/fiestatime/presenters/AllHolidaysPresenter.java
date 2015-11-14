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

    private ArrayList<String> holidayNames;

    private IHolidayProvider holidayProvider;


    public AllHolidaysPresenter(AllHolidays view) {
        this.view = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void bindView(final MVPView v) {
        if (this.holidayNames != null) {
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
        Logger.log("saving the presenter state" + this.holidayNames.size());
        b.putStringArrayList(HOLIDAY_NAMES_TOKEN, this.holidayNames);
    }

    @Override
    public void onRestoreState(Bundle b) {
        if (b != null) {
            Logger.log("restoring the presenter state");
            this.holidayNames = b.getStringArrayList(HOLIDAY_NAMES_TOKEN);
        } else {
            Logger.log("nothing to restore from!");
        }
    }

    public ArrayList<String> getHolidayNames() {
        return this.holidayNames;
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
        this.holidayNames = new ArrayList<>();
        for (Holiday holiday : holidays) {
            this.holidayNames.add(holiday.name);
        }
        view.initializeViews();
    }


    public void addHoliday(Holiday h){
        this.holidayNames.add(h.name);
        view.initializeViews();
    }

    /**
     * Elaborates a click on item with number index.
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.holidayNames.get(index));
    }
}
