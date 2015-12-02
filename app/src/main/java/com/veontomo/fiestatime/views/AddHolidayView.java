package com.veontomo.fiestatime.views;

import android.view.View;

import com.veontomo.fiestatime.api.Event;

/**
 * View for adding mEvents
 */
public interface AddHolidayView extends MVPView {

    void onDateClick(View v);

    void setDate(String date);

    void onHolidayAdded(Event h);

    void onHolidayUpdated(Event h);

    void load(Event h);

    /**
     * Disables "confirm" and "cancel" buttons
     * @param status true to enable buttons, false to disable them
     */
    void setEnableButtons(boolean status);

}
