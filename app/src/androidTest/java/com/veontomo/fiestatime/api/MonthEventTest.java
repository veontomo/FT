package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Test suite for events that occur once a week
 *
 */
public class MonthEventTest extends TestCase {

    MonthEvent event;
    Calendar c;

    public void setUp() throws Exception {
        super.setUp();
        c = Calendar.getInstance();
        c.set(2000, 10, 28); // Tuesday, 28 November 2000
        event = new MonthEvent(34, "Tuesday", c.getTimeInMillis());
    }

    public void testShouldAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        c.set(2015, 3, 10);
        assertTrue(event.shouldAdjustDate(c.getTimeInMillis()));
    }

    public void testShouldAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 2, 2);
        assertFalse(event.shouldAdjustDate(c.getTimeInMillis()));
    }



    public void testAdjustDateIfEventIsManyYearsBeforeGivenDate() throws Exception {
        c.set(2015, 9, 4); // Sunday, 4 October 2015
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertEquals(c.get(Calendar.YEAR), 2015);
        assertEquals(c.get(Calendar.MONTH), 9);
        assertEquals(c.get(Calendar.DAY_OF_MONTH), 28);
    }

    public void testAdjustDateIfEventIsADayBeforeGivenDate() throws Exception {
        c.set(2000, 10, 29); // Wednesday, 29 November 2000
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertEquals(c.get(Calendar.YEAR), 2000);
        assertEquals(c.get(Calendar.MONTH), 11);
        assertEquals(c.get(Calendar.DAY_OF_MONTH), 28);
    }
}