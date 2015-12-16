package com.veontomo.fiestatime.views;

import android.content.Context;
import android.view.View;

import com.veontomo.fiestatime.api.Event;

/**
 * View for adding mEvents
 */
public interface AddEventView extends MVPView {

    void onDateClick(View v);

    void setDate(String date);

    void onEventAdded(Event h);

    void onEventUpdated(Event h);

    void load(Event h);

    /**
     * Disables "confirm" and "cancel" buttons
     * @param status true to enable buttons, false to disable them
     */
    void setEnableButtons(boolean status);

}
