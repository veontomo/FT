package com.veontomo.fiestatime.presenters;

import android.content.Context;
import android.os.Bundle;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.HolidayProvider;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.fragments.AllHolidays;
import com.veontomo.fiestatime.views.MVPView;

import java.util.ArrayList;


/**
 * Presenter for the all-holidays view.
 */
public class AllHolidaysPresenter implements MVPPresenter {


    private final AllHolidays view;

    private final static String HOLIDAY_NAMES_TOKEN = "names";

    private ArrayList<String> holidayNames;

    private HolidayProvider hp;


    public AllHolidaysPresenter(MVPView view) {
        this.view = (AllHolidays) view;
        this.holidayNames = new ArrayList<>();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void bindView(final MVPView v, final Context context) {
        if (this.holidayNames != null) {
            v.initializeViews();
        } else {
            hp = new HolidayProvider(new Storage(context));

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
}
