package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Test suite for events that occur once a year
 */
public class YearEventTest extends TestCase {

    YearEvent event;
    Calendar c = Calendar.getInstance();

    public void setUp() throws Exception {
        super.setUp();
        c.set(1980, 4, 20);
        event = new YearEvent(3, "some event", c.getTimeInMillis());
    }

    public void testAdjustDateIfEventIsOneDayBeforeGivenDate() throws Exception {
        c.set(1980, 4, 21) ;
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertEquals(c.get(Calendar.YEAR), 1981);
        assertEquals(c.get(Calendar.MONTH), 4);
        assertEquals(c.get(Calendar.DAY_OF_MONTH), 20);
    }

    public void testAdjustDateIfEventIs30YearsBeforeGivenTime() throws Exception {
        c.set(2010, 10, 20);
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertEquals(c.get(Calendar.YEAR), 2011);
        assertEquals(c.get(Calendar.MONTH), 4);
        assertEquals(c.get(Calendar.DAY_OF_MONTH), 20);
    }

    public void testAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 3, 10);
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertEquals(c.get(Calendar.YEAR), 1980);
        assertEquals(c.get(Calendar.MONTH), 4);
        assertEquals(c.get(Calendar.DAY_OF_MONTH), 20);
    }

    public void testShouldAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        c.set(2000, 3, 10);
        assertTrue(event.shouldAdjustDate(c.getTimeInMillis()));
    }

    public void testShouldAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 2, 2);
        assertFalse(event.shouldAdjustDate(c.getTimeInMillis()));
    }
}