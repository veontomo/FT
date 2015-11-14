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

    private final static String SEPARATOR = "|";

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

    public String serialize() {
        return String.valueOf(id) + SEPARATOR + String.valueOf(nextOccurrence) + SEPARATOR + String.valueOf(periodicity) + SEPARATOR + name;
    }

    public static Holiday deserialize(String holidayStr){
        String[] arr = holidayStr.split(SEPARATOR);
        Holiday h = null;
        if (arr.length == 4){
            long id = Long.parseLong(arr[0], 10);
            long next = Long.parseLong(arr[1], 10);
            int periodicity = Integer.parseInt(arr[2]);
            h = new Holiday(id, arr[3], next, periodicity);
        }
        return h;
    }
}
