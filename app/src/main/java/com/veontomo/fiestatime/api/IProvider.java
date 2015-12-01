package com.veontomo.fiestatime.api;


import java.util.List;

/**
 * Holiday provider interface
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
     * Returns nearest holidays
     * @return
     */
    T getNearest();

    /**
     * Returns a list of holidays whose date should be adjusted
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
