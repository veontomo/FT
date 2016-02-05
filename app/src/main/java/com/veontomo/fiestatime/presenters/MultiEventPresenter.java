package com.veontomo.fiestatime.presenters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.veontomo.fiestatime.Logger;
import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.DetailedAdapter;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.EventDBProvider;
import com.veontomo.fiestatime.api.Factory;
import com.veontomo.fiestatime.api.MonthEvent;
import com.veontomo.fiestatime.api.RetrieveAllEventsTask;
import com.veontomo.fiestatime.api.SingleEvent;
import com.veontomo.fiestatime.api.Storage;
import com.veontomo.fiestatime.api.WeekEvent;
import com.veontomo.fiestatime.api.YearEvent;
import com.veontomo.fiestatime.views.MVPView;
import com.veontomo.fiestatime.views.MultiEventView;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MultiEventPresenter implements MVPPresenter {
    private final static String HOLIDAYS_TOKEN = "mEvents";
    protected final MultiEventView mView;
    protected final ArrayList<Event> mEvents;
    private final Context mContext;
    private final DetailedAdapter<Event> mAdapter;
    /**
     * list of available event types
     */
    private final String[] eventClassNames = new String[]{SingleEvent.class.getCanonicalName(),
            WeekEvent.class.getCanonicalName(),
            MonthEvent.class.getCanonicalName(),
            YearEvent.class.getCanonicalName()};
    /**
     * list of ids of layouts corresponding to different event types
     */
    private final int[] eventLayouts = new int[]{R.layout.single_event_row, R.layout.week_event_row, R.layout.month_event_row, R.layout.year_event_row};


    public MultiEventPresenter(MultiEventView view, final Context context) {
        this.mView = view;
        this.mContext = context;
        this.mEvents = new ArrayList<>();
        this.mAdapter = new DetailedAdapter<>(context, eventClassNames, eventLayouts);
        this.mView.setAdapter(this.mAdapter);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mView.onEventClick(mEvents.get(position));
            }
        };
        this.mView.setOnClickListener(listener);

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
            this.mEvents.addAll(deserialize(items));
        }
    }

    /**
     * Loads events into the presenter and the adapter
     */
    public void load(final List<Event> events) {
        this.mEvents.addAll(events);
        this.mAdapter.load(mEvents);

    }


    public ArrayList<Event> getEvents() {
        return this.mEvents;
    }


    public void addEvent(Event h) {
        this.mEvents.add(h);
        mView.updateViews();
    }

    /**
     * Elaborates a click on item with number index.
     *
     * @param index
     */
    public void onItemClick(int index) {
        Logger.log("click on " + this.mEvents.get(index));
        mView.onEventClick(this.mEvents.get(index));
    }


    public void updateEvent(Event h) {
        for (Event event : this.mEvents) {
            if (event.getId() == h.getId()) {
                int pos = this.mEvents.indexOf(event);
                this.mEvents.set(pos, h);
                break;
            }
            mView.updateViews();
            onLoaded();
        }

    }

    /**
     * This method is called after the events have been loaded into the presenter.
     * <br>
     * Notifies the adapter that the data has been changed.
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

    /**
     * Created by Mario Rossi on ${CURRENTDATE} at ${CURRENTTIME}.
     *
     * @author veontomo@gmail.com
     * @since xx.xx
     */
    public void setEventTypes(String[] strings) {


    }
}
