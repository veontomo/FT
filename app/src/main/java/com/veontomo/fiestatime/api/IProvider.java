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
     * Returns nearest holidaysю
     * @return
     */
    T getNearest();

}
