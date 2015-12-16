package com.veontomo.fiestatime.presenters;

import android.os.Bundle;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.Factory;
import com.veontomo.fiestatime.api.ITask;
import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiEventView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MultiEventPresenter implements MVPPresenter {
    private final static String[] classes = new String[]{"com.veontomo.fiestatime.api.SingleEvent",
            "com.veontomo.fiestatime.api.WeekEvent",
            "com.veontomo.fiestatime.api.MonthEvent",
            "com.veontomo.fiestatime.api.YearEvent"};
    private final static String HOLIDAYS_TOKEN = "mEvents";
    protected final MultiEventView view;
    protected ArrayList<Event> mEvents;
    protected ITask mTask;

    public MultiEventPresenter(MultiEventView view) {
        this.view = view;
    }


    @Override
    public void bindView(final MVPView v) {
        if (this.mEvents != null) {
            v.updateViews();
        } else if (mTask != null) {
            mTask.setOnDataLoaded(this);
            mTask.execute();
        }
    }


    @Override
    public void saveState(Bundle b) {
        String[] holidaysArray = serialize(this.mEvents);
        b.putStringArray(HOLIDAYS_TOKEN, holidaysArray);
    }

    /**
     * Converts list of mEvents into array of strings.
     *
     * @param data
     * @return
     */
    private String[] serialize(List<Event> data) {
        int s = (data == null) ? 0 : data.size();
        String[] result = new String[s];
        Event h;
        for (int i = 0; i < s; i++) {
            h = data.get(i);
            result[i] = h.serialize();
        }
        return result;
    }

    /**
     * Recreates list of mEvents from array of strings
     *
     * @param items
     * @return
     */
    private ArrayList<Event> deserialize(String[] items) {
        ArrayList<Event> result = new ArrayList<>();
        Event h;
        Factory<Event> factory = new Factory<>();
        for (String item : items) {
            h = factory.produce(item);
            if (h != null) {
                result.add(h);
            } else {
                Logger.log("Failed to deserialize " + item);
            }
        }
        return result;
    }


    @Override
    public void restoreState(Bundle b) {
        if (b != null) {
            String[] items = b.getStringArray(HOLIDAYS_TOKEN);
            this.mEvents = deserialize(items);
        }
    }

    /**
     * Loads mEvents into the presenter AND initializes the view
     * TODO: split the method in two, since it performs two actions
     */
    public void load(List<Event> events) {
        this.mEvents = new ArrayList<>();
        for (Event event : events) {
            this.mEvents.add(event);
        }
        view.updateViews();
    }


    public void setTask(ITask task) {
        this.mTask = task;
    }

    public ArrayList<Event> getEvents() {
        return this.mEvents;
    }


    public void addEvent(Event h) {
        this.mEvents.add(h);
        view.updateViews();
    }

    /**
     * Elaborates a click on item with number index.
     *
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.mEvents.get(index));
        view.onEventClick(this.mEvents.get(index));
    }


    public void updateEvent(Event h) {
        for (Event event : this.mEvents) {
            if (event.getId() == h.getId()) {
                int pos = this.mEvents.indexOf(event);
                this.mEvents.set(pos, h);
                view.updateViews();

            }
        }

    }
}
