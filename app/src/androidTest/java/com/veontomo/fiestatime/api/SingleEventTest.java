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
        assertEquals("id must be equal to 22", h1.id, 22);
    }

    public void testSingleHolidayWithoutId() {
        assertEquals("id must be equal to -1", h2.id, -1);
    }

    public void testSingleHolidayNameExistent() {
        assertEquals("SingleEvent name must be equal to Saturday night", h1.name, "Saturday night");
    }

    public void testSingleHolidayNameWithoutId() {
        assertEquals("SingleEvent name must be equal to Saturday night", h2.name, "Xmas");
    }

    public void testSingleHolidayNextExistent() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", h1.nextOccurrence, 12345);
    }

    public void testSingleHolidayNextWithoutId() {
        assertEquals("SingleEvent next occurrence must be equal to 12345", h2.nextOccurrence, 834);
    }

    public void testSerializeWithId() {
        assertEquals("SingleEvent#22#12345#Saturday night", h1.serialize());
    }

    public void testSerializeWithoutId() {
        assertEquals("SingleEvent#-1#834#Xmas", h2.serialize());
    }






}