package com.veontomo.fiestatime.api;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.*;

/**
 * Test suite for a task that updates the widget
 *
 */
public class WidgetUpdateTaskTest extends TestCase {

    private WidgetUpdateTask task;
    private static final int MILLISEC_IN_DAY = 1000 * 60 * 60 * 24;

    public void setUp() throws Exception {
        super.setUp();
        task = new WidgetUpdateTask();

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDaysToEvent49HoursAgo() throws Exception {
        // the event occurred 49 hours ago
        long nowTime = 12345678000l;
        long eventTime =  nowTime - 49 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(-2);
    }

    public void testDaysToEvent47HoursAgo() throws Exception {
        // the event occurred 47 hours ago
        long nowTime = 12345678000l;
        long eventTime =  nowTime - 47 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(-1);
    }


    public void testDaysToEvent25HoursAgo() throws Exception {
        // the event occurred 25 hours ago
        long nowTime = 12345678000l;
        long eventTime =  nowTime - 25 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(-1);
    }

    public void testDaysToEvent23HoursAgo() throws Exception {
        // the event occurred 23 hours ago
        long nowTime = 12345678000l;
        long eventTime =  nowTime - 23 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(0);
    }


    public void testDaysToEvent12HoursAgo() throws Exception {
        // the event occurred 12 hours ago
        long nowTime = 12345678000l;
        long eventTime =  nowTime - 12 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(0);
    }

    public void testDaysToEventTheSameTime() throws Exception {
        long t = 12345678l;
        assertThat(task.daysBetween(t, t)).isEqualTo(0);
    }

    public void testDaysToEventIn12Hours() throws Exception {
        // the event will occur in 12 hours
        long nowTime = 12345678000l;
        long eventTime =  nowTime + 12 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(1);
    }

    public void testDaysToEventIn23Hours() throws Exception {
        // the event will occur in 23 hours
        long nowTime = 12345678000l;
        long eventTime =  nowTime + 23 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(1);
    }

    public void testDaysToEventIn25Hours() throws Exception {
        // the event will occur in 25 hours
        long nowTime = 12345678000l;
        long eventTime =  nowTime + 25 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(2);
    }

    public void testDaysToEventIn47Hours() throws Exception {
        // the event will occur in 47 hours
        long nowTime = 12345678000l;
        long eventTime =  nowTime + 47 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(2);
    }

    public void testDaysToEventIn49Hours() throws Exception {
        // the event will occur in 49 hours
        long nowTime = 12345678000l;
        long eventTime =  nowTime + 49 * 60 * 60 * 1000L;
        assertThat(task.daysBetween(nowTime, eventTime)).isEqualTo(3);
    }






}