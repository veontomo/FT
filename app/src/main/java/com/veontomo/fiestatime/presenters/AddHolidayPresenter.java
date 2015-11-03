package com.veontomo.fiestatime.presenters;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Implementation of {@link MVPPresenter} for adding holidays
 *
 */
public class AddHolidayPresenter implements MVPPresenter {
    private final MVPView view;

    public AddHolidayPresenter(MVPView view){
        this.view = view;

    }
    @Override
    public void onStart() {

    }
}
