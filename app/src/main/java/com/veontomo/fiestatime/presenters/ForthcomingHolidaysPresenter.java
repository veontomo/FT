package com.veontomo.fiestatime.presenters;

import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiHolidaysView;

/**
 * Presenter for displaying forthcoming holidays
 *
 */
public class ForthcomingHolidaysPresenter extends MultiHolidaysPresenter {


    public ForthcomingHolidaysPresenter(MultiHolidaysView view) {
        super(view);
    }


    @Override
    public void bindView(MVPView v) {
        if (this.holidays != null) {
            v.updateViews();
        } else if (holidayProvider != null) {
            holidayProvider.lazyLoadForthcoming(this);
        }
    }


}
