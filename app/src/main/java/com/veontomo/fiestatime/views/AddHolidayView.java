package com.veontomo.fiestatime.views;

import android.os.Bundle;
import android.view.View;

import com.veontomo.fiestatime.api.Holiday;

/**
 * View for adding holidays
 */
public interface AddHolidayView extends MVPView {


    void initializeName();

    void initializeNextOccurrence();

    void initializePeriodicity();

    void onDateClick(View v);

    void setDate(String date);

    String restoreName(Bundle b);

    String restoreDate(Bundle b);

    int restorePeriodicity(Bundle b);

    void onHolidayAdded(Holiday h);

    void load(Holiday h);
}
