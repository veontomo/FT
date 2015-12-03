package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 * Test for the event factory
 */
public class FactoryTest extends TestCase {

    private final static String[] classes = new String[]{"com.veontomo.fiestatime.api.SingleEvent",
            "com.veontomo.fiestatime.api.WeekEvent",
            "com.veontomo.fiestatime.api.MonthEvent",
            "com.veontomo.fiestatime.api.YearEvent"};

    Factory<Event> factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = new Factory<>(classes);
    }

    public void testProduceSingleEvent() {
        Event e = factory.produce(0, 2L, "single event", 1234567);
        assertNotNull(e);
    }

    public void testProduceSingleEventByClassName() {
        Event e = factory.produce("com.veontomo.fiestatime.api.SingleEvent", 2L, "single event", 1234567);
        assertNotNull(e);
    }

    public void testIndexOfSingleEvent() {
        assertEquals(0, factory.indexOf("com.veontomo.fiestatime.api.SingleEvent"));
    }

    public void testIndexOfWeekEvent() {
        assertEquals(1, factory.indexOf("com.veontomo.fiestatime.api.WeekEvent"));
    }

    public void testIndexOfMonthEvent() {
        assertEquals(2, factory.indexOf("com.veontomo.fiestatime.api.MonthEvent"));
    }
    public void testIndexOfYearEvent() {
        assertEquals(3, factory.indexOf("com.veontomo.fiestatime.api.YearEvent"));
    }

}