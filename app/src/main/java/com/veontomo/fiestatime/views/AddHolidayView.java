package com.veontomo.fiestatime.views;

import android.os.Bundle;
import android.view.View;

import com.veontomo.fiestatime.api.Holiday;

/**
 * View for adding holidays
 */
public interface AddHolidayView extends MVPView {

    void onDateClick(View v);

    void setDate(String date);

    void onHolidayAdded(Holiday h);

    void load(Holiday h);

    /**
     * Disables "confirm" and "cancel" buttons
     * @param status true to enable buttons, false to disable them
     */
    void setEnableButtons(boolean status);
}
