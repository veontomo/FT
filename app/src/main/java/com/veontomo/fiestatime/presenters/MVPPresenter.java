package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.views.MVPView;

/**
 * Presenter of MVP architectural pattern
 */
public interface MVPPresenter {
    void onStart();

    void bindView(MVPView v);


    /**
     * This method is called when a user clicks the "confirm" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
    void onConfirm(String name, String next, int pos);

    /**
     * This method is called when a user clicks the "cancel" button
     *
     * @param name content of the text view corresponding to holiday's name
     * @param next content of the date picker dialog text view corresponding to holiday's date
     * @param pos  index of the item selected from dropdown list corresponding to the holiday's periodicity
     */
    void onCancel(String name, String next, int pos);


    void onSaveState(Bundle b);

    void onRestoreState(Bundle b);
}
