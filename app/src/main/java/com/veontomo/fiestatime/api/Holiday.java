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
    public final static int WEEKLY = 1;
    public final static int MONTH = 2;
    public final static int YEAR = 3;

    public final String name;
    public long nextOccurrence;
    public int periodicity;
    public final long id;

    public Holiday(String name, long next, int periodicity){
        this(-1, name, next, periodicity);
    }

    public Holiday(long id, String name, long next, int periodicity){
        this.name = name;
        this.nextOccurrence = next;
        this.periodicity = periodicity;
        this.id = -1;
    }

}
