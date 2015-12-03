package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 * Test for the event factory
 */
public class EventFactoryTest extends TestCase {
    EventFactory factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = new EventFactory();
    }

    public void testProduceSingleEvent() {
        Event e = factory.produce(0, 2L, "single event", 1234567);
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