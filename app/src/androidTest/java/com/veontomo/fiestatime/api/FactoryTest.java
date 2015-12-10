package com.veontomo.fiestatime.api;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.*;

/**
 * Test for the event factory
 */
public class FactoryTest extends TestCase {

    Factory<Event> factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = new Factory<>();
    }


    public void testProduceSingleEventByClassName() {
        Event e = factory.produce("com.veontomo.fiestatime.api.SingleEvent", 2L, "single event", 1234567);
        assertThat(e).isNotNull();
    }


}