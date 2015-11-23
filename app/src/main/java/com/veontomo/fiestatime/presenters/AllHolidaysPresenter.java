package com.veontomo.fiestatime.presenters;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Holiday;
import com.veontomo.fiestatime.views.MultiHolidaysView;
import com.veontomo.fiestatime.views.MVPView;

import java.util.ArrayList;


/**
 * Presenter for the all-holidays view.
 */
public class AllHolidaysPresenter extends MultiHolidaysPresenter {

    public AllHolidaysPresenter(MultiHolidaysView view) {
        super(view);
    }


    @Override
    public void bindView(final MVPView v) {
        if (this.holidays != null) {
            v.updateViews();
        } else if (mTask != null) {
            mTask.setOnDataLoaded(this);
            mTask.execute();
        }
    }



    public ArrayList<Holiday> getHolidays() {
        return this.holidays;
    }



    public void addHoliday(Holiday h) {
        this.holidays.add(h);
        view.updateViews();
    }

    /**
     * Elaborates a click on item with number index.
     *
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.holidays.get(index));
        view.onHolidayClick(this.holidays.get(index));
    }
}
