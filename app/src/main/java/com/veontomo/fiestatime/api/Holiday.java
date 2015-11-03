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
    public long nextOccurence;
    public int periodicity;
    public final int id;

    public Holiday(String name, long next, int periodicity){
        this(-1, name, next, periodicity);
    }

    public Holiday(int id, String name, long next, int periodicity){
        this.name = name;
        this.nextOccurence = next;
        this.periodicity = periodicity;
        this.id = -1;
    }

}
