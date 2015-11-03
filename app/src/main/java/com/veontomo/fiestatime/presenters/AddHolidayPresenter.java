package com.veontomo.fiestatime.presenters;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.views.MVPView;

import java.util.Calendar;

/**
 * Implementation of {@link MVPPresenter} for adding holidays
 *
 */
public class AddHolidayPresenter implements MVPPresenter {
    private final MVPView view;

    public AddHolidayPresenter(MVPView view){
        this.view = view;

    }
    @Override
    public void onStart() {



    }
}
