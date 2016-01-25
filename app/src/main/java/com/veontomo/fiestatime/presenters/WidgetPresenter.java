package com.veontomo.fiestatime.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.veontomo.fiestatime.R;
import com.veontomo.fiestatime.api.Event;
import com.veontomo.fiestatime.api.IProvider;
import com.veontomo.fiestatime.api.WidgetUpdateTask;
import com.veontomo.fiestatime.views.MVPView;

import java.util.List;

/**
 * Presenter for the countdown widget.
 */
public class WidgetPresenter {
    private final MVPView view;
    /**
     * number of days to the nearest holiday
     */
    private int nearest = -1;

    /**
     * number of days to the holiday after the nearest
     */
    private int nextNearest = -1;

    private IProvider<Event> mItemProvider;

    /**
     * list of nearest events
     */
    private List<Event> mNearestEvents;

    public WidgetPresenter(MVPView view) {
        this.view = view;
    }


    /**
     * Updates the presenter data
     */
    public void update() {
        WidgetUpdateTask worker = new WidgetUpdateTask(this, mItemProvider);
        worker.execute();
    }

    /**
     * Gets called once the presenter state is updated.
     * <br>
     * It gets called from {@link  WidgetUpdateTask#onPostExecute(Void)}.
     */
    public void onUpdated() {
        view.updateViews();
    }

    public int getNearest() {
        return nearest;
    }

    public int getNextNearest() {
        return nextNearest;
    }

    public String getNearestEventsDescription(final Context context) {
        StringBuilder builder = new StringBuilder();
        for (Event e : mNearestEvents) {
            builder.append(e.getName());
            builder.append(System.getProperty("line.separator", " "));
        }
        return builder.toString();
    }

    public void setNearest(int nearest) {
        this.nearest = nearest;
    }

    public void setNextNearest(int nextNearest) {
        this.nextNearest = nextNearest;
    }

    public void setItemProvider(IProvider<Event> itemProvider) {
        mItemProvider = itemProvider;
    }


    /**
     * Returns a phrase corresponding to the number of days to the nearest event(s).
     * <p/>
     * If the number of days is negative, then display "no event" text.
     * If the number of days is equal to 0, then display "today" (localized).
     * If the number of days is equal to 1, then display "tomorrow" (localized).
     * In other cases display the argument.
     *
     * @param context it is necessary in order to take localized version of the message
     * @return human-friendly form of the number of days
     */
    public String getCountdownPhrase(final Context context) {
        String text;
        int daysNorm = getNearest();
        if (daysNorm < 0) {
            daysNorm = -1;
        }
        switch (daysNorm) {
            case -1:
                text = context.getString(R.string.noEvents);
                break;
            case 0:
                text = context.getString(R.string.today);
                break;
            case 1:
                text = context.getString(R.string.tomorrow);
                break;
            default:
                text = String.valueOf(daysNorm);
        }
        return text;
    }

    /**
     * Returns description of the event(s) coming after the nearest event.
     *
     * @param context it is necessary in order to take localized version of the message
     * @return human-friendly form of the number of days
     */
    public String getNextNearestEventsDescription(final Context context) {
        String text;
        int days = getNextNearest();
        if (days > 0) {
            text = context.getString(R.string.days_to_event);
            text = text.replaceFirst("#1", String.valueOf(days));
        } else {
            text = context.getString(R.string.noNextToNearestEvents);
        }
        return text;
    }

    /**
     * Setter for {@link #mNearestEvents}.
     * @param nearestEvents
     */
    public void setNearestEvents(final List<Event> nearestEvents) {
        mNearestEvents = nearestEvents;
    }

}
