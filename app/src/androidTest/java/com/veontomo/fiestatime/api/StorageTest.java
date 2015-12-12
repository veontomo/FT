package com.veontomo.fiestatime.api;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


/**
 *
 *
 */
public class StorageTest extends AndroidTestCase {

    private Storage storage;

    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        storage = new Storage(context);
    }

    public void tearDown() throws Exception {
        storage.close();
        storage = null;
        super.tearDown();
    }

    public void testSaveSingleEvent(){
        SingleEvent e = new SingleEvent("single event", 1234567000L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
    }


    public void testSaveTwoEventsWithTheSameName(){
        String name = "plain event";
        SingleEvent e1 = new SingleEvent(name, 9934567000L);
        SingleEvent e2 = new SingleEvent(name, 4454567000L);
        long id1 = storage.save(e1);
        long id2 = storage.save(e2);
        assertThat(id1).isNotEqualTo(-1);
        assertThat(id2).isNotEqualTo(-1);
    }

    public void testSaveTheSameEventTwice(){
        Event e = new SingleEvent("an event", 3489547858L);
        long id1 = storage.save(e);
        long id2 = storage.save(e);
        assertThat(id1).isNotEqualTo(-1);
        assertThat(id2).isEqualTo(-1);
    }

    public void testRestoreSingleEventByIdIfExists(){
        Event e = new SingleEvent("Conference", 3489547858L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        SingleEvent e2 = (SingleEvent) storage.getEventById(id);
        assertThat(e2).isNotNull();
        assertThat(e2.getName()).isEqualTo("Conference");
        assertThat(e2.getNextOccurrence()).isEqualTo(3489547858L);
        assertThat(e2.getId()).isEqualTo(id);
    }

    public void testRestoreSingleEventByIdIfNotExists() {
        Event e2 = storage.getEventById(123456);
        assertThat(e2).isNull();
    }

    public void testRestoreWeekEventByIdIfExists(){
        Event e = new WeekEvent("week seminar", 3489547858L);
        long id = storage.save(e);
        assertThat(id).isNotEqualTo(-1);
        WeekEvent e2 = (WeekEvent) storage.getEventById(id);
        assertThat(e2).isNotNull();
        assertThat(e2.getName()).isEqualTo("week seminar");
        assertThat(e2.getNextOccurrence()).isEqualTo(3489547858L);
        assertThat(e2.getId()).isEqualTo(id);
    }

    public void testRestoreWeekEventByIdIfNotExists() {
        WeekEvent e2 = (WeekEvent) storage.getEventById(123456);
        assertThat(e2).isNull();
    }


    public void testRetrieveAllEventsIfEmpty(){
        List<Event> events = storage.getEvents();
        assertThat(events).isEmpty();
    }


    public void testRetrieveAllEventIfOnlyOneEventIsPresent(){
        SingleEvent e = new SingleEvent("single event", 8888L);
        storage.save(e);
        List<Event> events = storage.getEvents();
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo("single event");
        assertThat(events.get(0).getNextOccurrence()).isEqualTo(8888L);

    }



}