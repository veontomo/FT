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
}
