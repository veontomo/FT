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
        e2 = new YearEvent(3, "1 June", 888);
    }

    public void testAdjustDate() throws Exception {

    }

    public void testShouldAdjustDate() throws Exception {

    }
}