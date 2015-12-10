package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

import java.util.Calendar;
import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for events that occur once a week
 *
 */
public class WeekEventTest extends TestCase {

    WeekEvent event;
    Calendar c;

    public void setUp() throws Exception {
        super.setUp();
        c = Calendar.getInstance();
        c.set(2000, 10, 28); // Tuesday, 28 November 2000
        event = new WeekEvent(34, "Tuesday", c.getTimeInMillis());
    }


    public void testAssignDefaultIdIfNotSpecified(){
        event = new WeekEvent("event without id", 1234567890);
        assertThat(event.getId()).isEqualTo(-1L);
    }

    public void testAdjustDateIfEventIsManyYearsBeforeGivenDate() throws Exception {
        c.set(2015, 9, 4); // Sunday, 4 October 2015
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertThat(c.get(Calendar.YEAR)).isEqualTo(2015);
        assertThat(c.get(Calendar.MONTH)).isEqualTo(9);
        assertThat(c.get(Calendar.DAY_OF_MONTH)).isEqualTo(6);
    }

    public void testAdjustDateIfEventIsADayBeforeGivenDate() throws Exception {
        c.set(2000, 10, 29); // Wednesday, 29 November 2000
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertThat(c.get(Calendar.YEAR)).isEqualTo(2000);
        assertThat(c.get(Calendar.MONTH)).isEqualTo(11);
        assertThat(c.get(Calendar.DAY_OF_MONTH)).isEqualTo(5);
    }

    public void testShouldAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        c.set(2015, 3, 10);
        assertThat(event.shouldAdjustDate(c.getTimeInMillis())).isTrue();
    }

    public void testShouldAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 2, 2);
        assertThat(event.shouldAdjustDate(c.getTimeInMillis())).isFalse();
    }
}