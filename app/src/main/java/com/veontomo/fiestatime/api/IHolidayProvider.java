package com.veontomo.fiestatime.api;


import java.util.List;

/**
 * Holiday provider interface
 */
public interface IHolidayProvider {
    /**
     * Saves the holiday into a storage and returns the id with which the holiday can be retrieved.
     *
     * @param holiday
     */
    long save(Holiday holiday);


    List<Holiday> getHolidays();



}
