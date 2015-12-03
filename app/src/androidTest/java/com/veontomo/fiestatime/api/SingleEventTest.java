package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 *
 */
public class SingleEventTest extends TestCase {

    SingleEvent h1, h2;

    public void setUp() throws Exception {
        super.setUp();
        h1 = new SingleEvent(22, "Saturday night", 12345);
        h2 = new SingleEvent("Xmas", 834);
    }

    public void testSingleHolidayExistent() {
        assertEquals("id must be equal to 22", h1.getId(), 22);
    }

    public void testSingleHolidayWithoutId() {
        assertEquals("id must be equal to -1", h2.getId(), -1);
    }

    public void testSingleHolidayNameExistent() {
        assertEquals("SingleEvent name must be equal to Saturday night", h1.getName(), "Saturday night");
    }

    public void testSingleHolidayNameWithoutId() {
        assertEquals("SingleEvent name must be equal to Saturday night", h2.getName(), "Xmas");
    }

    public void testSingleHolidayNextExistent() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", h1.getNextOccurrence(), 12345);
    }

    public void testSingleHolidayNextWithoutId() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", h2.getNextOccurrence(), 834);
    }

    public void testSerializeWithId() {
        assertEquals("SingleEvent#22#12345#Saturday night", h1.serialize());
    }

    public void testSerializeWithoutId() {
        assertEquals("SingleEvent#-1#834#Xmas", h2.serialize());
    }

    public void testShouldAdjustDateWithId(){
        assertFalse(h1.shouldAdjustDate(1));
    }

    public void testShouldAdjustDateWithoutId(){
        assertFalse(h2.shouldAdjustDate(20083));
    }

    public void testAdjustDateNoChangeIfEventIsBeforeGivenTime(){
        h1.adjustDate(55555);
        assertEquals(12345, h1.getNextOccurrence());
    }


    public void testAdjustDateNoChangeIfEventIsAfterGivenTime(){
        h1.adjustDate(1);
        assertEquals(12345, h1.getNextOccurrence());
    }

    public void testGetNameWithId(){
        h1.adjustDate(1);
        assertEquals(12345, h1.getNextOccurrence());
    }





}