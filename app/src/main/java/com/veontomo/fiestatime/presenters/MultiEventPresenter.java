package com.veontomo.fiestatime.presenters;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.Factory;
import com.veontomo.fiestatime.api.RetrieveAllEventsTask;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiEventView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MultiEventPresenter implements MVPPresenter {
    private final static String HOLIDAYS_TOKEN = "mEvents";
    protected final MultiEventView view;
    protected ArrayList<Event> mEvents;
    private final Context mContext;
    private BaseAdapter mAdapter;


    public MultiEventPresenter(MultiEventView view, final Context context) {
        this.view = view;
        this.mContext = context;
    }

    public void setAdapter(BaseAdapter adapter){
        this.mAdapter = adapter;
        this.view.setAdapter(adapter);
    }


    @Override
    public void bindView(final MVPView v) {
        if (this.mEvents != null) {
            v.updateViews();
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
    public void load(final List<Event> events) {
        this.mEvents.addAll(events);

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

    /**
     * This method is called after the events have been loaded into the presenter
     */
    public void onLoaded() {
        this.mAdapter.notifyDataSetChanged();

    }

    /**
     * Retrieve events from the storage.
     */
    public void loadEvents() {
        (new RetrieveAllEventsTask(new EventDBProvider(new Storage(mContext)), this)).execute();

    }
}
