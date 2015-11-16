package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Presenter for displaying forthcoming holidays
 *
 */
public class ForthcomingHolidaysPresenter implements MVPPresenter {

    private final MVPView view;

    public ForthcomingHolidaysPresenter(MVPView view) {
        this.view = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void bindView(MVPView v) {
        v.initializeViews();
    }

    @Override
    public void onConfirm(String name, String next, int pos) {

    }

    @Override
    public void onCancel(String name, String next, int pos) {

    }

    @Override
    public void onSaveState(Bundle b) {
        view.onSaveState(b);
    }

    @Override
    public void onRestoreState(Bundle b) {

    }
}
