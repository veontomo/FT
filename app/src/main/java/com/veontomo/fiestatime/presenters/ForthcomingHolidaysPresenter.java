package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Presenter for displaying forthcoming holidays
 *
 */
public class ForthcomingHolidaysPresenter implements MVPPresenter {

    private final MVPView view;
    private String holidayName;

    public ForthcomingHolidaysPresenter(MVPView view) {
        this.view = view;
        this.holidayName = "constructor holiday name";
    }


    @Override
    public void bindView(MVPView v) {
        v.updateViews();
    }

    @Override
    public void saveState(Bundle b) {
        view.saveState(b);
    }

    @Override
    public void restoreState(Bundle b) {

    }

    public String getHolidayName() {
        return holidayName;
    }
}
