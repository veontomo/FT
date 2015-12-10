package com.veontomo.fiestatime.api;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.*;

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
        assertThat(e1.getId()).isEqualTo(22);
    }

    public void testIdIfNotSet() {
        assertThat(e2.getId()).isEqualTo(-1);
    }

    public void testNameWithId() {
        assertThat(e1.getName()).isEqualTo("Saturday night");
    }

    public void testNameNoId() {
        assertThat(e2.getName()).isEqualTo("Xmas");
    }

    public void testNextOccurrenceWithId() {
        assertThat(e1.getNextOccurrence()).isEqualTo(12345);
    }

    public void testNextOccurrenceNoId() {
        assertThat(e2.getNextOccurrence()).isEqualTo(834);
    }

    public void testSerializeWithId() {
        assertThat(e1.serialize()).isEqualTo("SingleEvent#22#12345#Saturday night");
    }

    public void testSerializeWithoutId() {
        assertThat(e2.serialize()).isEqualTo("SingleEvent#-1#834#Xmas");
    }

    public void testShouldAdjustDateWithId(){
        assertThat(e1.shouldAdjustDate(1)).isFalse();
    }

    public void testShouldAdjustDateWithoutId(){
        assertFalse(e2.shouldAdjustDate(20083));
    }

    public void testAdjustDateNoChangeIfEventIsBeforeGivenTime(){
        e1.adjustDate(55555);
        assertThat(e1.getNextOccurrence()).isEqualTo(12345);
    }


    public void testAdjustDateNoChangeIfEventIsAfterGivenTime(){
        e1.adjustDate(1);
        assertThat(12345).isEqualTo(e1.getNextOccurrence());
    }

    public void testGetNameWithId(){
        e1.adjustDate(1);
        assertThat(12345).isEqualTo(e1.getNextOccurrence());
    }





}