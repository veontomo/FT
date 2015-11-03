package com.veontomo.fiestatime.views;

import android.view.View;

/**
 * Created by Mario Rossi on 03/11/2015 at 14:52.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public interface AddHolidayView extends MVPView {
    /**
     * Fills in text views, edit views etc. that are present on the current activity
     */
    void fillInViews();

    void setName();

    void setNextOccurrence();

    void setPeriodicity();

    void onConfirm();

    void onCancel();

    void onSetDate();

    void onDateClick(View v);

    void setDate(String date);
}
