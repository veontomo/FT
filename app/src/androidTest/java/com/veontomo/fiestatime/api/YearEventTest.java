package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 * Test suite for events that occur once a year
 *
 */
public class YearEventTest extends TestCase {

    YearEvent e1, e2;

    public void setUp() throws Exception {
        super.setUp();
        e1 = new YearEvent("New Year", 50000);
        e2 = new YearEvent(3, "1 June", 5000);
    }

    public void testAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        e2.adjustDate(6000);
        long expectedTime = 5000L + 1000L * 60 * 60 * 24 * 365;
        assertEquals(expectedTime, e2.getNextOccurrence());
    }

    public void testAdjustDateIfEventIsAfterGivenTime() throws Exception {
        e2.adjustDate(1000);
        assertEquals(5000, e2.getNextOccurrence());
    }

    public void testShouldAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        assertTrue(e2.shouldAdjustDate(10000));
    }

    public void testShouldAdjustDateIfEventIsAfterGivenTime() throws Exception {
        assertFalse(e2.shouldAdjustDate(4000));
    }
}