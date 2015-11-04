package com.veontomo.fiestatime.views;

import android.view.View;

/**
 * Created by Mario Rossi on 03/11/2015 at 14:52.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public interface AddHolidayView extends MVPView {


    void initializeName();

    void initializeNextOccurrence();

    void initializePeriodicity();

    void onConfirm();

    void onCancel();

    void onDateClick(View v);

    void setDate(String date);
}
