package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.fragments.AllHolidays;
import com.veontomo.fiestatime.views.AddHolidayView;
import com.veontomo.fiestatime.views.MVPView;


/**
 * Presenter for the all-holidays view.
 */
public class AllHolidaysPresenter implements MVPPresenter {


    private final AllHolidays view;


    public AllHolidaysPresenter(MVPView view) {
        this.view = (AllHolidays) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void bindView(MVPView v) {

    }

    @Override
    public void onConfirm(String name, String next, int pos) {

    }

    @Override
    public void onCancel(String name, String next, int pos) {

    }

    @Override
    public void onSaveState(Bundle b) {
        // TODO put into the bundle all data that allows to restore the presenter state
        // later on

    }

    @Override
    public void onRestoreState(Bundle b) {
        // TODO restore the presenter state from the bundle
    }
}
