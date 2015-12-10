package com.veontomo.fiestatime.api;

import junit.framework.TestCase;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;
/**
 * Test suite for events that occur once a year
 */
public class YearEventTest extends TestCase {

    YearEvent event;
    Calendar c;

    public void setUp() throws Exception {
        super.setUp();
        c = Calendar.getInstance();
        c.set(1980, 4, 20);
        event = new YearEvent(3, "some event", c.getTimeInMillis());
    }

    public void testAssignDefaultIdIfNotSpecified(){
        event = new YearEvent("event without id", 2222222);
        assertThat(event.getId()).isEqualTo(-1);
    }

    public void testAdjustDateIfEventIsOneDayBeforeGivenDate() throws Exception {
        c.set(1980, 4, 21) ;
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertThat(c.get(Calendar.YEAR)).isEqualTo(1981);
        assertThat(c.get(Calendar.MONTH)).isEqualTo(4);
        assertThat(c.get(Calendar.DAY_OF_MONTH)).isEqualTo(20);
    }

    public void testAdjustDateIfEventIs30YearsBeforeGivenTime() throws Exception {
        c.set(2010, 10, 20);
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertThat(c.get(Calendar.YEAR)).isEqualTo(2011);
        assertThat(c.get(Calendar.MONTH)).isEqualTo(4);
        assertThat(c.get(Calendar.DAY_OF_MONTH)).isEqualTo(20);
    }

    public void testAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 3, 10);
        event.adjustDate(c.getTimeInMillis());
        c.setTimeInMillis(event.getNextOccurrence());
        assertThat(c.get(Calendar.YEAR)).isEqualTo(1980);
        assertThat(c.get(Calendar.MONTH)).isEqualTo(4);
        assertThat(c.get(Calendar.DAY_OF_MONTH)).isEqualTo(20);
    }

    public void testShouldAdjustDateIfEventIsBeforeGivenTime() throws Exception {
        c.set(2000, 3, 10);
        assertThat(event.shouldAdjustDate(c.getTimeInMillis())).isTrue();
    }

    public void testShouldAdjustDateIfEventIsAfterGivenTime() throws Exception {
        c.set(1970, 2, 2);
        assertThat(event.shouldAdjustDate(c.getTimeInMillis())).isFalse();
    }
}