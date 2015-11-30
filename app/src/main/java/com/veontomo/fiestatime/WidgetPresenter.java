package com.veontomo.fiestatime;

import com.veontomo.fiestatime.views.MVPView;

import java.util.Random;

/**
 * Presenter for the countdown widget.
 */
public class WidgetPresenter {
    private final MVPView view;

    /**
     * a mock for the forthcoming holiday names
     */
    private final String[] mockHolidays = new String[]{"New Year", "holiday", "Saturday", "Birthday"};

    /**
     * number of days to the nearest holiday
     */
    private int nearest;

    /**
     * number of days to the holiday after the nearest
     */
    private int nextNearest;

    /**
     * String representation of the forthcoming holiday(s)
     */
    private String nearestDescr;

    public WidgetPresenter(MVPView view){
        this.view = view;
    }


    /**
     * Updates the presenter data
     */
    public void update() {
        Random random = new Random();
        this.nearest = random.nextInt(30);
        this.nextNearest = this.nearest + random.nextInt(30);
        this.nearestDescr = mockHolidays[this.nearest % mockHolidays.length];
    }

    public void onUpdated(){
        view.updateViews();
    }

    public int getNearest() {
        return nearest;
    }

    public int getAfterNearest() {
        return nextNearest;
    }

    public String getDescription() {
        return nearestDescr;
    }
}
