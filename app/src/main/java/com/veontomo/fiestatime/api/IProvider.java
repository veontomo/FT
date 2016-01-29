package com.veontomo.fiestatime.api;


import android.support.annotation.NonNull;

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
     * Returns a list of nearest events that occur at the same time after given time
     * @return
     */
    @NonNull
    List<T> getNearest(long time);

    /**
     * Returns a list of events whose date should be adjusted
     * @param
     */
    List<T> toAdjustDate(long time);

    /**
     * Deletes the item with given id from the storage
     * @param id
     * @return
     */
    boolean delete(long id);

}
