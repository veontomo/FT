package com.veontomo.fiestatime.views;

import com.veontomo.fiestatime.api.Event;

/**
 * Interface for View in a MVP pattern that is used to add mEvents.
 */
public interface MultiEventView extends MVPView {
    /**
     * Adds a holiday
     * @param h
     */
    void addEvent(Event h);

    /**
     * Eliminates a holiday at given position
     * @param pos
     */
    void deleteEvent(int pos);

    /**
     * This method is called when a click on a given event occurs
     * @param event
     */
    void onEventClick(Event event);

    /**
     * Updates a holiday
     * @param h
     */
    void updateEvent(Event h);
}
