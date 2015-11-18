package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

/**
 */
public class HolidayTest extends TestCase {

    public void testSerializeWithId() throws Exception {
        Holiday h = new Holiday(34, "a holiday", 123456, 4);
        assertEquals(h.serialize(), "34#a holiday#123456#4");
    }

    public void testSerializeWithoutId() throws Exception {
        Holiday h = new Holiday("a holiday", 123456, 4);
        assertEquals(h.serialize(), "-1#a holiday#123456#4");
    }

    public void testDeserialize() throws Exception {

    }
}