package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 * Test suite for events that occur just once
 */
public class SingleEventTest extends TestCase {

    SingleEvent e1, e2;

    public void setUp() throws Exception {
        super.setUp();
        e1 = new SingleEvent(22, "Saturday night", 12345);
        e2 = new SingleEvent("Xmas", 834);
    }

    public void testIdIfSet() {
        assertEquals("id must be equal to 22", e1.getId(), 22);
    }

    public void testIdIfNotSet() {
        assertEquals("id must be equal to -1", e2.getId(), -1);
    }

    public void testNameWithId() {
        assertEquals("SingleEvent name must be equal to Saturday night", e1.getName(), "Saturday night");
    }

    public void testNameNoId() {
        assertEquals("SingleEvent name must be equal to Saturday night", e2.getName(), "Xmas");
    }

    public void testNextOccurrenceWithId() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", e1.getNextOccurrence(), 12345);
    }

    public void testNextOccurrenceNoId() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", e2.getNextOccurrence(), 834);
    }

    public void testSerializeWithId() {
        assertEquals("SingleEvent#22#12345#Saturday night", e1.serialize());
    }

    public void testSerializeWithoutId() {
        assertEquals("SingleEvent#-1#834#Xmas", e2.serialize());
    }

    public void testShouldAdjustDateWithId(){
        assertFalse(e1.shouldAdjustDate(1));
    }

    public void testShouldAdjustDateWithoutId(){
        assertFalse(e2.shouldAdjustDate(20083));
    }

    public void testAdjustDateNoChangeIfEventIsBeforeGivenTime(){
        e1.adjustDate(55555);
        assertEquals(12345, e1.getNextOccurrence());
    }


    public void testAdjustDateNoChangeIfEventIsAfterGivenTime(){
        e1.adjustDate(1);
        assertEquals(12345, e1.getNextOccurrence());
    }

    public void testGetNameWithId(){
        e1.adjustDate(1);
        assertEquals(12345, e1.getNextOccurrence());
    }





}