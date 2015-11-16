package com.veontomo.fiestatime.views;

import android.os.Bundle;
import android.view.View;

import com.veontomo.fiestatime.api.Holiday;

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

    void onDateClick(View v);

    void setDate(String date);

    String restoreName(Bundle b);

    String restoreDate(Bundle b);

    int restorePeriodicity(Bundle b);

    void onHolidayAdded(Holiday h);

    void load(Holiday h);
}
