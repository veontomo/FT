package com.veontomo.fiestatime.api;

/**
 * Holiday
 *
 */
public class Holiday {
    /**
     * How often the holiday occurs
     */
    public final static int ONCE = 0;
    public final static int WEEK = 1;
    public final static int MONTH = 2;
    public final static int YEAR = 3;

    public final String name;
    public long nextOccurence;
    public int periodicity;

    public Holiday(String name, long next, int periodicity){
        this.name = name;
        this.nextOccurence = next;
        this.periodicity = periodicity;
    }

}

