package com.veontomo.fiestatime.api;


import java.util.List;

/**
 * Event provider interface
 */
public interface IProvider<T> {
    /**
     * Saves the given object into a storage and returns an id with which that item can be retrieved.
     *
     * @param item  object to saved
     */
    long save(T item);

    /**
     * Returns the items from the storage
     * @return
     */
    List<T> getItems();


    /**
     * Returns nearest mEvents
     * @return
     */
    T getNearest(long time);

    /**
     * Returns a list of mEvents whose date should be adjusted
     * @param
     */
    List<T> toAdjustDate(long time);

    /**
     * Updates record that is  already present in the storage.
     *
     * Return true if the update is successful, false - otherwise
     * @param item
     */
    boolean update(T item);
}
