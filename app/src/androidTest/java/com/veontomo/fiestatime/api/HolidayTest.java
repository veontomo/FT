package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 *
 */
public class HolidayTest extends TestCase {

    Holiday h1, h2;

    public void setUp() throws Exception {
        super.setUp();
        h1 = new Holiday(22, "Saturday night", 12345, 3);
        h2 = new Holiday("Xmas", 834, 1);
    }

    public void testHolidayExistent() {
        assertEquals("id must be equal to 22", h1.id, 22);
    }

    public void testHolidayWithoutId() {
        assertEquals("id must be equal to -1", h2.id, -1);
    }

    public void testHolidayNameExistent() {
        assertEquals("holiday name must be equal to Saturday night", h1.name, "Saturday night");
    }

    public void testHolidayNameWithoutId() {
        assertEquals("holiday name must be equal to Saturday night", h2.name, "Xmas");
    }

    public void testHolidayNextExistent() {
        assertEquals("holiday next occurrence must be equal to 12345", h1.nextOccurrence, 12345);
    }

    public void testHolidayNextWithoutId() {
        assertEquals("holiday next occurrence must be equal to 12345", h2.nextOccurrence, 834);
    }

    public void testHolidayPeriodicityExistent() {
        assertEquals("holiday periodicity must be equal to 3", h1.periodicity, 3);
    }

    public void testHolidayPeriodicityWithoutId() {
        assertEquals("holiday periodicity must be equal to 1", h2.periodicity, 1);
    }

    public void testDeserializeIdExistent() {
        Holiday h = Holiday.deserialize(h1.serialize());
        assertEquals("after deserialization, holiday id is wrong!", h.id, 22);
    }

    public void testDeserializeNameExistent() {
        Holiday h = Holiday.deserialize(h1.serialize());
        assertEquals("after deserialization, holiday name is wrong!", h.name, "Saturday night");
    }

    public void testDeserializeOccurrenceExistent() {
        Holiday h = Holiday.deserialize(h1.serialize());
        assertEquals("after deserialization, holiday next occurrence is wrong!", h.nextOccurrence, 12345);
    }

    public void testDeserializePeriodicityExistent() {
        Holiday h = Holiday.deserialize(h1.serialize());
        assertEquals("after deserialization, holiday periodicity is wrong!!", h.periodicity, 3);
    }

    public void testDeserializeIdOfNonExistent() {
        Holiday h = Holiday.deserialize(h2.serialize());
        assertEquals("after deserialization, holiday id is wrong!", h.id, -1);
    }

    public void testDeserializeNameOfNonExistent() {
        Holiday h = Holiday.deserialize(h2.serialize());
        assertEquals("after deserialization, holiday name is wrong!", h.name, "Xmas");
    }

    public void testDeserializeOccurrenceOfNonExistent() {
        Holiday h = Holiday.deserialize(h2.serialize());
        assertEquals("after deserialization, holiday next occurrence is wrong!", h.nextOccurrence, 834);
    }

    public void testDeserializePeriodicityOfNonExistent() {
        Holiday h = Holiday.deserialize(h2.serialize());
        assertEquals("after deserialization, holiday periodicity is wrong!", h.periodicity, 1);
    }


}