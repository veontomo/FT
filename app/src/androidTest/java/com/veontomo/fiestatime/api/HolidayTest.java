package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 *
 */
public class HolidayTest extends TestCase {

    SingleHoliday h1, h2;

    public void setUp() throws Exception {
        super.setUp();
        h1 = new SingleHoliday(22, "Saturday night", 12345);
        h2 = new SingleHoliday("Xmas", 834);
    }

    public void testSingleHolidayExistent() {
        assertEquals("id must be equal to 22", h1.id, 22);
    }

    public void testSingleHolidayWithoutId() {
        assertEquals("id must be equal to -1", h2.id, -1);
    }

    public void testSingleHolidayNameExistent() {
        assertEquals("SingleHoliday name must be equal to Saturday night", h1.name, "Saturday night");
    }

    public void testSingleHolidayNameWithoutId() {
        assertEquals("SingleHoliday name must be equal to Saturday night", h2.name, "Xmas");
    }

    public void testSingleHolidayNextExistent() {
        assertEquals("SingleHoliday next occurrence must be equal to 12345", h1.nextOccurrence, 12345);
    }

    public void testSingleHolidayNextWithoutId() {
        assertEquals("SingleHoliday next occurrence must be equal to 12345", h2.nextOccurrence, 834);
    }






}